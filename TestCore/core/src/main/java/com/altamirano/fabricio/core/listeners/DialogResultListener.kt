package com.altamirano.fabricio.core.listeners

import com.altamirano.fabricio.core.dialogs.PasswordDialog

interface DialogResultListener {
    fun onCorrectPassword(dialog: PasswordDialog, numberIntentsBefore:Int)
    fun onErrorPassword(dialog: PasswordDialog, numberIntents:Int)
}