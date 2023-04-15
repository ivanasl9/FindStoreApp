package com.example.shopslistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.shopslistapp.adapter.SliderAdapter;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.Objects;

public class IntroductionActivity extends AppCompatActivity {
    ViewPager viewPager;
    SpringDotsIndicator springDotsIndicator;
    LinearLayout linearLayout;
    SliderAdapter sliderAdapter;

    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        viewPager = findViewById(R.id.viewPagerSlider);
        linearLayout = findViewById(R.id.linearlayout);
        btnContinue = findViewById(R.id.btnC);

        Objects.requireNonNull(getSupportActionBar()).hide();
        springDotsIndicator = findViewById(R.id.spring_dots_indicator);
        viewPager = findViewById(R.id.viewPagerSlider);
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        springDotsIndicator.attachTo(viewPager);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        btnContinue.setOnClickListener(view -> {
            Intent intent = new Intent(IntroductionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


    }
}