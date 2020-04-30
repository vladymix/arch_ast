package com.altamirano.fabricio.core.listeners

import com.altamirano.fabricio.core.dialogs.DialogPassword

interface DialogResultListener {
    fun onCorrectPassword(dialog: DialogPassword, numberIntentsBefore:Int)
    fun onErrorPassword(dialog: DialogPassword, numberIntents:Int)
}