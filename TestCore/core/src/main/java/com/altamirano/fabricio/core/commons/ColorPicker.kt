package com.altamirano.fabricio.core.commons

import android.graphics.Color

class ColorPicker(var alfa: Int, var red: Int, var green: Int, var blue: Int) {

    fun getAsColor(): Int {
        return Color.argb(alfa, red, green, blue)
    }

    fun getColor(percentage: Float): Int {
        return Color.argb((alfa * percentage).toInt(), red, green, blue)
    }

    override fun equals(other: Any?): Boolean {
        val target = (other as ColorPicker)

        return target.alfa == this.alfa && target.red == this.red && target.green == this.green
                && target.blue == this.blue
    }

    override fun hashCode(): Int {
        var result = alfa
        result = 31 * result + red
        result = 31 * result + green
        result = 31 * result + blue
        return result
    }
}