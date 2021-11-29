package com.yom.hospitalmanagementyom.adapter;


import android.content.Context;
import android.graphics.drawable.DrawableContainer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.yom.hospitalmanagementyom.R;


public class SlideAdapter extends PagerAdapter {

    Context context;
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
    public String[] slide_headings = {
            "Doctors",
            "Community",
            "Drugs",
            "HealthCare"
    };
    public String[] slide_decs = {
        "You can book a doctor and know his appointments at any time",
            "You can benefit from many tips here",
            "You can order a drug or show availability of drug in hospital pharmacy",
            "Elderly people and people with chronic diseases can participate in a private medical care service"
    };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_item,container,false);
        ImageView SlideImageView = (ImageView) view.findViewById(R.id.imageView_id);
        TextView SlideHeading =(TextView) view.findViewById(R.id.Head_id);
        TextView SlideDecs =(TextView) view.findViewById(R.id.Description_id) ;


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
