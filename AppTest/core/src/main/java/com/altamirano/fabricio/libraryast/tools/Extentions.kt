package com.altamirano.fabricio.libraryast.tools

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(idLayout:Int): View {
    return LayoutInflater.from(this.context!!).inflate(idLayout, this,false)
}