package com.altamirano.fabricio.apptest.commons

import android.app.Activity

data class ItemMenu(val idImage:Int, val text:String, val clazz:(Class<out Activity>)?=null)