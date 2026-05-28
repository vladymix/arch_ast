package com.altamirano.fabricio.core

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.altamirano.fabricio.core.dialogs.DialogBrowser
import com.altamirano.fabricio.core.dialogs.DialogLicence

class AstAboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ast_activity_about)
        findViewById<View>(R.id.astbtnabout).setOnClickListener { this.onShowAbout() }
        findViewById<View>(R.id.btnShowPolicy).setOnClickListener { this.onShowPolicy() }
        findViewById<View>(R.id.astbtnshowcode).setOnClickListener { this.onShowCode() }
        findViewById<View>(R.id.astOnBack).setOnClickListener { this.onBackPressed() }
        this.loadVersionCode()
    }

    private fun loadVersionCode() {
        try {
            val pk = this.packageManager.getPackageInfo(packageName, 0)
            findViewById<TextView>(R.id.tvVersion)?.text = pk.versionName
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun onShowCode() {
        val dialog = DialogLicence()
        dialog.showFull(supportFragmentManager)
    }

    private fun onShowPolicy() {
        val dialog = DialogBrowser()
        dialog.arguments = Bundle().apply {
            putString("open_url", resources.getString(R.string.ast_url_privacity))
            putBoolean("show_toolbar", false)
        }
        dialog.showFull(supportFragmentManager)
    }

    private fun onShowAbout() {
        try {
            //Open the specific App Info page:
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            var packageName = this.packageName
            this.let { ctk ->
                ctk.packageManager?.getPackageInfo(ctk.packageName, 0)
                    ?.let { packageName = it.packageName }
            }
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No application can handle this request.", Toast.LENGTH_LONG)
                .show()
            e.printStackTrace()
        }
    }
}