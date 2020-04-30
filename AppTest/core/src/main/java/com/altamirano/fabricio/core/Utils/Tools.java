package com.altamirano.fabricio.core.Utils;

import android.view.View;

import androidx.core.widget.NestedScrollView;

public class Tools {

    public static void nestedScrollTo(final NestedScrollView nestedScrollView, final View view) {
        nestedScrollView.post(new Runnable() {
            public void run() {
                nestedScrollView.scrollTo(500, view.getBottom());
            }
        });
    }
}
