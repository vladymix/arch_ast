package com.altamirano.fabricio.testcore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.altamirano.fabricio.core.dialogs.AstDialog

class AstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        AstDialog(this).apply {
            this.setTitle("Hola")
            this.setMessage("Como estas")
        }.show()

    }
}