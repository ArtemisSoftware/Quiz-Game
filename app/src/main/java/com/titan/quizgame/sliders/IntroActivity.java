package com.titan.quizgame.sliders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.titan.quizgame.R;
import com.titan.quizgame.sliders.models.Slider;

public class IntroActivity extends AppCompatActivity {

    LinearLayout Layout_bars;
    TextView[] bottomBars;
    private Slider[] screens;
    Button Skip, Next;
    ViewPager vp;
    IntroViewPagerAdapter introViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        vp = (ViewPager) findViewById(R.id.view_pager);
        Layout_bars = (LinearLayout) findViewById(R.id.layoutBars);
        Skip = (Button) findViewById(R.id.skip);
        Next = (Button) findViewById(R.id.next);

        initIntroSlider();

        ColoredBars(0);
    }

    private void initIntroSlider() {

        /*
        PreferenceManager preferenceManager = new PreferenceManager(this);


        if (!preferenceManager.FirstLaunch()) {
            launchMain();
            finish();
        }
        */




        Skip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                skip(v);
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                next(v);

            }
        });

        int[] colorsInactive = getResources().getIntArray(R.array.dot_on_page_not_active);
        int[] colorsActive = getResources().getIntArray(R.array.dot_on_page_active);

        screens = new Slider[]{

                new Slider("Screen 0", colorsActive[0], colorsInactive[0],  R.layout.intro_screen, R.color.screen1),
                new Slider("Screen 1", colorsActive[1], colorsInactive[1],  R.layout.intro_screen, R.color.screen2),
                new Slider("Screen 2", colorsActive[2], colorsInactive[2],  R.layout.intro_screen, R.color.screen3)
        };

        introViewPagerAdapter = new IntroViewPagerAdapter(this, screens);
        vp.setAdapter(introViewPagerAdapter);

        vp.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    public void next(View v) {
        int i = getItem(+1);
        if (i < screens.length) {
            vp.setCurrentItem(i);
        } else {
            launchMain();
        }
    }

    public void skip(View view) {
        launchMain();
    }

    private void ColoredBars(int thisScreen) {

        int[] colorsInactive = getResources().getIntArray(R.array.dot_on_page_not_active);
        int[] colorsActive = getResources().getIntArray(R.array.dot_on_page_active);
        bottomBars = new TextView[screens.length];

        Layout_bars.removeAllViews();

        for (int i = 0; i < bottomBars.length; i++) {

            bottomBars[i] = new TextView(this);
            bottomBars[i].setTextSize(100);
            bottomBars[i].setText(Html.fromHtml("¯"));
            Layout_bars.addView(bottomBars[i]);
            bottomBars[i].setTextColor(colorsInactive[thisScreen]);
        }
        if (bottomBars.length > 0)
            bottomBars[thisScreen].setTextColor(colorsActive[thisScreen]);
    }

    private int getItem(int i) {
        return vp.getCurrentItem() + i;
    }

    private void launchMain() {
        //preferenceManager.setFirstTimeLaunch(false);
        //startActivity(new Intent(MainScreen.this, MainActivity.class));
        finish();
    }





    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            ColoredBars(position);

            if (position == screens.length - 1) {
                Next.setText("start");
                Skip.setVisibility(View.GONE);
            }
            else {
                Next.setText(getString(R.string.next));
                Skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


}
