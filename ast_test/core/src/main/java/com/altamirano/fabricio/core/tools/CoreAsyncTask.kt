package com.altamirano.fabricio.core.tools

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

abstract  class CoreAsyncTask<Params, Progress, Result> {

    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    protected abstract fun onPreExecute()
    protected abstract fun doInBackground(vararg params: Params): Result
    protected abstract fun onPostExecute(result: Result)

    fun execute(){
        executor.execute {
            handler.post {
                onPreExecute()
            }
          val result = doInBackground()
            handler.post {
                onPostExecute(result)
            }
        }
    }
}