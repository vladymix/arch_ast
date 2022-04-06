package com.altamirano.fabricio.core.analytics

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import com.altamirano.fabricio.core.tools.asyncTask
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

class VAnalytics(val context: Context) {
    private val events= ArrayList<AnalyticsEvent>()
    private var sendCrasesh: Boolean = false
    var userName: String = ""
    private var versionName: String = "not_found"
    private var versionCode: Int = 0
    private val packageName: String

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: VAnalytics? = null

        fun getInstance(context: Context): VAnalytics {
            if (instance == null) {
                instance = VAnalytics(context)
            }
            return instance!!
        }
    }

    init {
        packageName = context.packageName
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = pInfo.versionName
            versionCode = pInfo.versionCode

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        Thread.setDefaultUncaughtExceptionHandler { _, paramThrowable ->
            paramThrowable.printStackTrace()
            if (sendCrasesh)
                sendException(paramThrowable)

        }
    }

    private fun sendException(paramThrowable: Throwable) {
        asyncTask {
            try {
                val message = paramThrowable.message ?: "Error not caught"
                val params = Log.getStackTraceString(paramThrowable)

                val sk = URLEncoder.encode(params, "UTF-8")
                val device = URLEncoder.encode(getInfoDevice(), "UTF-8")

                val jsonBulder = StringBuilder()
                jsonBulder.append("{")
                jsonBulder.append("\"type\":")
                jsonBulder.append("\"$message\"")

                jsonBulder.append(",")

                jsonBulder.append("\"version\":")
                jsonBulder.append("\"${versionName}\"")

                jsonBulder.append(",")

                jsonBulder.append("\"stack\":")
                jsonBulder.append("\"$sk\"")

                jsonBulder.append(",")

                jsonBulder.append("\"device\":")
                jsonBulder.append("\"$device\"")

                jsonBulder.append(",")

                jsonBulder.append("\"id_package\":")
                jsonBulder.append("\"${packageName}\"")

                jsonBulder.append("}")

                performPostCall(
                    "https://apiservice.vladymix.es/exceptions/api/logger",
                    jsonBulder
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                System.exit(0)
            }
        }
    }

    fun putEvent(type: TypeEVENT, name:String){
        events.add(AnalyticsEvent(packageName,type.toString(),name, false))
        asyncTask {
            events.forEach {
                sendEvent(it)
            }
        }
    }

    private fun sendEvent(item:AnalyticsEvent)
    {
        try {
            if(item.isSending){
                return
            }
            item.isSending = true

            val jsonBulder = StringBuilder()
            jsonBulder.append("{")
            jsonBulder.append("\"package\":")
            jsonBulder.append("\"${item.mPackage}\"")

            jsonBulder.append(",")

            jsonBulder.append("\"event\":")
            jsonBulder.append("\"${item.event}\"")

            jsonBulder.append(",")

            jsonBulder.append("\"name\":")
            jsonBulder.append("\"${item.name}\"")

            jsonBulder.append("}")

            performPostCall(
                "https://apiservice.vladymix.es/exceptions/api/putEvent",
                jsonBulder, "PUT"
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun enableCrashes(isEnableCrash: Boolean) {
        this.sendCrasesh = isEnableCrash
    }

    private fun getInfoDevice(): String {
        val info = "VERSION.RELEASE : " + Build.VERSION.RELEASE +
                "\nVERSION_NAME : " + versionName +
                "\nVERSION_CODE : " + versionCode +
                "\nVERSION.INCREMENTAL : " + Build.VERSION.INCREMENTAL +
                "\nVERSION.SDK.NUMBER : " + Build.VERSION.SDK_INT +
                "\nBOARD : " + Build.BOARD +
                "\nBOOTLOADER : " + Build.BOOTLOADER +
                "\nBRAND : " + Build.BRAND +
                "\nDISPLAY : " + Build.DISPLAY +
                "\nFINGERPRINT : " + Build.FINGERPRINT +
                "\nHARDWARE : " + Build.HARDWARE +
                "\nHOST : " + Build.HOST +
                "\nID : " + Build.ID +
                "\nMANUFACTURER : " + Build.MANUFACTURER +
                "\nMODEL : " + Build.MODEL +
                "\nPRODUCT : " + Build.PRODUCT +
                "\nTAGS : " + Build.TAGS +
                "\nTIME : " + Build.TIME +
                "\nTYPE : " + Build.TYPE +
                "\nUNKNOWN : " + Build.UNKNOWN +
                "\nUSER : " + Build.USER
        return info
    }


    private fun performPostCall(
        requestURL: String?,jsonBulder:StringBuilder, type:String = "POST"
    ): String? {
        val url: URL
        var response: String? = ""
        try {
            url = URL(requestURL)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.readTimeout = 15000
            conn.connectTimeout = 15000
            conn.requestMethod = type

            if(type== "PUT"){
                conn.doOutput = true
               val outPut =  OutputStreamWriter(conn.outputStream)
                outPut.write(jsonBulder.toString())
                outPut.close()

            }else{
                conn.doInput = true
                conn.doOutput = true

                val os: OutputStream = conn.outputStream
                val writer = BufferedWriter(
                    OutputStreamWriter(os, "UTF-8")
                )

                writer.write(jsonBulder.toString())
                writer.flush()
                writer.close()
                os.close()
            }

            val responseCode: Int = conn.responseCode
            if (responseCode == HttpsURLConnection.HTTP_OK && type != "PUT") {
                var line: String?
                val br = BufferedReader(InputStreamReader(conn.getInputStream()))
                while (br.readLine().also { line = it } != null) {
                    response += line
                }
            } else {
                response = ""
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return response
    }

    enum class TypeEVENT{
        SCREEN, TOUCH, LOAD,LOCATION_ID,ITEMS,CONTENT,SEARCH_TERM,SHIPPING,DESTINATION,ORIGIN
    }
}