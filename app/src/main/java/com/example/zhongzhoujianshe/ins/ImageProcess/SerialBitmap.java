package com.example.zhongzhoujianshe.ins.ImageProcess;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;


public class SerialBitmap implements Serializable {

    private static final long serialVersionUID = 1L;

    public String filename;
    public long timestamp;
    public byte[] blob;


    public SerialBitmap(Bitmap img,String aFile,long aTimestamp){
        blob = bmpToBlob(img);
        filename=aFile;
        timestamp=aTimestamp;
    }

    public byte[] bmpToBlob(Bitmap bmp) {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,0,baos);
        blob=baos.toByteArray();
        try {
            baos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return blob;
    }
}
