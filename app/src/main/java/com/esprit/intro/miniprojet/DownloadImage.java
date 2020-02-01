package com.esprit.intro.miniprojet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadImage extends AsyncTask<String, Void, Bitmap>


{
    private String TAG = "DownloadImage";

    private Bitmap downloadImageBitmap(String sUrl) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new URL(sUrl).openStream();
            // Download Image from URL
            bitmap = BitmapFactory.decodeStream(inputStream);
            // Decode  Bitmap
            inputStream.close();
        } catch (Exception e) {
            Log.d(TAG, "Exception 1, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return downloadImageBitmap(params[0]);
    }

    protected void onPostExecute(Bitmap result) {


     //   AccueilDocteur2.saveImage( result, "my_image.png");


    }



    }
