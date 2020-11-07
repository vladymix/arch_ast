package com.altamirano.fabricio.core.tools;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 *@author  Created by fabricio Altamirano on 7/3/18.
 */

public class Tools {

    public static boolean isNullOrEmpty(ArrayList<?> source){
        return source==null || source.size() == 0;
    }

    public static boolean isNullOrEmpty(List<?> source){
        return source==null || source.size() == 0;
    }

    public static boolean isNullOrEmpty(CharSequence value){
        return value== null || value.length() == 0 ||  isNullOrEmpty(value.toString());
    }

    public static boolean isNullOrEmpty(String value){
        return value==null || value.length() == 0 || value.trim().length() == 0;
    }

    public static boolean isNullOrEmpty(EditText value){
        return value==null || isNullOrEmpty(value.getText());
    }

    public static boolean isNullOrEmpty(TextView value){
        return value==null || isNullOrEmpty(value.getText());
    }

    public static void nestedScrollTo(final NestedScrollView nestedScrollView, final View view) {
        nestedScrollView.post(new Runnable() {
            public void run() {
                nestedScrollView.scrollTo(500, view.getBottom());
            }
        });
    }
    public static void changeColorBar(Activity activity, int color){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(activity.getResources().getColor(color));
        }
    }

    public static void changeColorBarValue(Activity activity, int color){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }
}
