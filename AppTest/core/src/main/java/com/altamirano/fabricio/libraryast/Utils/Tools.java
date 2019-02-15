package com.altamirano.fabricio.libraryast.Utils;

import android.support.v4.widget.NestedScrollView;
import android.view.View;

public class Tools {

    public static void nestedScrollTo(final NestedScrollView nestedScrollView, final View view) {
        nestedScrollView.post(new Runnable() {
            public void run() {
                nestedScrollView.scrollTo(500, view.getBottom());
            }
        });
    }
}
