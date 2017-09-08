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
import com.google.firebase.database.Query;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class ImportantCustomer extends AppCompatActivity {

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
    private DatabaseReference mDatabaseImportant;
    private Query mQueryByName;
    RelativeLayout empty_view;
    private boolean important=false;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_customer);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Customer");
        //mDatabaseImportant= mDatabase.push().child("important");
       // mQueryByName=mDatabase.orderByChild("name");
        mQueryByName=mDatabase.orderByChild("important").equalTo(true);
        mDatabase.keepSynced(true);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        empty_view= (RelativeLayout) findViewById(R.id.empty_view);
        recyclerView= (RecyclerView) findViewById(R.id.customer_recycler_view);
        recyclerView.setHasFixedSize(true);
        manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#20000000"));



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(ImportantCustomer.this,CustomerAdd.class);
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


        FirebaseRecyclerAdapter<Customer,CustomerInfo.CustomerviewHolder> firebaseRecyclerAdapter
                =new FirebaseRecyclerAdapter<Customer, CustomerInfo.CustomerviewHolder>(Customer.class,R.layout.list_item,CustomerInfo.CustomerviewHolder.class,mQueryByName) {
            @Override
            protected void populateViewHolder(final CustomerInfo.CustomerviewHolder viewHolder, final Customer model, int position) {

                final String post_key=getRef(position).getKey();
                String nameTextInitial=model.getName().substring(0,1);
                viewHolder.setName(model.getName());
               // viewHolder.setPhone(model.getPhone());
                viewHolder.setDate(model.getDate());
                viewHolder.setGST(model.getGst());
                // viewHolder.setGST(String.valueOf(FirebaseDatabase.getInstance().getReference()).substring(8,21));
                viewHolder.setIconText(nameTextInitial);


                viewHolder.add_important.setChecked(model.isImportant());



                CustomerDetail.setLatoBlack(ImportantCustomer.this,viewHolder.customer_name);
//                CustomerDetail.setLatoBlack(ImportantCustomer.this,viewHolder.customer_phone);
                CustomerDetail.setLatoBlack(ImportantCustomer.this,viewHolder.customer_added_date);
                CustomerDetail.setLatoRegular(ImportantCustomer.this,viewHolder.customer_gst);






                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(CustomerInfo.this, post_key+"", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ImportantCustomer.this,CustomerDetail.class);
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
                            mDatabase.child(post_key).child("important").setValue(true);
//                            important=true;

                            Toast.makeText(ImportantCustomer.this, "Added", Toast.LENGTH_SHORT).show();

                        } else
                        {
                            mDatabase.child(post_key).child("important").setValue(false);
                            Toast.makeText(ImportantCustomer.this, "Removed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        };




        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
        empty_view.setVisibility(View.GONE);



    }


    public static class CustomerviewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        CheckBox add_important;
        TextView customer_name, customer_added_date,customer_phone,customer_gst,iconText;

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

        /*public void setPhone(String phone)
               {
            customer_phone= (TextView) mView.findViewById(R.id.txt_primary);
            customer_phone.setText(phone);
        }*/

        public void setGST(String gst)
        {
            customer_gst= (TextView) mView.findViewById(R.id.txt_secondary);
            customer_gst.setText("GSTIN: "+gst);
        }

        public void setIconText(String text)
        {
            iconText= (TextView) mView.findViewById(R.id.icon_text);
            iconText.setText(text);
        }


    }}