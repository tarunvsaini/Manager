package com.tarun.saini.manager;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCustomerFragment extends Fragment  {

    private static final String SAVE_COLLAPSE_STATE = "collapse state";
    Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private boolean collapsed;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;


    public AddCustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_customer, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinatorLayout);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#20000000"));


        collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appBar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Add Customer");
                    isShow = true;
                    collapsed = false;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                    collapsed = true;
                }

            }

        });

        if (savedInstanceState != null) {


            collapsed = savedInstanceState.getBoolean(SAVE_COLLAPSE_STATE);
            appBarLayout.setExpanded(collapsed);


        }
        return rootView;

    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVE_COLLAPSE_STATE, collapsed);
    }


}
