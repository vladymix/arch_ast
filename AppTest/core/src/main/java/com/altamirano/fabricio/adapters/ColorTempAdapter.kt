package com.altamirano.fabricio.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.altamirano.fabricio.dialogs.ColorPickerDialog
import com.altamirano.fabricio.libraryast.R
import com.altamirano.fabricio.libraryast.tools.inflate

class ColorTempAdapter(val list:List<ColorPickerDialog.ColorPicker>, val onItemClick:(ColorPickerDialog.ColorPicker?)->Unit):RecyclerView.Adapter<ColorTempAdapter.ColorTempHolder>() {

    inner class ColorTempHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val view = itemView.findViewById<ImageView>(R.id.viewColor)

        fun bindValue(colorPicker: ColorPickerDialog.ColorPicker) {

            view.setOnClickListener{ onItemClick.invoke(colorPicker) }
            view.setColorFilter(colorPicker.getAsColor())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorTempHolder = ColorTempHolder(parent.inflate(R.layout.item_cache_color))

    override fun getItemCount(): Int = (list.size > 10).ternario(10, list.size)

    override fun onBindViewHolder(holder: ColorTempHolder, position: Int) {
        holder.bindValue(list[position])
    }

    fun Boolean.ternario(correct:Int, incorrect:Int):Int{
        if(this){
            return correct
        }
        return incorrect
    }
}