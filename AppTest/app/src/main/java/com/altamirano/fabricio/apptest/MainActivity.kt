package com.altamirano.fabricio.apptest

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.altamirano.fabricio.apptest.adapters.ItemMenuAdapter
import com.altamirano.fabricio.apptest.commons.AppTest
import com.altamirano.fabricio.apptest.commons.ItemMenu
import com.altamirano.fabricio.core.dialogs.AstDialog
import com.altamirano.fabricio.core.dialogs.BaseDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.recycler.adapter = ItemMenuAdapter(AppTest.getListMenu()){this.onItemSelected(it)}

        findViewById<View>(R.id.btnTest).setOnClickListener { this.onTesting(it) }
    }

    private fun onTesting(view: View?) {
        AstDialog(this).apply {
            this.setCancelable(false)
            this.setMessage("Mensaje de pruebas")
            this.setPositiveButton("Aceptar"
            ) { dialog, which ->
                Toast.makeText(this@MainActivity, "Hello", Toast.LENGTH_LONG).show()
            }
        }.show()

    }

    private fun onItemSelected(it: ItemMenu) {
        it.clazz?.let{
            startActivity(Intent(this@MainActivity, it))
        }

    }
}
