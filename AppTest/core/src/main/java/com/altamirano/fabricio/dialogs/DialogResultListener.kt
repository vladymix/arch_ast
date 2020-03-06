package com.altamirano.fabricio.dialogs

interface DialogResultListener {
    fun onCorrectPassword(dialog:DialogPassword, numberIntentsBefore:Int)
    fun onErrorPassword(dialog:DialogPassword, numberIntents:Int)
}