package com.altamirano.fabricio.core.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import com.altamirano.fabricio.core.R
import com.altamirano.fabricio.libraryast.tools.ternary

class AstDialog(context: Context) : Dialog(context), DialogInterface {


    private var viewAsProgress: Boolean = false
    lateinit var dialog: AlertDialog
    private var buttonPositive: ConfigButton? = null
    private var buttonNegative: ConfigButton? = null
    private var mMessage: String? = null
    private var mTitle: String? = null

    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.ast_dialog)
    }

    override fun setTitle(title: CharSequence?) {
        this.mTitle = title?.toString()
    }

    override fun setTitle(titleId: Int) {
        this.mTitle = this.context.getString(titleId)
    }

    fun setMessage(value: Int) {
        this.mMessage = this.context.getString(value)
        this.mMessage?.let {
            this.findViewById<TextView>(R.id.astMessage)?.text = it
        }
    }

    fun setMessage(value: CharSequence?) {
        this.mMessage = value?.toString()

        this.mMessage?.let {
            this.findViewById<TextView>(R.id.astMessage)?.text = it
        }
    }

    override fun getWindow(): Window? {
        val winds = super.getWindow()
        winds?.setBackgroundDrawableResource(android.R.color.transparent)
        return winds
    }

    fun setPositiveButton(
        @StringRes textId: Int,
        listener: DialogInterface.OnClickListener? = null
    ) {
        buttonPositive = ConfigButton(context.getString(textId), listener)
    }

    fun setPositiveButton(textId: String, listener: DialogInterface.OnClickListener? = null) {
        buttonPositive = ConfigButton(textId, listener)
    }

    fun setNegativeButton(
        @StringRes textId: Int,
        listener: DialogInterface.OnClickListener? = null
    ) {
        buttonNegative = ConfigButton(context.getString(textId), listener)
    }

    fun setNegativeButton(textId: String, listener: DialogInterface.OnClickListener? = null) {
        buttonNegative = ConfigButton(textId, listener)
    }

    override fun show() {
        this.configureView()
        super.show()

    }

    fun setViewProgress(viewProgress: Boolean) {
        this.viewAsProgress = viewProgress
    }

    private fun configureView() {
        this.mTitle?.let {
            this.findViewById<TextView>(R.id.astTitle)?.text = it
        }

        this.mMessage?.let {
            this.findViewById<TextView>(R.id.astMessage)?.text = it
        }

        this.findViewById<Button>(R.id.astBtnSecundary)?.setOnClickListener {
            this.dismiss()
        }

        this.findViewById<Button>(R.id.astBtnPrimary)?.setOnClickListener {
            this.dismiss()
        }

        buttonNegative?.let { cb ->
            this.findViewById<Button>(R.id.astBtnSecundary)?.let {
                it.visibility = View.VISIBLE
                it.text = cb.title
                it.setOnClickListener {
                    cb.listener?.onClick(this, DialogInterface.BUTTON_NEGATIVE)
                    this.dismiss()
                }
            }
        }

        buttonPositive?.let { cb ->
            this.findViewById<Button>(R.id.astBtnPrimary)?.let {
                it.visibility = View.VISIBLE
                it.text = cb.title
                it.setOnClickListener {
                        cb.listener?.onClick(this, DialogInterface.BUTTON_POSITIVE)
                        this.dismiss()
                    }
            }


        }

        if (buttonNegative == null && buttonPositive == null) {
            this.findViewById<View>(R.id.astButtons).visibility = View.GONE
        }

        if (this.mTitle.isNullOrEmpty()) {
            this.findViewById<TextView>(R.id.astTitle)?.visibility = View.GONE
        }

        if (this.mMessage.isNullOrEmpty()) {
            this.findViewById<TextView>(R.id.astMessage)?.visibility = View.GONE
        }

        this.findViewById<View>(R.id.astProgress)?.visibility =
            viewAsProgress.ternary(View.VISIBLE, View.GONE)
    }

    internal data class ConfigButton(
        val title: String,
        val listener: DialogInterface.OnClickListener?
    )
}