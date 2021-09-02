package com.altamirano.fabricio.core.tools

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

class TaskRunner{
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    fun <T> executeAsync(preExecute: (() -> Unit)?, doInBackground: () -> T?, postExecute: ((T?) -> Unit)? ){
        executor.execute {
            preExecute?.let {
                handler.post(preExecute)
            }
           val result= doInBackground.invoke()
            handler.post {
                postExecute?.invoke(result)
            }
        }
    }
}

fun <T> asyncTask(preExecute: () -> Unit, doInBackground: () -> T?, postExecute: (T?) -> Unit ){
    TaskRunner().executeAsync(preExecute,doInBackground, postExecute)
}

fun <T> asyncTask(doInBackground: () -> T?, postExecute: (T?) -> Unit ){
    TaskRunner().executeAsync(null,doInBackground, postExecute)
}


fun <T> asyncTask(doInBackground: () -> T?){
    TaskRunner().executeAsync(null,doInBackground, null)
}