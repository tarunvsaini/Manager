package com.tarun.saini.manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

public class CustomerDetail extends AppCompatActivity {

    TextView customerDetails, customerName, customerAddress, customerPhone, customerGst, customerPan, customerUid, customerEmail, customerNotes;
    Button downloadButton, editButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);


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
            startActivity(new Intent(CustomerDetail.this, CustomerAdd.class));
        }
        return super.onOptionsItemSelected(item);
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
