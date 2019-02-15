package com.altamirano.fabricio.apptest.activities;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.altamirano.fabricio.apptest.R;
import com.altamirano.fabricio.libraryast.Utils.Tools;
import com.altamirano.fabricio.libraryast.Utils.ViewAnimation;

public class ExpandableBasicActivity extends AppCompatActivity {

    ImageButton bt_toggle_text;
    LinearLayout lyt_expand_text;
    NestedScrollView parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_basic);
        bt_toggle_text = findViewById(R.id.bt_toggle_text);
        lyt_expand_text = findViewById(R.id.lyt_expand_text);
        parent = findViewById(R.id.parent);
        this.lyt_expand_text.setVisibility(View.GONE);

        this.setListeners();
    }

    private void setListeners(){
        bt_toggle_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpandableBasicActivity.this.toggleSectionText(ExpandableBasicActivity.this.bt_toggle_text);
            }
        });
    }
    private void toggleSectionText(View view) {
        if (toggleArrow(view)) {
            ViewAnimation.expand(this.lyt_expand_text);
        } else {
            ViewAnimation.collapse(this.lyt_expand_text);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0.0f) {
            view.animate().setDuration(200).rotation(180.0f);
            return true;
        }
        view.animate().setDuration(200).rotation(0.0f);
        return false;
    }

    class Listener implements ViewAnimation.AnimListener {
        Listener() {
        }

        public void onFinish() {
            Tools.nestedScrollTo(ExpandableBasicActivity.this.parent, ExpandableBasicActivity.this.lyt_expand_text);
        }
    }
}
