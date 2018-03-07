package com.altamirano.fabricio.libraryast;

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
}
