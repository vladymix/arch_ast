package com.altamirano.fabricio.core.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.altamirano.fabricio.core.dialogs.ColorPickerDialog
import com.altamirano.fabricio.core.R
import com.altamirano.fabricio.libraryast.tools.inflate
import com.altamirano.fabricio.libraryast.tools.ternary

class ColorTempAdapter(val list:List<ColorPickerDialog.ColorPicker>, val onItemClick:(ColorPickerDialog.ColorPicker?)->Unit):RecyclerView.Adapter<ColorTempAdapter.ColorTempHolder>() {

    inner class ColorTempHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val view = itemView.findViewById<ImageView>(R.id.viewColor)

        fun bindValue(colorPicker: ColorPickerDialog.ColorPicker) {

            view.setOnClickListener{ onItemClick.invoke(colorPicker) }
            view.setColorFilter(colorPicker.getAsColor())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorTempHolder = ColorTempHolder(parent.inflate(R.layout.item_cache_color))

    override fun getItemCount(): Int = (list.size > 10).ternary(10, list.size)

    override fun onBindViewHolder(holder: ColorTempHolder, position: Int) {
        holder.bindValue(list[position])
    }


}