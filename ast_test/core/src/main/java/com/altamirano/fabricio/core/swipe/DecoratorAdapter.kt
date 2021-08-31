package com.altamirano.fabricio.core.swipe

import android.database.DataSetObserver
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

open class DecoratorAdapter(val adapter: BaseAdapter) : BaseAdapter() {
    override fun getCount(): Int {
        return adapter.count
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        return adapter.getView(position, convertView, parent)
    }

    override fun getItem(position: Int): Any {
        return adapter.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return adapter.getItemId(position)
    }

    override fun areAllItemsEnabled(): Boolean {
        return adapter.areAllItemsEnabled()
    }

    override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
        return adapter.getDropDownView(position, convertView, parent)
    }

    override fun getItemViewType(position: Int): Int {
        return adapter.getItemViewType(position)
    }

    override fun getViewTypeCount(): Int {
        return adapter.viewTypeCount
    }

    override fun hasStableIds(): Boolean {
        return adapter.hasStableIds()
    }

    override fun isEmpty(): Boolean {
        return adapter.isEmpty
    }

    override fun isEnabled(position: Int): Boolean {
        return adapter.isEnabled(position)
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun notifyDataSetInvalidated() {
        adapter.notifyDataSetInvalidated()
    }

    override fun registerDataSetObserver(observer: DataSetObserver) {
        adapter.registerDataSetObserver(observer)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        adapter.unregisterDataSetObserver(observer)
    }
}