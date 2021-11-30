package com.yom.hospitalmanagementyom.adapter;


import android.content.Context;
import android.content.res.Resources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import java.lang.String;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.yom.hospitalmanagementyom.R;


public class SlideAdapter extends PagerAdapter {


    Resources res ;
    Context context ;

    LayoutInflater layoutInflater;
    public SlideAdapter(Context context)
    {
        this.context = context;
    }
    //Arrays
    public int[] slide_images = {
            R.drawable.doctors_icon,
            R.drawable.community_icon,
            R.drawable.drugs_icon,
            R.drawable.health_care_icon
    };

    //this is new: declaration two arrays for slide heading & description
    public String[] slide_headings = new String[4];
    public String[] slide_decs = new String[4];

    //----------------------------------------------------------------------------------------------------------------------------
    //this is new :  declaration two setters for slide heading & description and calling them in activity from slideAdapter object
    public void setSlide_headings(String[] slide_headings) {
        this.slide_headings = slide_headings;
    }

    public void setSlide_decs(String[] slide_decs) {
        this.slide_decs = slide_decs;
    }

    @Override
    public int getCount() {
        return slide_headings.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_item,container,false);
        ImageView SlideImageView = view.findViewById(R.id.imageView_id);
        TextView SlideHeading = view.findViewById(R.id.Head_id);
        TextView SlideDecs = view.findViewById(R.id.Description_id);


        SlideImageView.setImageResource(slide_images[position]);
        SlideHeading.setText(slide_headings[position]);
        SlideDecs.setText(slide_decs[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);

    }
}
