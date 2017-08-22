package com.tarun.saini.manager;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tarun on 22-08-2017.
 */

public class CustomerPersistence extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


}
