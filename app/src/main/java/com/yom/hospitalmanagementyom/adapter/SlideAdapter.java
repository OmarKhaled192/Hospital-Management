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


//Omar Khaled
public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    int[] slide_images;
    String[] slide_headings;
    String[] slide_decs;
    public SlideAdapter(Context context, int[] slide_images, String[] slide_headings, String[] slide_decs) {
        this.context = context;
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
