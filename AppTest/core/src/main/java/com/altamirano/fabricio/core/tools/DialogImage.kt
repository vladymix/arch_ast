package com.altamirano.fabricio.core.tools

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.altamirano.fabricio.core.R
import com.altamirano.fabricio.core.Utils.A
import com.altamirano.fabricio.core.dialogs.BaseDialog

/**
 * @autor Created by Fabricio Altamirano on 13/3/18.
 */
class DialogImage : DialogFragment() {
    private var btn_primary: String? = null
    private var btn_secundary: String? = null
    private val contains_image_center = false
    private lateinit var mDialog: Dialog
    private var imageCenter: Drawable?
    private var imageTitle: Drawable?
    private var message: String?
    private var onClickPrimay: View.OnClickListener? = null
    private var onClickSecundary: View.OnClickListener? = null
    private var title: String?

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDialog = BaseDialog(this.requireContext(), R.layout.ast_dialog_image_header)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(true)
        val v_imageCenter = mDialog.findViewById<ImageView>(R.id.image_center)
        val mTitle = mDialog.findViewById<TextView>(R.id.dg_tittle)
        val mMessage = mDialog.findViewById<TextView>(R.id.dg_message)
        val mSecundary = mDialog.findViewById<Button>(R.id.dg_btn_secundary)
        val mprimary = mDialog.findViewById<Button>(R.id.dg_btn_primary)
        mprimary.setOnClickListener(onClickPrimay)
        mprimary.text = btn_primary
        mSecundary.setOnClickListener(onClickSecundary)
        mSecundary.text = btn_secundary
        mTitle.text = A.StrEmpty
        mMessage.text = A.StrEmpty
        if (title != null) {
            mTitle.text = title
        }
        if (message != null) {
            mMessage.text = message
        }
        if (imageCenter != null) {
            v_imageCenter.setImageDrawable(imageCenter)
        } else {
            v_imageCenter.visibility = View.GONE
        }
        val v_imageTitle = mDialog.findViewById<ImageView>(R.id.image_title)
        if (imageTitle != null) {
            v_imageTitle.setImageDrawable(imageTitle)
        } else {
            v_imageTitle.visibility = View.GONE
        }
        return mDialog

      //  return AlertDialog.Builder(requireContext()).create()
    }




    fun setImageCenter(drawable: Drawable?): DialogImage {
        imageCenter = drawable
        return this
    }

    fun setImageTitle(drawable: Drawable?): DialogImage {
        imageTitle = drawable
        return this
    }

    fun setMessage(message: String?): DialogImage {
        this.message = message
        return this
    }

    fun setPrimaryButton(value: String?, onClick: View.OnClickListener?): DialogImage {
        btn_primary = value
        onClickPrimay = onClick
        return this
    }

    fun setSecundaryButton(value: String?, onClick: View.OnClickListener?): DialogImage {
        btn_secundary = value
        onClickSecundary = onClick
        return this
    }

    fun setTitle(title: String?): DialogImage {
        this.title = title
        return this
    }

    init {
        // need because fragment
        title = A.StrEmpty
        message = A.StrEmpty
        imageCenter = null
        imageTitle = null
    }
}