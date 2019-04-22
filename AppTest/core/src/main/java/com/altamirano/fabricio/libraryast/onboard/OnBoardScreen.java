package com.altamirano.fabricio.libraryast.onboard;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.altamirano.fabricio.libraryast.R;

import java.util.ArrayList;

/**
 * Created by fabricio Altamirano on 12/9/18.
 */
public class OnBoardScreen extends RelativeLayout {

    private LinearLayout pager_indicator;
    private ViewPager onboard_pager;
    private Button btn_get_started;
    // private OnBoardAdapter mAdapter;
    private PagerAdapter mAdapter;
    int previous_pos = 0;
    private int dotsCount;
    private ImageView[] dots;

    public OnBoardScreen(Context context) {
        super(context);
        init();
    }

    public OnBoardScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OnBoardScreen(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.on_board_layout, this);
        onboard_pager = findViewById(R.id.pager_introduction);
        pager_indicator = findViewById(R.id.viewPagerCountDots);
        btn_get_started = findViewById(R.id.btn_get_started);
    }


    public void setAdapter(PagerAdapter adapter) {
        this.mAdapter = adapter;
        this.configByAdapter();
    }

    public void setEndButton(String labelButton, OnClickListener action) {
        btn_get_started.setText(labelButton);
        btn_get_started.setOnClickListener(action);
    }


    public void setAdapterDefault(ArrayList<OnBoardItem> onBoardItems) {
        this.mAdapter = new OnBoardAdapter(this.getContext(), onBoardItems);
        this.configByAdapter();
    }

    private void configByAdapter() {
        this.configureDonuts();
        this.configureOnBoardPage();
    }

    private void configureOnBoardPage() {

        onboard_pager.setAdapter(mAdapter);
        onboard_pager.setCurrentItem(0);
        onboard_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // Change the current position intimation
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(OnBoardScreen.this.getContext(), R.drawable.non_selected_item_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(OnBoardScreen.this.getContext(), R.drawable.selected_item_dot));

                int pos = position + 1;

                if (pos == dotsCount && previous_pos == (dotsCount - 1))
                    show_animation();
                else if (pos == (dotsCount - 1) && previous_pos == dotsCount)
                    hide_animation();

                previous_pos = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void configureDonuts() {
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this.getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.non_selected_item_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.selected_item_dot));
    }

    public void show_animation() {
        Animation show = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_up_anim);

        btn_get_started.startAnimation(show);

        show.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                btn_get_started.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btn_get_started.clearAnimation();
            }

        });


    }

    public void hide_animation() {
        Animation hide = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_down_anim);

        btn_get_started.startAnimation(hide);

        hide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btn_get_started.clearAnimation();
                btn_get_started.setVisibility(View.GONE);

            }

        });


    }
}
