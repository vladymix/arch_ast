package com.altamirano.fabricio.apptest.activities.dialogs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.altamirano.fabricio.apptest.R
import com.altamirano.fabricio.dialogs.ColorPickerDialog
import com.altamirano.fabricio.dialogs.DialogPassword
import com.altamirano.fabricio.dialogs.DialogResultListener

class ColorPickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_picker)

       var dialog  =DialogPassword(this, "1234", R.style.AppTheme_PopupOverlay)

        dialog.resultListener= object : DialogResultListener {
            override fun onCorrectPassword(dialog: DialogPassword, numberIntentsBefore: Int) {
                showDialog()
            }

            override fun onErrorPassword(dialog: DialogPassword, numberIntents: Int) {
               Toast.makeText(this@ColorPickerActivity, "Error number $numberIntents", Toast.LENGTH_LONG).show()
            }

        }
        dialog.show()
    }

    private fun showDialog(){
        ColorPickerDialog{it->this.onColorSelected(it)}.show(this.fragmentManager,"Hola")
    }

    private fun onColorSelected(colorPicker: ColorPickerDialog.ColorPicker?) {

    }
}
