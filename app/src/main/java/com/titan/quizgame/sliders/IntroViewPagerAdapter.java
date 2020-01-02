package com.titan.quizgame.sliders;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

public class IntroViewPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private int [] screens;
    private Activity context;

    public IntroViewPagerAdapter(Activity context, int [] screens) {

        this.context = context;
        this.screens = screens;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(screens[position], container, false);
        container.addView(view);
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

