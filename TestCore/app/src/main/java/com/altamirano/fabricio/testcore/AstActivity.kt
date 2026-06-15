package com.altamirano.fabricio.testcore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.altamirano.fabricio.core.AstAboutActivity
import com.altamirano.fabricio.core.analytics.VAnalytics
import com.altamirano.fabricio.core.dialogs.AstDialog

class AstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        VAnalytics.getInstance(this).autoNeedUpdate(this,"1.0")
/*        AstDialog(this).apply {
            this.setTitle("Hola")
            this.setMessage("Como estas")
        }.show()*/

        //startActivity(Intent(this, AstAboutActivity::class.java))

    }
}