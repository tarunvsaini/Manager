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
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

public class CustomerDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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

        getMenuInflater().inflate(R.menu.customer_detail_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.delete_customer)
        {
            startActivity(new Intent(CustomerDetail.this,CustomerAdd.class));
        }
        return super.onOptionsItemSelected(item);
    }


    public static void setLatoRegular(Context context, TextView textView)
    {
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"lato_regular.ttf");
        textView.setTypeface(typeface);

    }
    public static void setLatoBold(Context context, TextView textView)
    {
        Typeface typefaceBold=Typeface.createFromAsset(context.getAssets(),"lato_bold.ttf");
        textView.setTypeface(typefaceBold);

    }
    public static void setLatoBlack(Context context, TextView textView)
    {
        Typeface typefaceBlack=Typeface.createFromAsset(context.getAssets(),"lato_black.ttf");
        textView.setTypeface(typefaceBlack);

    }

}
