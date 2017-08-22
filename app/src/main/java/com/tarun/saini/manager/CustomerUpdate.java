package com.tarun.saini.manager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

import static com.tarun.saini.manager.CustomerDetail.CUSTOMER_ADDRESS_DETAILS;
import static com.tarun.saini.manager.CustomerDetail.CUSTOMER_DATE_DETAILS;
import static com.tarun.saini.manager.CustomerDetail.CUSTOMER_EMAIL_DETAILS;
import static com.tarun.saini.manager.CustomerDetail.CUSTOMER_GSTIN_DETAILS;
import static com.tarun.saini.manager.CustomerDetail.CUSTOMER_ID_DETAILS;
import static com.tarun.saini.manager.CustomerDetail.CUSTOMER_NAME_DETAILS;
import static com.tarun.saini.manager.CustomerDetail.CUSTOMER_NOTES_DETAILS;
import static com.tarun.saini.manager.CustomerDetail.CUSTOMER_PAN_DETAILS;
import static com.tarun.saini.manager.CustomerDetail.CUSTOMER_PHONE_DETAILS;
import static com.tarun.saini.manager.CustomerDetail.CUSTOMER_UID_DETAILS;
import static com.tarun.saini.manager.CustomerDetail.CUSTOMER_URL_DETAILS;
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

public class CustomerUpdate extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton floatingActionButton;
    private TextView tv;
    private Button save;
    private String xml, name_string, address_string, uid_string, jsonString, phone_string, gst_string, pan_string, email_string, notes_string, dateInString,post_key,downLoadUrlNew;
    private EditText name, address, uid, phone, gst, pan, notes, email;
    private Integer REQUEST_CAMERA = 101, SELECT_FILE = 100;
    private ImageButton imageButton;
    private TextView photo_tv;
    private ImageView imageView;
    private Uri selectedImageUri = null;
    private String downLoadUrl, mTempPhotoPath;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private static final String FILE_PROVIDER_AUTHORITY = "com.tarun.saini.fileprovider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update);
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        uid = (EditText) findViewById(R.id.adhaar);
        phone = (EditText) findViewById(R.id.phone_no);
        gst = (EditText) findViewById(R.id.gst);
        pan = (EditText) findViewById(R.id.pan);
        notes = (EditText) findViewById(R.id.notes);
        email = (EditText) findViewById(R.id.email);

        CustomerDetail.setLatoRegular(this,name);
        CustomerDetail.setLatoRegular(this,address);
        CustomerDetail.setLatoRegular(this,uid);
        CustomerDetail.setLatoRegular(this,phone);
        CustomerDetail.setLatoRegular(this,gst);
        CustomerDetail.setLatoRegular(this,pan);
        CustomerDetail.setLatoRegular(this,notes);
        CustomerDetail.setLatoRegular(this,email);


        post_key=getIntent().getExtras().getString(CUSTOMER_ID_DETAILS);
        name_string=getIntent().getExtras().getString(CUSTOMER_NAME_DETAILS);
        address_string=getIntent().getExtras().getString(CUSTOMER_ADDRESS_DETAILS);
        uid_string=getIntent().getExtras().getString(CUSTOMER_UID_DETAILS);
        phone_string=getIntent().getExtras().getString(CUSTOMER_PHONE_DETAILS);
        gst_string=getIntent().getExtras().getString(CUSTOMER_GSTIN_DETAILS);
        pan_string=getIntent().getExtras().getString(CUSTOMER_PAN_DETAILS);
        email_string=getIntent().getExtras().getString(CUSTOMER_EMAIL_DETAILS);
        notes_string=getIntent().getExtras().getString(CUSTOMER_NOTES_DETAILS);
        downLoadUrl=getIntent().getExtras().getString(CUSTOMER_URL_DETAILS);
        dateInString=getIntent().getExtras().getString(CUSTOMER_DATE_DETAILS);


        imageButton = (ImageButton) findViewById(R.id.add_photo_button);
        photo_tv = (TextView) findViewById(R.id.add_photo_text);
        imageView = (ImageView) findViewById(R.id.image);

        CustomerDetail.setLatoRegular(this,photo_tv);

        if (!downLoadUrl.isEmpty())
        {
            Glide.with(this).load(downLoadUrl).into(imageView);
        }


       // private EditText name, address, uid, phone, gst, pan, notes, email;
        name.setText(name_string);
        address.setText(address_string);
        uid.setText(uid_string);
        phone.setText(phone_string);
        gst.setText(gst_string);
        pan.setText(pan_string);
        notes.setText(notes_string);
        email.setText(email_string);




        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Customer");

        imageButton.setOnClickListener(this);
        photo_tv.setOnClickListener(this);
        imageView.setOnClickListener(this);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        save = (Button) findViewById(R.id.save_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerUpdate.this, ScanBarcode.class);
                startActivityForResult(intent, 0);

            }
        });

        CustomerDetail.setLatoBold(this,save);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    xml = barcode.displayValue.replaceFirst("/", "");
                    XmlToJson xmlToJson = new XmlToJson.Builder(xml).build();
                    jsonString = xmlToJson.toString();


                    try {
                        JSONObject customer_data = (new JSONObject(jsonString)).getJSONObject("PrintLetterBarcodeData");
                        name_string = customer_data.getString("name");
                        uid_string = customer_data.getString("uid");
                        address_string = customer_data.getString("loc") + ","
                                + customer_data.getString("vtc") + ","
                                + customer_data.getString("dist") + ","
                                + customer_data.getString("state") + ","
                                + customer_data.getString("pc");

                        name.setText(name_string);
                        uid.setText(uid_string);
                        address.setText(address_string);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }


        } else if (resultCode == Activity.RESULT_OK) {

            Bitmap bmp;
            if (requestCode == REQUEST_CAMERA) {

                bmp = BitmapUtils.resamplePic(this, mTempPhotoPath);
                //  bmp = (Bitmap)  data.getExtras().get("data");
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "Title", null);
                selectedImageUri = Uri.parse(path);
                Glide.with(this)
                        .load(selectedImageUri)
                        .asBitmap()
                        .into(imageView);
            }
            //selectedImageUri = getImageUri(this, bmp);
            //imageView.setImageBitmap(bmp);

            else if (requestCode == SELECT_FILE) {

                selectedImageUri = data.getData();
                try {
                    bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    Glide.with(this)
                            .load(selectedImageUri)
                            .asBitmap()
                            .into(imageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } else {

            // Otherwise, delete the temporary image file
            BitmapUtils.deleteImageFile(this, mTempPhotoPath);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void SelectImage() {

        final CharSequence[] items = {getString(R.string.camera), getString(R.string.gallery), getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_image);

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals(getString(R.string.camera))) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Ensure that there's a camera activity to handle the intent
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        // Create the temporary File where the photo should go
                        File photoFile = null;
                        try {
                            photoFile = BitmapUtils.createTempImageFile(CustomerUpdate.this);
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            ex.printStackTrace();
                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {

                            // Get the path of the temporary file
                            mTempPhotoPath = photoFile.getAbsolutePath();

                            // Get the content URI for the image file
                            Uri photoURI = FileProvider.getUriForFile(CustomerUpdate.this,
                                    FILE_PROVIDER_AUTHORITY,
                                    photoFile);

                            // Add the URI so the camera can store the image
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(Intent.createChooser(intent, "Choose Camera"), REQUEST_CAMERA);


                        }
                    }

                    /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);*/
                } else if (items[i].equals(getString(R.string.gallery))) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals(getString(R.string.launch_camera))) {

                    Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals(getString(R.string.cancel))) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    public void onClick(View v) {
        SelectImage();

    }


    //upload image to firebase storage
    private void uploadCustomerData() {

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        //if there is a file to upload
        if (selectedImageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.uploading_message));
            progressDialog.show();
            String path = "images/" + currentDateTimeString + ".png";
            StorageReference riversRef = mStorageRef.child(path);
            riversRef.putFile(selectedImageUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests") Uri ImageUrl = taskSnapshot.getDownloadUrl();
                            if (ImageUrl != null)
                            {
                                downLoadUrlNew = ImageUrl.toString().trim();

                            }
                            else
                            {
                                downLoadUrlNew=" ";
                            }

                            if (!TextUtils.isEmpty(name_string)) {
                                DatabaseReference newCustomer = mDatabase.push();
                                newCustomer.child("name").setValue(name_string);
                                newCustomer.child("address").setValue(address_string);
                                newCustomer.child("uid").setValue(uid_string);
                                newCustomer.child("phone").setValue(phone_string);
                                newCustomer.child("gst").setValue(gst_string);
                                newCustomer.child("pan").setValue(pan_string);
                                newCustomer.child("date").setValue(dateInString);
                                newCustomer.child("downloadUrl").setValue(downLoadUrlNew);
                                newCustomer.child("email").setValue(email_string);
                                newCustomer.child("notes").setValue(notes_string);
                            }

                            Intent homeIntent = new Intent(CustomerUpdate.this, CustomerInfo.class);
                            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(homeIntent);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            progressDialog.dismiss();


                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            @SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage(getString(R.string.uploaded) + ((int) progress) + "%...");
                        }
                    });
        }

        else
        {
            if (!TextUtils.isEmpty(name_string)) {
                DatabaseReference newCustomer = mDatabase.push();
                newCustomer.child("name").setValue(name_string);
                newCustomer.child("address").setValue(address_string);
                newCustomer.child("uid").setValue(uid_string);
                newCustomer.child("phone").setValue(phone_string);
                newCustomer.child("gst").setValue(gst_string);
                newCustomer.child("pan").setValue(pan_string);
                newCustomer.child("date").setValue(dateInString);
                newCustomer.child("downloadUrl").setValue(downLoadUrl);
                newCustomer.child("email").setValue(email_string);
                newCustomer.child("notes").setValue(notes_string);
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle(getString(R.string.saving_message)+"%.....");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {

                        progressDialog.dismiss();
                        Intent homeIntent = new Intent(CustomerUpdate.this, CustomerInfo.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homeIntent);

                    }
                },2000);



            }
            else
            {
                Toast.makeText(this, "Please add some info about customer", Toast.LENGTH_SHORT).show();
            }
        }


    }

//String xml, name_string, address_string, uid_string, jsonString,
// phone_string, gst_string, pan_string, email_string, notes_string,
// dateInString;

    public void saveInDataBase(View view)
    {
        String pattern = "dd MMM yyyy";
        name_string = name.getText().toString();
        address_string = address.getText().toString();
        uid_string = uid.getText().toString();
        phone_string = phone.getText().toString();
        gst_string = gst.getText().toString();
        pan_string = pan.getText().toString();
        email_string = email.getText().toString();
        notes_string = notes.getText().toString();
       // dateInString = new SimpleDateFormat(pattern).format(new Date());
        mDatabase.child(post_key).removeValue();

        uploadCustomerData();

    }


}
