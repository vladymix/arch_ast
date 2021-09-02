package com.altamirano.fabricio.core.tools

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

fun ViewGroup.inflate(idLayout:Int): View {
    return LayoutInflater.from(this.context!!).inflate(idLayout, this,false)
}

// if (!response.isSuccessful()) "fail" else response.body().string()
fun <T> Boolean.ternary(isTrue:T, isFalse:T): T {
    return when(this){
        true-> isTrue
        else -> isFalse
    }
}


@Override
fun Context.getColorById(idColor:Int):Int{
    return ContextCompat.getColor(this, idColor)
}