package com.altamirano.fabricio.core.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.altamirano.fabricio.core.R
import com.altamirano.fabricio.core.commons.OnBoardItem
import java.util.*

/**
 * Created by fabricio Altamirano on 12/9/18.
 */
class OnBoardAdapter(private val mContext: Context, items: ArrayList<OnBoardItem>) :
    PagerAdapter() {
    private val list: List<OnBoardItem>
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView =
            LayoutInflater.from(mContext).inflate(R.layout.on_board_item, container, false)
        val (image, idTitle, idDescription) = list[position]
        val imageView = itemView.findViewById<ImageView>(R.id.iv_onboard)
        imageView.setImageResource(image)
        val tv_title = itemView.findViewById<TextView>(R.id.tv_header)
        tv_title.setText(idTitle)
        val tv_content = itemView.findViewById<TextView>(R.id.tv_desc)
        tv_content.setText(idDescription)
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

    init {
        list = items
    }
}