package com.altamirano.fabricio.apptest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.altamirano.fabricio.apptest.R;
import com.altamirano.fabricio.apptest.commons.MenuItem;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<MenuItem> {

    public MenuAdapter(@NonNull Context context, @NonNull List<MenuItem> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_item_menu, parent, false);
        }

        MenuItem item =  getItem(position);

        if(item!=null){
          ImageView img_menu =  convertView.findViewById(R.id.img_menu);
          TextView menu_text = convertView.findViewById(R.id.menu_text);
            menu_text.setText(item.getTitle());
            img_menu.setImageResource(item.getImageId());
        }

        return convertView;
    }
}
