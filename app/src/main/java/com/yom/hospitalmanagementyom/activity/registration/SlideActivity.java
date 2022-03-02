package com.yom.hospitalmanagementyom.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.SlideAdapter;
import com.yom.hospitalmanagementyom.databinding.ActivitySlideBinding;


public class SlideActivity extends AppCompatActivity {

    private ActivitySlideBinding binding;
    private TextView[] mDots;
    private int nCurrentPage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding=ActivitySlideBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );

        int[] slide_images = new int[]{R.drawable.doctor, R.drawable.drugs, R.drawable.health_care, R.drawable.community};
        String[] slide_headings = getResources().getStringArray(R.array.Heads);
        String[] slide_decs = getResources().getStringArray(R.array.Descriptions);
        SlideAdapter slideAdapter = new SlideAdapter(slide_images, slide_headings, slide_decs);
        binding.slideViewId.setAdapter(slideAdapter);
        addDotsIndicators(0);
        binding.slideViewId.addOnPageChangeListener(viewListener);
    }

    // showing indicators :
    private void addDotsIndicators(int position) {
        mDots = new TextView[4];
        binding.dotsLayoutId.removeAllViews();
        for (int i = 0;i < 4; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.hint));
            binding.dotsLayoutId.addView(mDots[i]);
        }
        if(mDots.length >  0){
            mDots[position].setTextColor(getResources().getColor(R.color.teal_700));
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
            nCurrentPage=position;
            if(position==0){
                binding.next.setVisibility(View.VISIBLE);
                binding.back.setVisibility(View.GONE);
                binding.back.setAnimation(AnimationUtils.loadAnimation(getBaseContext(),R.anim.hide));
                binding.getStarted.setVisibility(View.GONE);
            }
            else if( position < mDots.length-1 ){
                if(binding.back.getVisibility()==View.GONE && binding.next.getVisibility()==View.VISIBLE){
                    binding.next.setVisibility(View.VISIBLE);
                    binding.back.setVisibility(View.VISIBLE);
                    binding.back.setAnimation(AnimationUtils.loadAnimation(getBaseContext(),R.anim.show));
                    binding.getStarted.setVisibility(View.GONE);
                }
                else if(binding.getStarted.getVisibility()==View.VISIBLE && binding.next.getVisibility()==View.GONE){
                    binding.next.setVisibility(View.VISIBLE);
                    binding.next.setAnimation(AnimationUtils.loadAnimation(getBaseContext(),R.anim.show));
                    binding.back.setVisibility(View.VISIBLE);
                    binding.back.setAnimation(AnimationUtils.loadAnimation(getBaseContext(),R.anim.show));
                    binding.getStarted.setVisibility(View.GONE);
                    binding.getStarted.setAnimation(AnimationUtils.loadAnimation(getBaseContext(),R.anim.hide));
                }
                else{
                    binding.next.setVisibility(View.VISIBLE);
                    binding.back.setVisibility(View.VISIBLE);
                    binding.getStarted.setVisibility(View.GONE);
                }
            } else if( position == mDots.length-1 ){
                binding.next.setVisibility(View.GONE);
                binding.next.setAnimation(AnimationUtils.loadAnimation(getBaseContext(),R.anim.hide));
                binding.back.setVisibility(View.GONE);
                binding.back.setAnimation(AnimationUtils.loadAnimation(getBaseContext(),R.anim.hide));
                binding.getStarted.setVisibility(View.VISIBLE);
                binding.getStarted.setAnimation(AnimationUtils.loadAnimation(getBaseContext(),R.anim.show));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void skip(View view) {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity( intent );
        finish();
    }
    public void back(View view) {
        binding.slideViewId.setCurrentItem(nCurrentPage-1);
    }
    public void next(View view) {
        binding.slideViewId.setCurrentItem(nCurrentPage+1);
    }
    public void start(View view) {
        skip(view);
    }
}