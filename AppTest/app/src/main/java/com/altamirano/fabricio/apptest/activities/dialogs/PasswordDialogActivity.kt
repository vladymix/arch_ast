package com.altamirano.fabricio.apptest.activities.dialogs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.altamirano.fabricio.apptest.R
import com.altamirano.fabricio.dialogs.DialogPassword
import com.altamirano.fabricio.dialogs.DialogResultListener

class PasswordDialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_dialog)
        findViewById<View>(R.id.btn).setOnClickListener { this.showDialog() }

        this.showDialog()
    }

    private fun showDialog() {

        val dialog = DialogPassword(this, "1234", R.style.AppTheme_PopupOverlay)

        dialog.resultListener = object : DialogResultListener {
            override fun onCorrectPassword(dialog: DialogPassword, numberIntentsBefore: Int) {

            }

            override fun onErrorPassword(dialog: DialogPassword, numberIntents: Int) {
                Toast.makeText(this@PasswordDialogActivity, "Error number $numberIntents", Toast.LENGTH_LONG).show()
            }
        }
        dialog.show()
    }
}
