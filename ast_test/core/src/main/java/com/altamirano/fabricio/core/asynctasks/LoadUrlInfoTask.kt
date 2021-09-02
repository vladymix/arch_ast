package com.altamirano.fabricio.core.asynctasks

import android.util.Log
import com.altamirano.fabricio.core.commons.InfoUrl
import com.altamirano.fabricio.core.listeners.UrlResponse
import com.altamirano.fabricio.core.tools.CoreAsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

/**
 * @autor Created by Fabricio Altamirano on 27/3/18.
 */
class LoadUrlInfoTask(private var url: String?, private val listener: UrlResponse?) : CoreAsyncTask<Void, Void?, InfoUrl?>() {

    override fun doInBackground(vararg params: Void): InfoUrl? {
        val infoUrl = InfoUrl()
        try {
            if (url != null && url!!.length > 4) {
                if (!url!!.startsWith("http://") || !url!!.startsWith("https://")) {
                    url = "http://$url"
                }
            } else {
                return null
            }
            val url = URL(url)
            val `in` = BufferedReader(InputStreamReader(url.openStream()))
            var inputLine: String
            var index: Int
            var our: Int
            while (`in`.readLine().also { inputLine = it } != null) {
                Log.w("LoadUrlInfoTask", inputLine)
                if (inputLine.contains("<link rel=\"icon\"") && infoUrl.url_icon == null) {
                    index = inputLine.indexOf("href") + 6
                    our = inputLine.indexOf("\"", index)
                    infoUrl.url_icon = inputLine.substring(index, our)
                } else if (inputLine.contains("<title>") && infoUrl.get_title() == null) {
                    index = inputLine.indexOf("<title>") + 7
                    our = inputLine.indexOf("</title>", index)
                    infoUrl.set_title(inputLine.substring(index, our))
                } else if (inputLine.contains("<description>") && infoUrl.description == null) {
                    index = inputLine.indexOf("<description>") + 13
                    our = inputLine.indexOf("</description>", index)
                    infoUrl.set_description(inputLine.substring(index, our))
                }
            }
            `in`.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return infoUrl
    }

    override fun onPostExecute(result: InfoUrl?) {
        listener?.result(result)
    }

    override fun onPreExecute() {

    }
}