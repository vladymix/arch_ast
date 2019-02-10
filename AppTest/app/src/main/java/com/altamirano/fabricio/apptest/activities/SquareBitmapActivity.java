package com.altamirano.fabricio.apptest.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.altamirano.fabricio.apptest.R;
import com.altamirano.fabricio.libraryast.ImageTools;

public class SquareBitmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_bitmap);

        ImageView imageView =  findViewById(R.id.image_view);

        //extraemos el drawable en un bitmap
        Drawable originalDrawable = getResources().getDrawable(R.drawable.bitmap_to_circle);

        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        imageView.setImageBitmap(ImageTools.getSquareBitmap(originalBitmap));
    }
}
