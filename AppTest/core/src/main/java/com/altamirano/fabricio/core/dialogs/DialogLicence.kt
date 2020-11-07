package com.altamirano.fabricio.core.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.altamirano.fabricio.core.R
/*
*Redefine string ast_licences
* */
class DialogLicence : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.ast_layout_licence, container, false)
        view.findViewById<View>(R.id.ast_content).setOnClickListener {  }
        view.findViewById<View>(R.id.astBackButton).setOnClickListener {
            fragmentManager?.popBackStackImmediate()
            this.dismiss()
        }
        dialog?.setCancelable(false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return view
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getString("open_url")?.let {

        }
    }

    fun showFull(supportFragmentManager: FragmentManager) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(android.R.id.content, this).addToBackStack("null").commit()
    }
}