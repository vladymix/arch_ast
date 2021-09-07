package com.altamirano.fabricio.core.swipe

import android.content.Context
import android.util.AttributeSet
import android.widget.BaseAdapter
import android.widget.ListView
import com.altamirano.fabricio.core.swipe.SwipeActionAdapter.SwipeActionListener

class SwipeList : ListView {
    private var mAdapter: SwipeActionAdapter? = null

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    fun setSwipeActionListener(listener: SwipeActionListener?) {
        mAdapter!!.setSwipeActionListener(listener)
    }

    fun setAdapter(adaper: BaseAdapter): SwipeActionAdapter {
        mAdapter = SwipeActionAdapter(adaper)
        mAdapter?.let {
            it.setListView(this)
            super.setAdapter(it)
        }
        return mAdapter!!
    }

    fun addBackground(key: SwipeDirection, resId: Int): SwipeActionAdapter? {
        mAdapter!!.addBackground(key, resId)
        return mAdapter
    }
}