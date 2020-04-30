package com.altamirano.fabricio.apptest.commons


import com.altamirano.fabricio.apptest.BoardActivity
import com.altamirano.fabricio.apptest.DialogsActivity
import com.altamirano.fabricio.apptest.R
import java.util.*

object AppTest {

    fun getListMenu():List<ItemMenu>{
        val array = ArrayList<ItemMenu>()
        array.add(ItemMenu(R.drawable.ic_bubble_chart,"Dialogs", DialogsActivity::class.java))
        array.add(ItemMenu(R.drawable.ic_bubble_chart,"On Board", BoardActivity::class.java))
        array.add(ItemMenu(android.R.drawable.ic_delete,"Hola"))
        array.add(ItemMenu(android.R.drawable.ic_delete,"Hola"))


        return array

    }
}