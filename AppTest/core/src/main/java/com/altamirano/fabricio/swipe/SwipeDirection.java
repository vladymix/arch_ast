package com.altamirano.fabricio.swipe;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * Class containing a set of constant directions used throughout the package
 *
 * Created by wdullaer on 02.07.14.
 */
public enum SwipeDirection {
    // Constants
    DIRECTION_NORMAL_LEFT,
    DIRECTION_FAR_LEFT,
    DIRECTION_NORMAL_RIGHT,
    DIRECTION_FAR_RIGHT,
    DIRECTION_NEUTRAL;

    @NonNull
    public static List<SwipeDirection> getAllDirections(){
        return Arrays.asList(
                DIRECTION_FAR_LEFT,
                DIRECTION_FAR_RIGHT,
                DIRECTION_NEUTRAL,
                DIRECTION_NORMAL_LEFT,
                DIRECTION_NORMAL_RIGHT
        );
    }

    public boolean isLeft() {
        return this.equals(DIRECTION_NORMAL_LEFT) || this.equals(DIRECTION_FAR_LEFT);
    }

    public boolean isRight() {
        return this.equals(DIRECTION_NORMAL_RIGHT) || this.equals(DIRECTION_FAR_RIGHT);
    }
}