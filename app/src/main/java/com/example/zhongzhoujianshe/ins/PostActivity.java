package com.example.zhongzhoujianshe.ins;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;

import com.example.zhongzhoujianshe.ins.Helper.Post;
import com.example.zhongzhoujianshe.ins.Home.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {

    //public ImageView imageview = null;
    private Bitmap rawBitmap = null;
    private Bitmap thumbnail = null;
    
    private Button btnPost;
    private Button btnBack;
    private Button btnChoose;
    //private String filePath = "";
    private ImageView imageView;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    private String imageId;

    //firebase
    //Firebase
    private FirebaseStorage storage;  //storage will be used to create a FirebaseStorage instance
    private StorageReference storageReference;  //storageReference will point to the uploaded file.
    private FirebaseUser user_db;
    private DatabaseReference dbRef;
    private String userId;


    private final static int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;

    Set<BluetoothDevice> pairedDevices;
    // temp arraylist

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //Initialize Views
        btnChoose = (Button) findViewById(R.id.button_choose);
        btnPost = (Button) findViewById(R.id.button_post);
        btnBack = (Button) findViewById(R.id.button_back);
        imageView = (ImageView) findViewById(R.id.imageview);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        dbRef = FirebaseDatabase.getInstance().getReference();
        user_db = FirebaseAuth.getInstance().getCurrentUser();
        if (user_db != null) {
            userId = user_db.getUid();

        }

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("posts/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            // save image to database
                            String key = dbRef.child("post").push().getKey();
                            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                                    .format(new Date());
                            Post post =
                                    new Post(userId, downloadUrl.toString(), timeStamp, "", key);

                            dbRef.child("post").child(key).setValue(post);

                            progressDialog.dismiss();
                            Toast.makeText(PostActivity.this, "Uploaded",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PostActivity.this, "Failed "+e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }else{
            Toast.makeText(PostActivity.this, "Please choose a photo",
                    Toast.LENGTH_SHORT).show();
        }
    }

   
    
}




