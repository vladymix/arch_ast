package com.altamirano.fabricio.apptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.altamirano.fabricio.core.dialogs.ColorPickerDialog
import com.altamirano.fabricio.core.dialogs.PasswordDialog
import com.altamirano.fabricio.core.listeners.DialogResultListener

class DialogsActivity : AppCompatActivity() {

    private var lastColor: ColorPickerDialog.ColorPicker? =
        ColorPickerDialog.ColorPicker(225, 221, 49, 246)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogs)

        findViewById<View>(R.id.btnDialogColor).setOnClickListener { view ->
            val dialog = ColorPickerDialog()
            dialog.colorInit = lastColor

            dialog.onColorChangeListener = { color ->
                color?.getAsColor()?.let {
                    view.setBackgroundColor(it)
                }
                lastColor = color
            }
            dialog.show(supportFragmentManager, "cache")
        }

        findViewById<View>(R.id.btnPassword).setOnClickListener {
            val dialog = PasswordDialog(this, "1234", R.style.AppThemePassword)

            dialog.onResultDialogListener = object : DialogResultListener{
                override fun onCorrectPassword(dialog: PasswordDialog, numberIntentsBefore: Int) {

                }

                override fun onErrorPassword(dialog: PasswordDialog, numberIntents: Int) {
                    Toast.makeText(this@DialogsActivity,"Error password", Toast.LENGTH_SHORT).show()
                }

            }
            dialog.show()
        }
    }
}
