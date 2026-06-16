package com.altamirano.fabricio.core.tools

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.concurrent.Executors

class TaskRunner{
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    fun <T> executeAsync(preExecute: (() -> Unit)?, doInBackground: () -> T?, postExecute: ((T?) -> Unit)? ){
        preExecute?.invoke()
        // 2. Ahora sí, mandamos el trabajo pesado al hilo de fondo
        executor.execute {
            val result = doInBackground.invoke()
            // 3. Pasamos el resultado al hilo principal
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