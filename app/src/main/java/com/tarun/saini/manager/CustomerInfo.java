package com.tarun.saini.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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

import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class CustomerInfo extends AppCompatActivity {


    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_ADDRESS = "customer_address";
    public static final String CUSTOMER_UID = "customer_uid";
    public static final String CUSTOMER_PHONE = "customer_phone";
    public static final String CUSTOMER_PAN = "customer_pan";
    public static final String CUSTOMER_GSTIN = "customer_gst";
    public static final String CUSTOMER_NOTES = "customer_notes";
    public static final String CUSTOMER_EMAIL = "customer_email";
    public static final String CUSTOMER_URL = "customer_url";
    public static final String CUSTOMER_DATE = "customer_date";

    FloatingActionButton fab;
    Toolbar toolbar;
    RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseImportant;
    private Query mQueryByName;
    RelativeLayout empty_view;
    private boolean important = false;
    //private MyCustomLayoutManager manager;
    private LinearLayoutManager manager;
    int previousPosition=0;
    TextView dictionary;
    CustomRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Customer");
        //mDatabaseImportant= mDatabase.push().child("important");
        mQueryByName = mDatabase.orderByChild("name");
        //mQueryByName=mDatabase.orderByChild("important").equalTo(true);
        mDatabase.keepSynced(true);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        empty_view = (RelativeLayout) findViewById(R.id.empty_view);



        recyclerView = (CustomRecyclerView) findViewById(R.id.customer_recycler_view);
       // recyclerView = (RecyclerView) findViewById(R.id.customer_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);

        // recyclerView.setHasFixedSize(true);


        /*manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);*/


        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#20000000"));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerInfo.this, CustomerAdd.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
        VerticalRecyclerViewFastScroller fastScroller = (VerticalRecyclerViewFastScroller) findViewById(R.id.fast_scroller);

        // Connect the recycler to the scroller (to let the scroller scroll the list)
        fastScroller.setRecyclerView(recyclerView);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        recyclerView.setOnScrollListener(fastScroller.getOnScrollListener());


        manager = new MyCustomLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        //recyclerView.smoothScrollToPosition(position);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter_list) {
            Intent intent = new Intent(CustomerInfo.this, ImportantCustomer.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

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


        FirebaseRecyclerAdapter<Customer, CustomerviewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Customer, CustomerviewHolder>(Customer.class, R.layout.list_item, CustomerviewHolder.class, mQueryByName) {
            @Override
            protected void populateViewHolder(final CustomerviewHolder viewHolder, final Customer model, int position)
            {

                final String post_key = getRef(position).getKey();
                final String nameTextInitial = model.getName().substring(0, 1);
                viewHolder.setName(model.getName());
               // viewHolder.setPhone(model.getPhone());
                viewHolder.setDate(model.getDate());
                viewHolder.setGST(model.getGst());
                // viewHolder.setGST(String.valueOf(FirebaseDatabase.getInstance().getReference()).substring(8,21));
                viewHolder.setIconText(nameTextInitial);

                /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                        if (dy < 0) {
                            dictionary.setVisibility(View.VISIBLE);
                            dictionary.setText();

                            //Toast.makeText(CustomerInfo.this, "Up", Toast.LENGTH_SHORT).show();
                            // Recycle view scrolling up...

                        } else if (dy > 0)
                        {
                            dictionary.setVisibility(View.VISIBLE);
                            dictionary.setText(nameTextInitial);

                           // Toast.makeText(CustomerInfo.this, "Down", Toast.LENGTH_SHORT).show();
                            // Recycle view scrolling down...
                        }
                    }
                });*/


                viewHolder.add_important.setChecked(model.isImportant());


                CustomerDetail.setLatoBlack(CustomerInfo.this, viewHolder.customer_name);
              //  CustomerDetail.setLatoBlack(CustomerInfo.this, viewHolder.customer_phone);
                CustomerDetail.setLatoBlack(CustomerInfo.this, viewHolder.customer_added_date);
                CustomerDetail.setLatoRegular(CustomerInfo.this, viewHolder.customer_gst);


                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(CustomerInfo.this, post_key+"", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CustomerInfo.this, CustomerDetail.class);
                        intent.putExtra(CUSTOMER_ID, post_key);
                        intent.putExtra(CUSTOMER_NAME, model.getName());
                        intent.putExtra(CUSTOMER_ADDRESS, model.getAddress());
                        intent.putExtra(CUSTOMER_UID, model.getUid());
                        intent.putExtra(CUSTOMER_PHONE, model.getPhone());
                        intent.putExtra(CUSTOMER_PAN, model.getPan());
                        intent.putExtra(CUSTOMER_GSTIN, model.getGst());
                        intent.putExtra(CUSTOMER_EMAIL, model.getEmail());
                        intent.putExtra(CUSTOMER_NOTES, model.getNotes());
                        intent.putExtra(CUSTOMER_URL, model.getDownloadUrl());
                        intent.putExtra(CUSTOMER_DATE, model.getDate());
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

                            Toast.makeText(CustomerInfo.this, "Added", Toast.LENGTH_SHORT).show();

                        } else {
                            mDatabase.child(post_key).child("important").setValue(false);
                            Toast.makeText(CustomerInfo.this, "Removed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


                /*if (position>previousPosition)
                {
                    AnimationUtil.animate(viewHolder,true);
                }
                else
                    {
                        AnimationUtil.animate(viewHolder,false);
                    }

                    previousPosition=position;*/
                //recyclerView.smoothScrollToPosition(position);
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }
        };

       // Toast.makeText(this, firebaseRecyclerAdapter.getItemCount()+"", Toast.LENGTH_SHORT).show();

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
        empty_view.setVisibility(View.GONE);





    }


    public static class CustomerviewHolder extends RecyclerView.ViewHolder {
        View mView;
        CheckBox add_important;
        TextView customer_name, customer_added_date, customer_phone, customer_gst, iconText;

        public CustomerviewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            add_important = (CheckBox) mView.findViewById(R.id.icon_star);

        }

        public void setName(String name) {
            customer_name = (TextView) mView.findViewById(R.id.name);
            customer_name.setText(name);

        }

        public void setDate(String date) {
            customer_added_date = (TextView) mView.findViewById(R.id.timestamp);
            customer_added_date.setText(date);
        }

       /* public void setPhone(String phone) {
            customer_phone = (TextView) mView.findViewById(R.id.txt_primary);
            customer_phone.setText(phone);
        }*/

        public void setGST(String gst) {
            customer_gst = (TextView) mView.findViewById(R.id.txt_secondary);
            customer_gst.setText("GSTIN: " + gst);
        }

        public void setIconText(String text) {
            iconText = (TextView) mView.findViewById(R.id.icon_text);
            iconText.setText(text);
        }


    }
}


/* */