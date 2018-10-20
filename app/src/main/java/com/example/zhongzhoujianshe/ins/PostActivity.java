package com.example.zhongzhoujianshe.ins;


import android.app.Dialog;
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
import com.example.zhongzhoujianshe.ins.Home.HomeActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.Set;

public class PostActivity extends AppCompatActivity {

    public ImageView imageview = null;
    private Bitmap rawBitmap = null;
    private Bitmap thumbnail = null;
    
    private Button btnPost = null;
    private Button btnBack = null;
    private String filePath = "";


    private final static int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;

    Set<BluetoothDevice> pairedDevices;
    // temp arraylist

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imageview = (ImageView) findViewById(R.id.thumbnail);

        // get the bitmap from file
        Intent intent = getIntent();
        filePath = intent.getStringExtra("post_img");

        rawBitmap = BitmapFactory.decodeFile(filePath);

        thumbnail = rawBitmap;
        imageview.setImageBitmap(thumbnail);

        btnBack = (Button) findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnPost = (Button) findViewById(R.id.button_post);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createInstagramIntent(filePath);
            }
        });

    }

    

    private void createInstagramIntent(String filePath){
        Intent instagram = new Intent(android.content.Intent.ACTION_SEND);
        instagram.setType("image/*");
        File file = new File(filePath);
        Uri uri = Uri.fromFile(file);
        instagram.putExtra(Intent.EXTRA_STREAM, uri);
        instagram.putExtra(Intent.EXTRA_TEXT, "YOUR TEXT TO SHARE IN INSTAGRAM");
        instagram.setPackage("com.instagram.android");


        startActivity(Intent.createChooser(instagram, "Share to"));
    }

   
    
}




