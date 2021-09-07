package com.altamirano.fabricio.ast_test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.altamirano.fabricio.ast_test.R

class SimpleListAdapter(context: Context, resource: Int, objects: List<String>) : ArrayAdapter<String>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view :View
        if(convertView==null){
            view =  LayoutInflater.from(this.context).inflate(R.layout.item_list, parent, false);
        }else{
            view = convertView
        }

        view.findViewById<TextView>(R.id.text).text = getItem(position)

        return view
    }
}