package com.example.zhongzhoujianshe.ins.Carema;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zhongzhoujianshe.ins.ImageProcess.BitmapStore;
import com.example.zhongzhoujianshe.ins.ImageProcess.CropProcessing;
import com.example.zhongzhoujianshe.ins.R;

public class CropActivity extends AppCompatActivity {

    private Bitmap newBitmap = null;
    private Bitmap cropBitmap = null;
    private CropProcessing crop = null;

    private Button btnOK = null;
    private Button btnBack = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        crop = (CropProcessing) findViewById(R.id.crop_view);
        Intent intent = getIntent();
        //Get crop image
        if (intent != null) {
            newBitmap = BitmapStore.getBitmap();
            crop.setImageBitmap(newBitmap);
        }
        //ok with the crop image
        btnOK = (Button) findViewById(R.id.button_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropBitmap = crop.getCroppedImage();
                crop.setImageBitmap(cropBitmap);
            }
        });
        //back to the previous page
        btnBack = (Button) findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cropBitmap != null) {
                    BitmapStore.setBitmap(cropBitmap);
                    Intent intent = new Intent(CropActivity.this, EditPhotoActivity.class);
                    startActivity(intent);
                } else {
                    CropActivity.this.finish();
                }
            }
        });
    }


}

