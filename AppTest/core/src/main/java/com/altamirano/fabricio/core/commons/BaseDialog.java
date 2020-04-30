package com.altamirano.fabricio.core.commons;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.altamirano.fabricio.core.R;

/**
 * @autor Created by Fabricio Altamirano on 13/3/18.
 */

public class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context);
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        this.setContentView(R.layout.custom_image_dialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
