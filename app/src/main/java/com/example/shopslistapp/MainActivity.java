package com.example.shopslistapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;

import com.example.shopslistapp.adapter.ShopAdapter;
import com.example.shopslistapp.model.ShopModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShopAdapter.ShopClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Popis trgovina</font>"));

        List<ShopModel> shopModelList = getShopData();

        initRecyclerView(shopModelList);

    }

    private void initRecyclerView(List<ShopModel> shopModelList) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShopAdapter adapter = new ShopAdapter(shopModelList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(ShopModel shopModel) {
        Intent intent = new Intent(MainActivity.this, ShopActivity.class);
        intent.putExtra("ShopModel", shopModel);
        startActivity(intent);
    }


    private List<ShopModel> getShopData() {
        InputStream is = getResources().openRawResource(R.raw.shops);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        ShopModel[] shopModels = gson.fromJson(jsonStr, ShopModel[].class);

        return Arrays.asList(shopModels);



    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", (dialog, which) -> finishAffinity());
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog alert = builder.create();
        try {
            alert.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }
}