package com.altamirano.fabricio.ast_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altamirano.fabricio.core.dialogs.AstDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dialog = AstDialog(this)
        dialog.setMessage("Dialogo de prueba")
        dialog.setTitle("Titulo")
        dialog.show()
    }
}