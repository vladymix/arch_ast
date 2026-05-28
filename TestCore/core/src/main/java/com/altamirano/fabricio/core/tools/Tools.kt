package com.altamirano.fabricio.core.tools

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import java.util.*

/**
 * @author  Created by fabricio Altamirano on 7/3/18.
 */
object Tools {

    fun isNullOrEmpty(source: ArrayList<*>?): Boolean {
        return source == null || source.size == 0
    }

    fun isNullOrEmpty(source: List<*>?): Boolean {
        return source == null || source.size == 0
    }

    fun isNullOrEmpty(value: CharSequence?): Boolean {
        return value == null || value.length == 0 || isNullOrEmpty(value.toString())
    }

    fun isNullOrEmpty(value: String?): Boolean {
        return value == null || value.length == 0 || value.trim { it <= ' ' }.length == 0
    }

    fun isNullOrEmpty(value: EditText?): Boolean {
        return value == null || isNullOrEmpty(value.text)
    }

    fun isNullOrEmpty(value: TextView?): Boolean {
        return value == null || isNullOrEmpty(value.text)
    }

    fun nestedScrollTo(nestedScrollView: NestedScrollView, view: View) {
        nestedScrollView.post { nestedScrollView.scrollTo(500, view.bottom) }
    }

    fun changeColorBar(activity: Activity, color: Int) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = activity.resources.getColor(color)
        }
    }

    fun changeColorBarValue(activity: Activity, color: Int) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = color
        }
    }
}