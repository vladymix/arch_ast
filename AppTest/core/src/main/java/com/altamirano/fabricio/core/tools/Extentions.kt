package com.altamirano.fabricio.libraryast.tools

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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