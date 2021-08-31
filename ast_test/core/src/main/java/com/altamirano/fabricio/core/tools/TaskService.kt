package com.altamirano.fabricio.core.tools

import android.os.AsyncTask

class TaskService<T>(val pre: (() -> Unit)?, val background: () -> T?, val post: ((T?) -> Unit)?) :
        AsyncTask<Void, Void, T?>() {

    override fun onPostExecute(result: T?) {
        post?.invoke(result)
    }

    override fun onPreExecute() {
        pre?.invoke()
    }

    override fun doInBackground(vararg params: Void?): T? {
        return background.invoke()
    }
}


fun <T> asyncTask(preExecute: () -> Unit, doInBackground: () -> T?, postExecute: (T?) -> Unit ){
    TaskService(
        preExecute,
        doInBackground,
        postExecute
    ).execute()
}

fun <T> asyncTask(doInBackground: () -> T?, postExecute: (T?) -> Unit ){
    TaskService(
        null,
        doInBackground,
        postExecute
    ).execute()
}


fun <T> asyncTask(doInBackground: () -> T?){
    TaskService(null, doInBackground, null).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
}