package com.titan.quizgame.sliders;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.titan.quizgame.R;
import com.titan.quizgame.sliders.models.Slider;

public class IntroViewPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Slider [] screens;
    private Activity context;

    public IntroViewPagerAdapter(Activity context, Slider[] screens) {

        this.context = context;
        this.screens = screens;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(screens[position].getScreen(), container, false);
        container.addView(view);


        ((RelativeLayout) view.findViewById(R.id.rlt_lyt_screen)).setBackgroundColor(context.getResources().getColor(screens[position].getBackGround()));
        ((TextView) view.findViewById(R.id.txt_description)).setText(screens[position].getText());


        return view;
    }

    @Override
    public int getCount() {
        return screens.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View v = (View) object;
        container.removeView(v);
    }

    @Override
    public boolean isViewFromObject(View v, Object object) {
        return v == object;
    }
}

