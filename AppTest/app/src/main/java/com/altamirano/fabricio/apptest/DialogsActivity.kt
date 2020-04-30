package com.altamirano.fabricio.apptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altamirano.fabricio.core.dialogs.ColorPickerDialog

class DialogsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogs)

        val dialog = ColorPickerDialog()
        dialog.show(supportFragmentManager, "cache")
    }
}
