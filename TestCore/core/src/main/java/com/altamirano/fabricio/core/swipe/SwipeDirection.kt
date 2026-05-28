package com.altamirano.fabricio.core.swipe

import java.util.*

/**
 * Class containing a set of constant directions used throughout the package
 *
 * Created by wdullaer on 02.07.14.
 */
enum class SwipeDirection {
    // Constants
    DIRECTION_NORMAL_LEFT, DIRECTION_FAR_LEFT, DIRECTION_NORMAL_RIGHT, DIRECTION_FAR_RIGHT, DIRECTION_NEUTRAL;

    val isLeft: Boolean
        get() = this == DIRECTION_NORMAL_LEFT || this == DIRECTION_FAR_LEFT
    val isRight: Boolean
        get() = this == DIRECTION_NORMAL_RIGHT || this == DIRECTION_FAR_RIGHT

    companion object {
        val allDirections: List<SwipeDirection>
            get() = Arrays.asList(
                DIRECTION_FAR_LEFT,
                DIRECTION_FAR_RIGHT,
                DIRECTION_NEUTRAL,
                DIRECTION_NORMAL_LEFT,
                DIRECTION_NORMAL_RIGHT
            )
    }
}