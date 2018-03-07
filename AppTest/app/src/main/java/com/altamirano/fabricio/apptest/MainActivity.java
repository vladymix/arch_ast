package com.altamirano.fabricio.apptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.altamirano.fabricio.libraryast.ImageTools;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView view = findViewById(R.id.image_app);
        ImageTools.getDpi(view.getDrawingCache());
    }
}
