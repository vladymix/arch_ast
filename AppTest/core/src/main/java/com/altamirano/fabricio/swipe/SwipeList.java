package com.altamirano.fabricio.swipe;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class SwipeList extends ListView {

    private SwipeActionAdapter mAdapter;

    public SwipeList(Context context) {
        super(context);
    }

    public SwipeList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSwipeActionListener(SwipeActionAdapter.SwipeActionListener listener) {
        this.mAdapter.setSwipeActionListener(listener);
    }

    public SwipeActionAdapter setAdapter(BaseAdapter adaper) {
        this.mAdapter = new SwipeActionAdapter(adaper);
        this.mAdapter.setListView(this);
        super.setAdapter(this.mAdapter);
        return this.mAdapter;
    }

    public SwipeActionAdapter addBackground(SwipeDirection key, int resId){
        this.mAdapter.addBackground(key, resId);
        return this.mAdapter;
    }

}
