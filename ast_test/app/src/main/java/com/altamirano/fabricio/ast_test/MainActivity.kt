package com.altamirano.fabricio.ast_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.altamirano.fabricio.core.analytics.VAnalytics

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        VAnalytics.getInstance(this).putEvent(VAnalytics.TypeEVENT.TOUCH,"From ast")
        VAnalytics.getInstance(this).putEvent(VAnalytics.TypeEVENT.TOUCH,"From ast")
        VAnalytics.getInstance(this).putEvent(VAnalytics.TypeEVENT.TOUCH,"From ast")
        VAnalytics.getInstance(this).putEvent(VAnalytics.TypeEVENT.TOUCH,"From ast")
        VAnalytics.getInstance(this).putEvent(VAnalytics.TypeEVENT.TOUCH,"From ast")
        VAnalytics.getInstance(this).putEvent(VAnalytics.TypeEVENT.TOUCH,"From ast")

    }
}