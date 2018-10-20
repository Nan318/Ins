package com.example.zhongzhoujianshe.ins.Carema;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zhongzhoujianshe.ins.ImageProcess.BitmapStore;
import com.example.zhongzhoujianshe.ins.ImageProcess.CropView;
import com.example.zhongzhoujianshe.ins.R;

public class CropActivity extends AppCompatActivity {

    private Bitmap newBitmap = null;
    private Bitmap cropBitmap = null;
    private CropView cropview = null;

    private Button btnOK = null;
    private Button btnReturn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        cropview = (CropView) findViewById(R.id.crop_view);
        Intent intent = getIntent();
        //Get crop image
        if (intent != null) {
            newBitmap = BitmapStore.getBitmap();
            cropview.setImageBitmap(newBitmap);
        }
        //ok with the crop image
        btnOK = (Button) findViewById(R.id.button_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropBitmap = cropview.getCroppedImage();
                cropview.setImageBitmap(cropBitmap);
            }
        });
        //back to the previous page
        btnReturn = (Button) findViewById(R.id.button_return);
        btnReturn.setOnClickListener(new View.OnClickListener() {
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

