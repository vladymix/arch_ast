package com.altamirano.fabricio.core.tools;

import android.widget.EditText;
import android.widget.TextView;

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
}
