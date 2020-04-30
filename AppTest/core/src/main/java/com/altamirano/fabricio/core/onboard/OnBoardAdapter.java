package com.altamirano.fabricio.core.onboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.altamirano.fabricio.core.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabricio Altamirano on 12/9/18.
 */
public class OnBoardAdapter  extends PagerAdapter {

    private Context mContext;
    private List<OnBoardItem> list;

    public OnBoardAdapter(Context context,ArrayList<OnBoardItem> items ){
        this.mContext = context;
        this.list = items;
    }
    @Override
    public int getCount() {
         return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.on_board_item, container, false);

        OnBoardItem item = list.get(position);

        ImageView imageView = itemView.findViewById(R.id.iv_onboard);
        imageView.setImageResource(item.getImageID());

        TextView tv_title=itemView.findViewById(R.id.tv_header);
        tv_title.setText(item.getTitle());

        TextView tv_content=itemView.findViewById(R.id.tv_desc);
        tv_content.setText(item.getDescription());
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
