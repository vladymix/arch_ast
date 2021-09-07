package com.altamirano.fabricio.ast_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altamirano.fabricio.core.dialogs.AstDialog
import com.altamirano.fabricio.core.swipe.SwipeList
import com.altamirano.fabricio.core.tools.asyncTask

class MainActivity : AppCompatActivity() {
    lateinit var swipeList:SwipeList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeList = findViewById(R.id.swipeList)
        swipeList.adapter


    }
}