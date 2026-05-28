package com.altamirano.fabricio.core.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.altamirano.fabricio.core.R

/*
dialog.arguments = Bundle().apply {
            putString("code_licence", "http://apps.vladymix.es/")
            putBoolean("show_toolbar", false)
        }
To arguments
String  open_url
Boolean show_toolbar
* */
class DialogBrowser : DialogFragment() {

    private var webView:WebView?=null
    private var astwrapurl:View?=null
    private var progressIndicator:View?=null
    private var asttitle: TextView?=null
    private var url:String?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.ast_browser, container, false)
        dialog?.setCancelable(false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        view.findViewById<View>(R.id.ast_content).setOnClickListener {  }

        webView = view.findViewById(R.id.ast_webView)
        asttitle = view.findViewById(R.id.asttitleweb)
        webView?.webViewClient = CustomCliente()

        astwrapurl = view.findViewById(R.id.astwrapurl)
        progressIndicator = view.findViewById(R.id.progressIndicator)

        return view
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView?.settings?.javaScriptEnabled = true
       // webView?.settings?.setAppCacheEnabled(false)
        webView?.settings?.javaScriptCanOpenWindowsAutomatically = true

        arguments?.getString("open_url")?.let {
            url = it
            webView?.loadUrl(it)
            asttitle?.text = it
        }

        arguments?.getBoolean("show_toolbar")?.let {
            if(it){
                astwrapurl?.visibility = View.VISIBLE
            }else{
                astwrapurl?.visibility = View.GONE
            }
        }
    }

    fun showFull(supportFragmentManager: FragmentManager) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(android.R.id.content, this).addToBackStack(null).commit()
    }


    private  inner class CustomCliente: WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressIndicator?.visibility = View.GONE
            view?.title?.let {
                asttitle?.text = it
            }
        }
    }


   /* private fun loadMetaData(){
        if(url!=null){
            asyncTask(doInBackground = {
                return@asyncTask   UrlTools.getUrlSource(url)
            }, postExecute = {

                if(it!=null){
                    if(it.contains("<meta name=\"theme-color\" content=\"")){
                        val start = it.indexOf("<meta name=\"theme-color\" content=\"")+ "<meta name=\"theme-color\" content=\"".length
                        val end = it.indexOf("\"",start)
                        val color = it.substring(start, end)
                        if(color.isNotEmpty()){
                            Tools.changeColorBarValue(this.activity, Color.parseColor(color))
                            astwrapurl?.setBackgroundColor(Color.parseColor(color))
                        }
                    }
                }
            })
        }
    }*/
}