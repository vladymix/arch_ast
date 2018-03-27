package com.altamirano.fabricio.libraryast.controllers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

import com.altamirano.fabricio.libraryast.R;

import static android.view.WindowManager.LayoutParams.FLAG_BLUR_BEHIND;

/**
 * @autor Created by Fabricio Altamirano on 13/3/18.
 */

public class CustomImageDialog extends Dialog {


    public CustomImageDialog(@NonNull Context context) {
        super(context);
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        this.setContentView(R.layout.custom_image_dialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
