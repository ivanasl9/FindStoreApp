package com.example.shopslistapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;

import com.example.shopslistapp.model.ShopModel;

public class OrderSuccessActivity extends AppCompatActivity {

    public static final String KEY_NAME = "ShopModel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        ShopModel shopModel = getIntent().getParcelableExtra(KEY_NAME);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(shopModel.getName());
        actionBar.setSubtitle(shopModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(false);


        TextView buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(v -> finish());
    }
}
