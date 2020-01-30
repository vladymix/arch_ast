package com.altamirano.fabricio.apptest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.altamirano.fabricio.apptest.R
import com.altamirano.fabricio.apptest.commons.Contact

class ContactsAdapter(context: Context,  objects: MutableList<Contact>) : ArrayAdapter<Contact>(context, 0, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view :View
        val holder:ViewHolderContact

        if(convertView==null){
            view = LayoutInflater.from(this.context).inflate(R.layout.item_contact,parent, false)
            holder = ViewHolderContact(view)
            view.tag = holder
        }else{
            view = convertView
            holder = convertView.tag as ViewHolderContact
        }

        getItem(position)?.let {
            holder.textView.text = it.name
        }

        return view
    }

    class ViewHolderContact(val view:View){
        val textView = view.findViewById<TextView>(R.id.itemName)
    }
}