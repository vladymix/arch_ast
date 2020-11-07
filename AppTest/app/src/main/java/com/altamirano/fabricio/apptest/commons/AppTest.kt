package com.altamirano.fabricio.apptest.commons


import com.altamirano.fabricio.apptest.BoardActivity
import com.altamirano.fabricio.apptest.CodeActivity
import com.altamirano.fabricio.apptest.DialogsActivity
import com.altamirano.fabricio.apptest.R
import java.util.*

object AppTest {

    fun getListMenu():List<ItemMenu>{
        val array = ArrayList<ItemMenu>()
        array.add(ItemMenu(R.drawable.ic_dialog,"Dialogs", DialogsActivity::class.java))
        array.add(ItemMenu(R.drawable.ic_on_board,"On Board", BoardActivity::class.java))
        array.add(ItemMenu(R.drawable.ic_terminal_sharp,"Snippet code", CodeActivity::class.java))
        return array

    }
}