package com.example.shopslistapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.Button;

import android.widget.Toast;

import com.example.shopslistapp.adapter.ProductAdapter;
import com.example.shopslistapp.model.Product;
import com.example.shopslistapp.model.ShopModel;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity implements ProductAdapter.ProductClickListener {

    private List<Product> productList = null;
    private List<Product> itemsInCartList;
    private int totalItemInCart = 0;
    private Button btnCheckout;


    final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result != null && result.getResultCode() == RESULT_OK) {
            if (result.getData() != null && result.getData().getStringExtra(PlaceYourOrderActivity.KEY_NAME) != null) {
                result.getData().getStringExtra(PlaceYourOrderActivity.KEY_NAME);
                finish();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        ShopModel shopModel = getIntent().getParcelableExtra("ShopModel");
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(shopModel.getName());
        actionBar.setSubtitle(shopModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);
        btnCheckout = findViewById(R.id.buttonCheck);
        productList = shopModel.getProducts();
        initRecyclerView();
        btnCheckout.setEnabled(false);



        btnCheckout.setOnClickListener(v -> {

            if (itemsInCartList != null && itemsInCartList.size() <= 0) {
                Toast.makeText(ShopActivity.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                return;
            }
            shopModel.setProducts(itemsInCartList);
            Intent i = new Intent(ShopActivity.this, PlaceYourOrderActivity.class);
            i.putExtra("ShopModel", shopModel);
            startForResult.launch(i);

        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ProductAdapter productAdapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onAddToCartClick(Product product) {
        if (itemsInCartList == null) {
            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(product);
        totalItemInCart = 0;

        for (Product m : itemsInCartList) {
            totalItemInCart = totalItemInCart + m.getTotalInCart();
        }
        btnCheckout.setText("Checkout (" + totalItemInCart + ") items");
        btnCheckout.setEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onUpdateCartClick(Product product) {
        if (itemsInCartList.contains(product)) {
            int index = itemsInCartList.indexOf(product);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, product);

            totalItemInCart = 0;

            for (Product m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            btnCheckout.setText("Checkout (" + totalItemInCart + ") items");
            btnCheckout.setEnabled(true);
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRemoveFromCartClick(Product product) {
        if (itemsInCartList.contains(product)) {
            itemsInCartList.remove(product);
            totalItemInCart = 0;

            for (Product m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            btnCheckout.setText("Checkout (" + totalItemInCart + ") items");
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
