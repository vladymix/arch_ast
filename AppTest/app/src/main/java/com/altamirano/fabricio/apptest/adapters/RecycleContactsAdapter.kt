package com.altamirano.fabricio.apptest.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.altamirano.fabricio.apptest.R
import com.altamirano.fabricio.apptest.commons.Contact

class RecycleContactsAdapter(val items: List<Contact>, val listener: (Contact) -> Unit) : RecyclerView.Adapter<RecycleContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(parent.inflate(R.layout.item_contact, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView.findViewById(R.id.itemName)

        fun bind(item: Contact, listener: (Contact) -> Unit) = with(itemView) {
            textView.text = item.name
            setOnClickListener { listener(item) }
        }
    }
}

private fun ViewGroup?.inflate(idRes: Int, b: Boolean): View {
    return LayoutInflater.from(this!!.context).inflate(idRes,this,b)
}

