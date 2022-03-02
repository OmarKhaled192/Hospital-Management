package com.yom.hospitalmanagementyom.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.yom.hospitalmanagementyom.R;


//Omar Khaled
public class SlideAdapter extends PagerAdapter {

    private int[] slide_images;
    private String[] slide_headings;
    private String[] slide_decs;
    public SlideAdapter( int[] slide_images, String[] slide_headings, String[] slide_decs) {
        this.slide_images=slide_images;
        this.slide_headings=slide_headings;
        this.slide_decs=slide_decs;
    }

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from( container.getContext() ).inflate(R.layout.slide_item,container,false);

        ImageView SlideImageView = view.findViewById(R.id.imageView_id);
        TextView SlideHeading = view.findViewById(R.id.Head_id);
        TextView SlideDecs = view.findViewById(R.id.Description_id) ;


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
