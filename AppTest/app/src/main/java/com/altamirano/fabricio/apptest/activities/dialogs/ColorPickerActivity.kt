package com.altamirano.fabricio.apptest.activities.dialogs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.altamirano.fabricio.apptest.R
import com.altamirano.fabricio.dialogs.ColorPickerDialog

class ColorPickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_picker)
        findViewById<View>(R.id.btn).setOnClickListener { this.showDialog() }
        this.showDialog()
    }

    private fun showDialog() {
        ColorPickerDialog(this) { this.onColorSelected(it) }.show(this.fragmentManager, "Hola")
    }

    private fun onColorSelected(colorPicker: ColorPickerDialog.ColorPicker?) {

        colorPicker?.let {
            findViewById<View>(R.id.content).setBackgroundColor(it.getAsColor())
        }
    }
}
