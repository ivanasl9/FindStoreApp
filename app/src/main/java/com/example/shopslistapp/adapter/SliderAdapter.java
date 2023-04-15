package com.example.shopslistapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.example.shopslistapp.R;

public class SliderAdapter extends PagerAdapter {
    final Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;

    }


    public final int[] headings = {
            (R.raw.allshops),
            (R.raw.oneclick),
            (R.raw.securepayment)
    };

    public final String[] slide_descriptions = {
            "Sve trgovine na jednom mjestu", "Jednim klikom naručite \n što god poželite", "Siguran način plaćanja"
    };


    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        LottieAnimationView heading = view.findViewById(R.id.heading);
        TextView description = (TextView) view.findViewById(R.id.description);

        heading.setAnimation(headings[position]);
        description.setText(slide_descriptions[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
