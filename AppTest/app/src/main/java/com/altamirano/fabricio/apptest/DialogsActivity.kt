package com.altamirano.fabricio.apptest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.altamirano.fabricio.core.AstAboutActivity
import com.altamirano.fabricio.core.commons.ColorPicker
import com.altamirano.fabricio.core.dialogs.AstDialog
import com.altamirano.fabricio.core.dialogs.ColorPickerDialog
import com.altamirano.fabricio.core.dialogs.PasswordDialog
import com.altamirano.fabricio.core.listeners.DialogResultListener
import com.altamirano.fabricio.core.tools.DialogImage

class DialogsActivity : AppCompatActivity() {

    private var lastColor: ColorPicker? = ColorPicker(225, 221, 49, 246)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogs)

        findViewById<View>(R.id.btnAbout).setOnClickListener{this.onViewAbout()}
        findViewById<View>(R.id.btnDialogImage).setOnClickListener{this.onShowDialogImage()}
        findViewById<View>(R.id.btnDialogProgress).setOnClickListener{this.onShowDialogProgress()}

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
            val dialog = PasswordDialog( this,"1234", R.style.AppThemePassword)

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

    private fun onShowDialogProgress() {
       AstDialog(this).apply {
           this.setMessage("Mensaje para prueba de cuadro de dialogo")
           this.setTitle("¿Que deseas hacer?")
           this.setPositiveButton("Nada")
           this.setNegativeButton("Cancelar")
           this.setViewProgress(true)
       }.show()
    }

    private fun onShowDialogImage() {
        AstDialog(this).apply {
            this.setMessage("Mensaje para prueba de cuadro de dialogo")
            this.setTitle("¿Que deseas hacer?")
        }.show()
    }

    private fun onViewAbout() {
        startActivity(Intent(this, AstAboutActivity::class.java))
    }
}
