package com.altamirano.fabricio.libraryast;

import android.graphics.Bitmap;

/**
 * @autor Created by Fabricio Altamirano on 7/3/18.
 */

public class ImageTools {

    public static int getDpi(Bitmap bitmap){
        if(bitmap!=null){
            return bitmap.getDensity();
        }else{
            return -1;
        }
    }
}
