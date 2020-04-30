package com.altamirano.fabricio.apptest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altamirano.fabricio.apptest.adapters.ItemMenuAdapter
import com.altamirano.fabricio.apptest.commons.AppTest
import com.altamirano.fabricio.apptest.commons.ItemMenu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.recycler.adapter = ItemMenuAdapter(AppTest.getListMenu()){this.onItemSelected(it)}
    }

    private fun onItemSelected(it: ItemMenu) {
        it.clazz?.let{
            startActivity(Intent(this@MainActivity, it))
        }

    }
}
