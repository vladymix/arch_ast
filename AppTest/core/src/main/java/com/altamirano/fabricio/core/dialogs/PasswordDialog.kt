package com.altamirano.fabricio.core.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.altamirano.fabricio.core.R
import com.altamirano.fabricio.core.listeners.DialogResultListener

//ic_launcher

class PasswordDialog(context: Context, private val password: String, theme: Int = android.R.style.Theme) :
        Dialog(context, theme) {

    private var input = ""
    private var intents = 0

    var onResultDialogListener: DialogResultListener? = null

    private lateinit var txt_mask: TextView

    init {
        this.setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this.window?.requestFeature(Window.FEATURE_ACTION_BAR)
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

        this.window?.getAttributes()?.windowAnimations = R.style.ASTDialogAnimation

        /*   this.window?.let {
                val wlp = it.attributes
                wlp?.gravity = Gravity.CENTER;
                wlp?.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
                it.setAttributes(wlp);
                it.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT
                )
            }


            this.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            this.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            this.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))*/


        (findViewById<ImageView>(R.id.ast_image)).setImageDrawable(this.context.packageManager.getApplicationIcon(this.context.applicationInfo))
        this.updateMask()

    }

    private fun onClickView(view: View) {
        when (view.id) {
            R.id.btn_ok -> {
                this.onCheckPassword()
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

    private fun onCheckPassword() {
        if (this.input.equals(this.password)) {
            onResultDialogListener?.onCorrectPassword(this, intents)
            this.dismiss()
        } else {
            intents++
            onResultDialogListener?.onErrorPassword(this, intents)
        }
    }

   private fun inputValue(v: Int) {
        if (this.input.length < this.password.length) {
            this.input += v
            updateMask();
        }

        if (this.input.length == this.password.length) {
            this.onCheckPassword()
            this.input = ""
            this.updateMask()
        }
    }

    private fun updateMask() {
        var inputMask = ""
        for (i in 1..input.length) {
            inputMask += "◉ "
        }

        for (i in 1..password.length - input.length) {
            inputMask += "◎ "
        }

        txt_mask.setText(inputMask)
    }

}