package com.tarun.saini.manager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import static com.tarun.saini.manager.CustomerInfo.CUSTOMER_ADDRESS;
import static com.tarun.saini.manager.CustomerInfo.CUSTOMER_DATE;
import static com.tarun.saini.manager.CustomerInfo.CUSTOMER_EMAIL;
import static com.tarun.saini.manager.CustomerInfo.CUSTOMER_GSTIN;
import static com.tarun.saini.manager.CustomerInfo.CUSTOMER_ID;
import static com.tarun.saini.manager.CustomerInfo.CUSTOMER_NAME;
import static com.tarun.saini.manager.CustomerInfo.CUSTOMER_NOTES;
import static com.tarun.saini.manager.CustomerInfo.CUSTOMER_PAN;
import static com.tarun.saini.manager.CustomerInfo.CUSTOMER_PHONE;
import static com.tarun.saini.manager.CustomerInfo.CUSTOMER_UID;
import static com.tarun.saini.manager.CustomerInfo.CUSTOMER_URL;


public class CustomerDetail extends AppCompatActivity {

    private TextView customerDetails, customerName, customerAddress, customerPhone, customerGst, customerPan, customerUid, customerEmail, customerNotes;
    private Button downloadButton, editButton;
    private String  name_string, address_string, uid_string, phone_string, gst_string, pan_string, email_string, notes_string, downLoadUrl,post_key,customer_date;
    private DatabaseReference mDatabase;
    public static final String CUSTOMER_ID_DETAILS ="customer_id" ;
    public static final String CUSTOMER_NAME_DETAILS ="customer_name" ;
    public static final String CUSTOMER_ADDRESS_DETAILS ="customer_address" ;
    public static final String CUSTOMER_UID_DETAILS ="customer_uid" ;
    public static final String CUSTOMER_PHONE_DETAILS ="customer_phone" ;
    public static final String CUSTOMER_PAN_DETAILS ="customer_pan" ;
    public static final String CUSTOMER_GSTIN_DETAILS ="customer_gst" ;
    public static final String CUSTOMER_NOTES_DETAILS ="customer_notes" ;
    public static final String CUSTOMER_EMAIL_DETAILS ="customer_email" ;
    public static final String CUSTOMER_URL_DETAILS ="customer_url" ;
    public static final String CUSTOMER_DATE_DETAILS ="customer_date" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Customer");

        post_key=getIntent().getExtras().getString(CUSTOMER_ID);
        name_string=getIntent().getExtras().getString(CUSTOMER_NAME);
        address_string=getIntent().getExtras().getString(CUSTOMER_ADDRESS);
        uid_string=getIntent().getExtras().getString(CUSTOMER_UID);
        phone_string=getIntent().getExtras().getString(CUSTOMER_PHONE);
        gst_string=getIntent().getExtras().getString(CUSTOMER_GSTIN);
        pan_string=getIntent().getExtras().getString(CUSTOMER_PAN);
        email_string=getIntent().getExtras().getString(CUSTOMER_EMAIL);
        notes_string=getIntent().getExtras().getString(CUSTOMER_NOTES);
        downLoadUrl=getIntent().getExtras().getString(CUSTOMER_URL);
        customer_date=getIntent().getExtras().getString(CUSTOMER_DATE);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        customerDetails = (TextView) findViewById(R.id.customer_details);
        customerName = (TextView) findViewById(R.id.customer_name);
        customerAddress = (TextView) findViewById(R.id.customer_address);
        customerPhone = (TextView) findViewById(R.id.customer_phone);
        customerGst = (TextView) findViewById(R.id.customer_gst);
        customerPan = (TextView) findViewById(R.id.customer_pan);
        customerUid = (TextView) findViewById(R.id.customer_uid);
        customerEmail = (TextView) findViewById(R.id.customer_email);
        customerNotes = (TextView) findViewById(R.id.customer_notes);

        downloadButton = (Button) findViewById(R.id.download_button);
        editButton = (Button) findViewById(R.id.edit_button);


        setLatoBlack(this, customerDetails);
        setLatoBlack(this, customerName);
        setLatoRegular(this, customerAddress);
        setLatoRegular(this, customerPhone);
        setLatoRegular(this, customerGst);
        setLatoRegular(this, customerPan);
        setLatoRegular(this, customerUid);
        setLatoRegular(this, customerEmail);
        setLatoRegular(this, customerNotes);
        setLatoBlack(this, downloadButton);
        setLatoBlack(this, editButton);

        customerName.setText(name_string);
        customerEmail.setText("Email: "+email_string);
        customerAddress.setText("Address: "+address_string);
        customerPhone.setText("Phone: "+phone_string);
        customerGst.setText("GSTIN: "+gst_string);
        customerPan.setText("PAN: "+pan_string);
        customerUid.setText("Aadhaar/UID: "+uid_string);
        customerNotes.setText("Notes: "+notes_string);

        if (downLoadUrl.isEmpty())
        {
            downloadButton.setVisibility(View.GONE);
        }

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(downLoadUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(CustomerDetail.this,CustomerUpdate.class);
                intent.putExtra(CUSTOMER_ID_DETAILS,post_key);
                intent.putExtra(CUSTOMER_NAME_DETAILS,name_string);
                intent.putExtra(CUSTOMER_ADDRESS_DETAILS,address_string);
                intent.putExtra(CUSTOMER_UID_DETAILS,uid_string);
                intent.putExtra(CUSTOMER_PHONE_DETAILS,phone_string);
                intent.putExtra(CUSTOMER_PAN_DETAILS,pan_string);
                intent.putExtra(CUSTOMER_GSTIN_DETAILS,gst_string);
                intent.putExtra(CUSTOMER_EMAIL_DETAILS,email_string);
                intent.putExtra(CUSTOMER_NOTES_DETAILS,notes_string);
                intent.putExtra(CUSTOMER_URL_DETAILS,downLoadUrl);
                intent.putExtra(CUSTOMER_DATE_DETAILS,customer_date);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#20000000"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.customer_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_customer) {

            createDialog();

        }
        return super.onOptionsItemSelected(item);
    }

    private void createDialog() {

        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle(Html.fromHtml("<font color='#ef5350'>WARNING: This will be deleted permanently.</font>"));
        alertDialog.setMessage("Are you sure want to delete this customer?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                mDatabase.child(post_key).removeValue();
                finish();
                Intent newIntent=new Intent(CustomerDetail.this,CustomerInfo.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);




            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        alertDialog.create();
        alertDialog.show();


    }


    public static void setLatoRegular(Context context, TextView textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "lato_regular.ttf");
        textView.setTypeface(typeface);

    }

    public static void setLatoBold(Context context, TextView textView) {
        Typeface typefaceBold = Typeface.createFromAsset(context.getAssets(), "lato_bold.ttf");
        textView.setTypeface(typefaceBold);

    }

    public static void setLatoBlack(Context context, TextView textView) {
        Typeface typefaceBlack = Typeface.createFromAsset(context.getAssets(), "lato_black.ttf");
        textView.setTypeface(typefaceBlack);

    }

}
