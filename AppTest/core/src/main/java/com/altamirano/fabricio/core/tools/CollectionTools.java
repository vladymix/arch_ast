package com.altamirano.fabricio.core.tools;

import java.util.ArrayList;
import java.util.List;

/**
 *@autor Created by fabricio Altamirano on 7/3/18.
 */

public class CollectionTools {

    public static boolean isNullOrEmpty(ArrayList<?> source){
        return source==null || source.size() == 0;
    }

    public static boolean isNullOrEmpty(List<?> source){
        return source==null || source.size() == 0;
    }
}
