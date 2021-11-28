package com.yom.hospitalmanagementyom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SlideActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SlideAdapter slideAdapter;
    private TextView[] mDots;
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_slide );

        // initialize slide view component:
        mSlideViewPager =findViewById(R.id.slide_view_id);

        //initialize dot layout component:
        mDotLayout =findViewById(R.id.dotsLayout_id);

        //initialize slide Activity :
        slideAdapter = new SlideAdapter(this);

        mSlideViewPager.setAdapter(slideAdapter);
        addDotsIndicators(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
        skip=findViewById( R.id.skip );

    }
    // showing indicators :
    public void addDotsIndicators(int position) {
        mDots = new TextView[4];
        mDotLayout.removeAllViews();
        for (int i = 0;i < 4; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.hint));

            mDotLayout.addView(mDots[i]);

        }
        if(mDots.length >  0){
            mDots[position].setTextColor(getResources().getColor(R.color.red));
        }

    }
    // showing effect on indicator:
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicators(position);
            if (position==3)
                skip.setText( R.string.next );
            else
                skip.setText( R.string.skip );

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void skip(View view) {
        startActivity( new Intent(this,LoginActivity.class)  );
    }
}