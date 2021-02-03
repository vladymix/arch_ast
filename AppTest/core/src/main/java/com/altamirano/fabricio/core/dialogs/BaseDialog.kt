package com.altamirano.fabricio.core.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import com.altamirano.fabricio.core.R

/**
 * @autor Created by Fabricio Altamirano on 13/3/18.
 */
class BaseDialog(context: Context, idLayout:Int) : Dialog(context) {
    init {
        this.window?.let {
            it.requestFeature(Window.FEATURE_NO_TITLE)
            it.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            )
            it.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            this.setContentView(idLayout)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}