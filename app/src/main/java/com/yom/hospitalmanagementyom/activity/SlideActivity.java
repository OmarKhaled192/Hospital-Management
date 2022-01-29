package com.yom.hospitalmanagementyom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.SlideAdapter;

//Omar Khaled
public class SlideActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    public SlideAdapter slideAdapter;
    private TextView[] mDots;

    //---------------------------------------------------
    // this is the new : declaration of two empty arrays.
    public int[] slide_images;
    public String[] slide_headings ;
    public String[] slide_decs ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_slide );

        // initialize slide view component:
        mSlideViewPager = (ViewPager) findViewById(R.id.slide_view_id);

        //initialize dot layout component:
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout_id);

        //------------------------------------------------
        // this is the new : creation of two empty arrays.
        slide_images = new int[]{
                R.drawable.doctors_icon,
                R.drawable.community_icon,
                R.drawable.drugs_icon,
                R.drawable.health_care_icon
        };
        slide_headings = new String[]{
                getString(R.string.doctor_head),
                getString(R.string.community_head),
                getString(R.string.drugs_head),
                getString(R.string.healthcare_head)
        };

        slide_decs = new String[] {

                getString(R.string.doctor_description),
                getString(R.string.community_description),
                getString(R.string.drugs_description),
                getString(R.string.healthcare_description)
        };
        //initialize slide Activity :
        slideAdapter = new SlideAdapter(this, slide_images, slide_headings, slide_decs);

        mSlideViewPager.setAdapter(slideAdapter);
        addDotsIndicators(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);



        //-------------------------------------------
        //this is new : calling Two functions setters:
       // slideAdapter.setSlide_headings(slide_headings );
       // slideAdapter.setSlide_decs(slide_decs);
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
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void skip(View view) {
        startActivity( new Intent(this,LoginActivity.class) );
    }
}