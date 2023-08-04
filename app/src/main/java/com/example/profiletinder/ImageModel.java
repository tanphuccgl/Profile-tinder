package com.example.profiletinder;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageModel {
    private String bitmap;
    private int requestCode;
    private String key; // Trường key mới

    public ImageModel(String bitmap, int requestCode, String key) {
        this.bitmap = bitmap;
        this.requestCode = requestCode;
        this.key = key;
    }

    public String getBitmap() {
        return bitmap;
    }


    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
