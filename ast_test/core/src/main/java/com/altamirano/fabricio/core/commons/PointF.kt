package com.altamirano.fabricio.core.commons

import android.graphics.Rect

class PointF(var x: Float, var y: Float) {

    var radius: Float = 18f

    fun isNearby(dx: Float, dy: Float, tolerance: Float): Boolean {
        val despYB = this.y + tolerance
        val despYT = this.y - tolerance

        val despXB = this.x + tolerance
        val despXT = this.x - tolerance
        return dy in despYT..despYB && dx in despXT..despXB
    }

    fun fixPosition(dx: Float, dy: Float, maxWith: Int, maxHeight: Int) {
        if (radius > 0) {
            if (dx < radius) {
                x = radius
            } else if (dx + radius > maxWith) {
                x = maxWith - radius
            } else {
                x = dx
            }

            if (dy < radius) {
                y = radius
            } else if (dy + radius >= maxHeight) {
                y = maxHeight - radius
            } else {
                y = dy
            }
        } else {
            x = dx
            y = dy
        }
    }

    fun fixPositionToCrop(dx: Float, dy: Float, target: Rect) {

        if (dy < target.top) {
            y = target.top.toFloat()
        } else if (dy > target.bottom) {
            y = target.bottom.toFloat()
        } else {
            y = dy
        }

        if (dx < target.left) {
            x = target.left.toFloat()
        } else if (dx > target.right) {
            x = target.right.toFloat()
        } else {
            x = dx
        }


    }
}