package com.tarun.saini.manager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
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


    public static final String CUSTOMER_ID ="customer_id" ;
    public static final String CUSTOMER_NAME ="customer_name" ;
    public static final String CUSTOMER_ADDRESS ="customer_address" ;
    public static final String CUSTOMER_UID ="customer_uid" ;
    public static final String CUSTOMER_PHONE ="customer_phone" ;
    public static final String CUSTOMER_PAN ="customer_pan" ;
    public static final String CUSTOMER_GSTIN ="customer_gst" ;
    public static final String CUSTOMER_NOTES ="customer_notes" ;
    public static final String CUSTOMER_EMAIL ="customer_email" ;
    public static final String CUSTOMER_URL ="customer_url" ;
    public static final String CUSTOMER_DATE ="customer_date" ;

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
        mDatabase.keepSynced(true);
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
                Intent intent=new Intent(CustomerInfo.this,CustomerAdd.class);
                intent .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

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
            final CharSequence[] items = {"By Name", "By Date(Newest)","By Date(Oldest)","Show Marked Important"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Filter Customers");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    if (items[i].equals("By Name"))
                    {

                    }
                    else if (items[i].equals("By Date(Newest)"))
                    {

                    }
                    else if (items[i].equals("By Date(Oldest)"))
                    {

                    }
                    else if (items[i].equals("By Show Marked Important"))
                    {

                    }

                }
            });

            builder.show();
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
            protected void populateViewHolder(final CustomerviewHolder viewHolder, final Customer model, int position) {

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
                        Intent intent=new Intent(CustomerInfo.this,CustomerDetail.class);
                        intent.putExtra(CUSTOMER_ID,post_key);
                        intent.putExtra(CUSTOMER_NAME,model.getName());
                        intent.putExtra(CUSTOMER_ADDRESS,model.getAddress());
                        intent.putExtra(CUSTOMER_UID,model.getUid());
                        intent.putExtra(CUSTOMER_PHONE,model.getPhone());
                        intent.putExtra(CUSTOMER_PAN,model.getPan());
                        intent.putExtra(CUSTOMER_GSTIN,model.getGst());
                        intent.putExtra(CUSTOMER_EMAIL,model.getEmail());
                        intent.putExtra(CUSTOMER_NOTES,model.getNotes());
                        intent.putExtra(CUSTOMER_URL,model.getDownloadUrl());
                        intent.putExtra(CUSTOMER_DATE,model.getDate());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });


                /*final boolean checked=viewHolder.add_important.isChecked();*/
                viewHolder.add_important.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (viewHolder.add_important.isChecked()) //check for condition if recipe in database
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