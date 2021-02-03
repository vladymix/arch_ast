package com.altamirano.fabricio.core.tools

import com.altamirano.fabricio.core.listeners.UrlResponse
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

/**
 * @autor Created by Fabricio Altamirano on 27/3/18.
 */
object UrlTools {

    fun getInfo(url: String?, listnerUrlResponse: UrlResponse?) {}
    @Throws(IOException::class)
    fun getUrlSource(site: String?): String {
        //GNU Public, from ZunoZap Web Browser
        val url = URL(site)
        val urlc = url.openConnection()
        val `in` = BufferedReader(
            InputStreamReader(
                urlc.getInputStream(), "UTF-8"
            )
        )
        var inputLine: String?
        val a = StringBuilder()
        while (`in`.readLine().also { inputLine = it } != null) a.append(inputLine)
        `in`.close()
        return a.toString()
    }
}