package com.altamirano.fabricio.core.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.altamirano.fabricio.core.R;
import com.altamirano.fabricio.core.commons.OnBoardItem;
import com.altamirano.fabricio.core.tools.Tools;

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
        imageView.setImageResource(item.getImage());

        TextView tv_title=itemView.findViewById(R.id.tv_header);
        tv_title.setText(item.getIdTitle());
        tv_title.setTextColor(ContextCompat.getColor(tv_title.getContext(), item.getIdTextColor()));

        TextView tv_content=itemView.findViewById(R.id.tv_desc);
        tv_content.setText(item.getIdDescription());
        tv_content.setTextColor(ContextCompat.getColor(tv_content.getContext(), item.getIdTextColor()));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    public void deployColor(RelativeLayout ast_content, int position) {
        int color = list.get(position).getIdColor();
        if(color>0){
            ast_content.setBackgroundColor(ContextCompat.getColor(this.mContext, color));
            Tools.changeColorBar((Activity) ast_content.getContext(), color);
        }

       // ViewAnimation.changeBackgroundColor(ast_content, ContextCompat.getColor(this.mContext, color));

    }
}
