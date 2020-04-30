package com.altamirano.fabricio.apptest.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altamirano.fabricio.apptest.R
import com.altamirano.fabricio.apptest.commons.ItemMenu
import com.altamirano.fabricio.libraryast.tools.inflate

class ItemMenuAdapter(val list: List<ItemMenu>, val listener: (ItemMenu) -> Unit) :
    RecyclerView.Adapter<ItemMenuAdapter.ItemMenuHolder>() {

    inner class ItemMenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemMenu: ItemMenu) {
            this.textMenu.text = itemMenu.text
            this.imageMenu.setImageResource(itemMenu.idImage)
            this.itemView.setOnClickListener {
                listener.invoke(itemMenu)
            }
        }

        val textMenu: TextView = itemView.findViewById(R.id.textMenu)
        val imageMenu: ImageView = itemView.findViewById(R.id.imageMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMenuHolder =
        ItemMenuHolder(
            parent.inflate(
                R.layout.layout_item_menu
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ItemMenuHolder, position: Int) {
        holder.bind(list[position])
    }
}