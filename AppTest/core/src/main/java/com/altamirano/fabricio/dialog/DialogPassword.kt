package com.altamirano.fabricio.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.altamirano.fabricio.libraryast.R

//ic_launcher
class DialogPassword(context: Context, val Password: String) :
        Dialog(context) {

    private var input = ""

    private lateinit var txt_mask: TextView

    init {
        this.setCancelable(false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ast_dialog_pin)

        txt_mask = findViewById(R.id.txt_mask)

        findViewById<LinearLayout>(R.id.btn_0).setOnClickListener { this.onClickView(it) }
        findViewById<LinearLayout>(R.id.btn_1).setOnClickListener { this.onClickView(it) }
        findViewById<LinearLayout>(R.id.btn_2).setOnClickListener { this.onClickView(it) }
        findViewById<LinearLayout>(R.id.btn_3).setOnClickListener { this.onClickView(it) }
        findViewById<LinearLayout>(R.id.btn_4).setOnClickListener { this.onClickView(it) }
        findViewById<LinearLayout>(R.id.btn_5).setOnClickListener { this.onClickView(it) }
        findViewById<LinearLayout>(R.id.btn_6).setOnClickListener { this.onClickView(it) }
        findViewById<LinearLayout>(R.id.btn_7).setOnClickListener { this.onClickView(it) }
        findViewById<LinearLayout>(R.id.btn_8).setOnClickListener { this.onClickView(it) }
        findViewById<LinearLayout>(R.id.btn_9).setOnClickListener { this.onClickView(it) }

        findViewById<LinearLayout>(R.id.btn_del).setOnClickListener { this.onClickView(it) }
        findViewById<LinearLayout>(R.id.btn_ok).setOnClickListener { this.onClickView(it) }

        this.window?.let {
            val wlp = it.attributes
            wlp?.gravity = Gravity.CENTER;
            wlp?.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
            it.setAttributes(wlp);
            it.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
            )
        }


        (findViewById<ImageView>(R.id.ast_image)).setImageDrawable( this.context.packageManager.getApplicationIcon(this.context.applicationInfo))
        this.updateMask()

    }

    private fun onClickView(view: View) {
        when (view.id) {
            R.id.btn_ok -> {
                if (this.input.equals(this.Password)) this.dismiss() else Toast.makeText(
                        this.context,
                        "ERROR",
                        Toast.LENGTH_LONG
                ).show()
            }
            R.id.btn_del -> {
                this.input = ""
                this.updateMask()
            }
            R.id.btn_0 -> {
                this.inputValue(0)
            }
            R.id.btn_1 -> {
                this.inputValue(1)
            }
            R.id.btn_2 -> {
                this.inputValue(2)
            }
            R.id.btn_3 -> {
                this.inputValue(3)
            }
            R.id.btn_4 -> {
                this.inputValue(4)
            }
            R.id.btn_5 -> {
                this.inputValue(5)
            }
            R.id.btn_6 -> {
                this.inputValue(6)
            }
            R.id.btn_7 -> {
                this.inputValue(7)
            }
            R.id.btn_8 -> {
                this.inputValue(8)
            }
            R.id.btn_9 -> {
                this.inputValue(9)
            }
        }
    }

    fun inputValue(v: Int) {
        if (this.input.length < this.Password.length) {
            this.input += v
            updateMask();
        }

        if (this.input.length == this.Password.length) {
            if (this.input.equals(this.Password)) this.dismiss()
        }
    }

    fun updateMask() {

        var inputMask = ""
        for (i in 1..input.length) {
            inputMask += "◉ "
        }

        for (i in 1..Password.length - input.length) {
            inputMask += "◎ "
        }

        txt_mask.setText(inputMask)
    }


}