package com.tarun.saini.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class CustomerInfo extends AppCompatActivity {


    FloatingActionButton fab;
    Toolbar toolbar;
    RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    RelativeLayout empty_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Customer");
        fab= (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        empty_view= (RelativeLayout) findViewById(R.id.empty_view);
        recyclerView= (RecyclerView) findViewById(R.id.customer_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#20000000"));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(CustomerInfo.this,CustomerAdd.class));

            }
        });


        int Permission_All = 1;
        String[] Permissions = new String[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Permissions = new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.MEDIA_CONTENT_CONTROL, android.Manifest.permission.CAMERA,};
        }
        if (!hasPermissions(this, Permissions)) {
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.filter_list)
        {
            //
        }
        return super.onOptionsItemSelected(item);
    }


    public static boolean hasPermissions(Context context, String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Customer,CustomerviewHolder> firebaseRecyclerAdapter
                =new FirebaseRecyclerAdapter<Customer, CustomerviewHolder>(Customer.class,R.layout.list_item,CustomerviewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(CustomerviewHolder viewHolder, Customer model, int position) {

                final String post_key=getRef(position).getKey();
                viewHolder.setName(model.getName());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setDate(model.getDate());
                viewHolder.setGST(model.getGst());

                CustomerDetail.setLatoBlack(CustomerInfo.this,viewHolder.customer_name);
                CustomerDetail.setLatoBlack(CustomerInfo.this,viewHolder.customer_phone);
                CustomerDetail.setLatoBlack(CustomerInfo.this,viewHolder.customer_added_date);
                CustomerDetail.setLatoRegular(CustomerInfo.this,viewHolder.customer_gst);


                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(CustomerInfo.this, post_key+"", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CustomerInfo.this,CustomerDetail.class));
                    }
                });


                final boolean checked=viewHolder.add_important.isChecked();
                viewHolder.add_important.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((checked)) //check for condition if recipe in database
                        {

                            Toast.makeText(CustomerInfo.this, "Added", Toast.LENGTH_SHORT).show();

                        } else
                        {
                            Toast.makeText(CustomerInfo.this, "Removed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        };


        if (recyclerView!=null)
        {

            recyclerView.setAdapter(firebaseRecyclerAdapter);
            firebaseRecyclerAdapter.notifyDataSetChanged();
            empty_view.setVisibility(View.INVISIBLE);
        }



    }


    public static class CustomerviewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        CheckBox add_important;
        TextView customer_name, customer_added_date,customer_phone,customer_gst;

        public CustomerviewHolder(View itemView) {
            super(itemView);

            mView=itemView;
            add_important= (CheckBox) mView.findViewById(R.id.icon_star);

        }

        public void setName(String name)
        {
             customer_name= (TextView) mView.findViewById(R.id.name);
            customer_name.setText(name);

        }
        public void setDate(String date)
        {
             customer_added_date = (TextView) mView.findViewById(R.id.timestamp);
            customer_added_date.setText(date);
        }

        public void setPhone(String phone)
        {
             customer_phone= (TextView) mView.findViewById(R.id.txt_primary);
            customer_phone.setText(phone);
        }

        public void setGST(String gst)
        {
             customer_gst= (TextView) mView.findViewById(R.id.txt_secondary);
            customer_gst.setText("GSTIN: "+gst);
        }


    }
}


/* */