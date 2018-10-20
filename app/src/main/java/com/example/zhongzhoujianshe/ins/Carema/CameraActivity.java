package com.example.zhongzhoujianshe.ins.Carema;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.zhongzhoujianshe.ins.Home.HomeActivity;
import com.example.zhongzhoujianshe.ins.ImageProcess.BitmapStore;
import com.example.zhongzhoujianshe.ins.PostActivity;
import com.example.zhongzhoujianshe.ins.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//Camera activity to take photos and select photo from phone gallery
public class CameraActivity extends Activity implements SurfaceHolder.Callback {

    private Camera camera = null;
    private SurfaceView cameraSurfaceView = null;
    private SurfaceHolder cameraSurfaceHolder = null;
    private boolean previewing = false;
    RelativeLayout relativeLayout;

    private Button btnCapture = null;
    private ToggleButton btnFlash = null;
    private ImageButton btnGallery = null;
    private Button btnHome =null;

    //initialize
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // take photo
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        relativeLayout=(RelativeLayout) findViewById(R.id.containerImg);
        relativeLayout.setDrawingCacheEnabled(true);

        cameraSurfaceView = (SurfaceView) findViewById(R.id.surfaceView1);

        cameraSurfaceHolder = cameraSurfaceView.getHolder();
        cameraSurfaceHolder.setKeepScreenOn(true);
        cameraSurfaceHolder.addCallback(this);

        btnCapture = (Button)findViewById(R.id.button1);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(shutterCallback,
                        rawCallback,
                        pictureCallback);

            }
        });

        btnHome = (Button) findViewById(R.id.button_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        // open FlashLight
        btnFlash = (ToggleButton) findViewById(R.id.button_flash);
        btnFlash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The FlashLight is enabled
                    try{
                        camera = Camera.open();
                        Camera.Parameters parameters;
                        parameters = camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();

                    } catch(Exception ex){
                        Log.e("Failed",ex.getMessage());
                    }

                } else {
                    // The FlashLight is disabled
                    try{
                        Camera.Parameters parameters;
                        parameters = camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.release();
                    } catch(Exception ex){
                        Log.e("Failed",ex.getMessage());
                    }
                }
            }
        });

        // select photo from Gallery
        btnGallery = (ImageButton) findViewById(R.id.button_gallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                final int ACTIVITY_SELECT_IMAGE = 0;
                startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
            }
        });
    }


    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback(){
        @Override
        public void onShutter(){

        }
    };

    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

    Camera.PictureCallback pictureCallback = new Camera.PictureCallback(){
        @Override
        public void onPictureTaken(byte[] data, Camera camera){
            Bitmap cameraBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            //save
            File storagePath = new File(Environment.getExternalStorageDirectory()
                    + "/DCIM/100ANDRO/");
            storagePath.mkdirs();

            File newImage = new File(storagePath, Long.toString(System.currentTimeMillis()) + ".jpg");
            try {
                FileOutputStream out = new FileOutputStream(newImage);
                cameraBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                out.flush();
                out.close();
            } catch(FileNotFoundException e) {
                Log.d("In Saving File", e + "");
            } catch(IOException e) {
                Log.d("In Saving File", e + "");
            }

            // save photo to the gallery
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(newImage)));

            // Pass the new image to the next edit view
            BitmapStore.setBitmap(cameraBitmap);
            Intent intent = new Intent();
            intent.setClass(CameraActivity.this, EditPhotoActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void surfaceChanged(SurfaceHolder surfaceholder, int format, int width, int height) {
        if(previewing) {
            camera.stopPreview();
            previewing = false;
        }
        try {
            // set camera and preview size
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewSize(640, 480);
            parameters.setPictureSize(640, 480);
            if(this.getResources().getConfiguration().orientation
                    != Configuration.ORIENTATION_LANDSCAPE) {
                camera.setDisplayOrientation(90);
            }

            camera.setParameters(parameters);
            camera.setPreviewDisplay(cameraSurfaceHolder);
            camera.startPreview();
            previewing = true;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    //information of exception
    @Override
    public void surfaceCreated(SurfaceHolder surfaceholder) {
        try {
            camera = Camera.open();
        } catch(RuntimeException e) {
            Toast.makeText(getApplicationContext(), "Device Camera is " +
                    "not working, please try after sometime.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }


    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        if(result == RESULT_OK){
            Uri selectedImage = data.getData();

            try{
                Bitmap newSelectedImage = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(selectedImage));
                BitmapStore.setBitmap(newSelectedImage);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }

            Intent intent = new Intent();
            intent.setClass(CameraActivity.this, EditPhotoActivity.class);
            startActivity(intent);
        }
    }
}
