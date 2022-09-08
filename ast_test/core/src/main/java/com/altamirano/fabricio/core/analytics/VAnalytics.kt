package com.altamirano.fabricio.core.analytics

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.contentValuesOf
import com.altamirano.fabricio.core.R
import com.altamirano.fabricio.core.dialogs.AstDialog
import com.altamirano.fabricio.core.tools.asyncTask
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class VAnalytics(val context: Context) {
    private val events = ArrayList<AnalyticsEvent>()
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
            if (sendCrasesh) {
                saveFile(paramThrowable)
            } else
                exitProcess(0)
        }
        sendPendingFiles()
    }

    fun autoNeedUpdate(context: Context, currentVersion:String){
        getVersion(packageName){ version, _ ->
            showDialogVersion(context,version, currentVersion, packageName)
        }
    }

    private fun showDialogVersion(context: Context,version: String, currentVersion:String,mPackage:String) {
        try {
            Log.i("VAnalytics","Version compare current version: $currentVersion with service version: $version")
            val lastVersion = version.replace(".", "").toInt()
            val versionName = currentVersion.replace(".", "").toInt()
            if (lastVersion > versionName) {
                val dialog = AstDialog(context)
                dialog.setTitle(context.getString(R.string.ast_msg_title_update))
                dialog.setMessage(context.getString(R.string.ast_msg_new_version))
                dialog.setPositiveButton(context.getString(R.string.ast_btn_accept)) { d, _ ->
                    gotoStore(context,mPackage)
                    d.dismiss()
                }

                dialog.setNegativeButton(context.getString(R.string.ast_btn_cancel)) { d, _ ->
                    d.dismiss()
                }
                dialog.show()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun gotoStore(context: Context,appPackageName: String) {
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    fun getVersion(mPackage:String, listener:(updateVersion:String,lastUpdate:String)->Unit){
        asyncTask(doInBackground = {
              val json = JSONObject()
            json.put("typeOS","android")
            json.put("package", mPackage)
            performPostCallResponse("https://apiservice.vladymix.es/analitycs/versionApp", json.toString())
        }, postExecute = {

            Log.i("VAnalytics","Version response $it")
            if(it.isNullOrEmpty())
                listener.invoke("1","")

            it?.let {
               val json= JSONObject(it)
                listener.invoke(json.getString("updateVersion"),json.getString("lastUpdate"))
            }
        })
    }

    private fun saveFile(paramThrowable: Throwable) {
        val message = paramThrowable.message ?: "Error not caught"
        val params = Log.getStackTraceString(paramThrowable)
        writeToFile("$message|$params")
    }

    private fun sendPendingFiles() {
        context.filesDir.listFiles()?.forEach {
            try {
                if (it.name.contains("error_")) {
                    val data = readFromFile(it.name).split("|")
                    sendDataException(data[0], data[1])
                    it.delete()
                }
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }

        }
    }

    private fun writeToFile(data: String) {
        try {
            val fileName = "error_" + SimpleDateFormat(
                "dd_MM_yyyy_HH_mm:ss",
                Locale.getDefault()
            ).format(Date())
            val outputStreamWriter =
                OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        } finally {
            exitProcess(0)
        }
    }

    private fun readFromFile(filename: String): String {
        var ret = ""
        try {
            val inputStream: InputStream? = context.openFileInput(filename)
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                val stringBuilder = java.lang.StringBuilder()
                while (bufferedReader.readLine().also { receiveString = it } != null) {
                    stringBuilder.append("\n").append(receiveString)
                }
                inputStream.close()
                ret = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            Log.e("login activity", "File not found: $e")
        } catch (e: IOException) {
            Log.e("login activity", "Can not read file: $e")
        }
        return ret
    }

    private fun sendDataException(message: String, params: String) {
        asyncTask {
            try {
                val sk = URLEncoder.encode(params, "UTF-8")
                val mess = URLEncoder.encode(message, "UTF-8")
                val device = URLEncoder.encode(getInfoDevice(), "UTF-8")

                val jsonBulder = StringBuilder()
                jsonBulder.append("{")
                jsonBulder.append("\"type\":")
                jsonBulder.append("\"$mess\"")

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
                    "https://apiservice.vladymix.es/exceptions/api/crashException",
                    jsonBulder
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun sendEvent(item: AnalyticsEvent) {
        try {
            if (item.isSending) {
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
                "https://apiservice.vladymix.es/analitycs/putEvent",
                jsonBulder, "PUT"
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
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
                "\nUSER : " + userName
        return info
    }

    private fun performPostCall(
        requestURL: String?, jsonBulder: StringBuilder, type: String = "POST"
    ): String? {
        val url: URL
        var response: String? = ""
        try {
            url = URL(requestURL)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.readTimeout = 15000
            conn.connectTimeout = 15000
            conn.requestMethod = type

            if (type == "PUT") {
                conn.doOutput = true
                val outPut = OutputStreamWriter(conn.outputStream)
                outPut.write(jsonBulder.toString())
                outPut.close()

            } else {
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
            if (responseCode == HttpsURLConnection.HTTP_OK) {

            } else {
                response = ""
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return response
    }

    fun putEvent(type: TypeEVENT, name: String) {
        events.add(AnalyticsEvent(packageName, type.toString(), name, false))
        asyncTask {
            for (item in events){
                sendEvent(item)
            }
        }
    }

    fun enableCrashes(isEnableCrash: Boolean) {
        this.sendCrasesh = isEnableCrash
    }

    fun sendException(paramThrowable: Throwable){
        val message = "Error recoverable ${paramThrowable.message}"
        val params = Log.getStackTraceString(paramThrowable)
        sendDataException(message, params)
    }

    enum class TypeEVENT {
        SCREEN,
        TOUCH,
        LOAD,
        NO_LOAD,
        LOCATION_ID,
        ITEMS,
        CONTENT,
        SEARCH_TERM,
        SHIPPING,
        DESTINATION,
        ORIGIN
    }

    private fun performPostCallResponse(
        requestURL: String?, json: String, type: String = "POST"
    ): String? {
        val url: URL
        var response: String? = ""
        try {
            url = URL(requestURL)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.readTimeout = 15000
            conn.connectTimeout = 15000
            conn.requestMethod = type

            if (type == "PUT") {
                conn.doOutput = true
                val outPut = OutputStreamWriter(conn.outputStream)
                outPut.write(json)
                outPut.close()

            } else {
                conn.doInput = true
                conn.doOutput = true

                val os: OutputStream = conn.outputStream
                val writer = BufferedWriter(
                    OutputStreamWriter(os, "UTF-8")
                )

                writer.write(json)
                writer.flush()
                writer.close()
                os.close()
            }

            val responseCode: Int = conn.responseCode
            if (responseCode == HttpsURLConnection.HTTP_OK) {
             val br =    BufferedReader(InputStreamReader(conn.inputStream))
                var line:String? = ""
                while ((br.readLine().also { line = it }) != null) {
                    response+=line;
                }
            } else {
                response = ""
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return response
    }

}