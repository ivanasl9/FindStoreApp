package com.example.shopslistapp;


import androidx.activity.result.ActivityResultLauncher;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;


import android.content.Intent;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

import com.example.shopslistapp.adapter.PlaceYourOrderAdapter;
import com.example.shopslistapp.model.Product;
import com.example.shopslistapp.model.ShopModel;

import java.util.Locale;

public class PlaceYourOrderActivity extends AppCompatActivity {

    private EditText inputName, inputAddress, inputCity, inputState, inputZip, inputCardNumber, inputCardExpiry, inputCardPin;
    private RecyclerView cartItemsRecyclerView;
    private TextView tvSubtotalAmount;
    private TextView tvDeliveryChargeAmount;
    private TextView tvDeliveryCharge;
    private TextView tvTotalAmount;
    private boolean isDeliveryOn;
    public static final String KEY_NAME = "ShopModel";


    final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result != null && result.getResultCode() == RESULT_OK) {
            if (result.getData() != null && result.getData().getStringExtra(OrderSuccessActivity.KEY_NAME) != null) {
                result.getData().getStringExtra(OrderSuccessActivity.KEY_NAME);
                finish();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_your_order);

        ShopModel shopModel = getIntent().getParcelableExtra(KEY_NAME);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(shopModel.getName());
        actionBar.setSubtitle(shopModel.getAddress());

        actionBar.setDisplayHomeAsUpEnabled(true);

        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        inputZip = findViewById(R.id.inputZip);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        inputCardExpiry = findViewById(R.id.inputCardExpiry);
        inputCardPin = findViewById(R.id.inputCardPin);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        TextView buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        SwitchCompat switchDelivery = findViewById(R.id.switchDelivery);

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        buttonPlaceYourOrder.setOnClickListener(v -> onPlaceOrderButtonClick(shopModel));

        switchDelivery.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                inputAddress.setVisibility(View.VISIBLE);
                inputCity.setVisibility(View.VISIBLE);
                inputState.setVisibility(View.VISIBLE);
                inputZip.setVisibility(View.VISIBLE);
                tvDeliveryChargeAmount.setVisibility(View.VISIBLE);
                tvDeliveryCharge.setVisibility(View.VISIBLE);
                isDeliveryOn = true;
                calculateTotalAmount(shopModel);
            } else {
                inputAddress.setVisibility(View.GONE);
                inputCity.setVisibility(View.GONE);
                inputState.setVisibility(View.GONE);
                inputZip.setVisibility(View.GONE);
                tvDeliveryChargeAmount.setVisibility(View.GONE);
                tvDeliveryCharge.setVisibility(View.GONE);
                isDeliveryOn = false;
                calculateTotalAmount(shopModel);
            }
        });
        initRecyclerView(shopModel);
        calculateTotalAmount(shopModel);
    }

    @SuppressLint("SetTextI18n")
    private void calculateTotalAmount(ShopModel shopModel) {
        float subTotalAmount = 0f;

        for (Product m : shopModel.getProducts()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }

        tvSubtotalAmount.setText("€" + String.format(Locale.ENGLISH, "%.2f", subTotalAmount));
        if (isDeliveryOn) {
            tvDeliveryChargeAmount.setText("€" + String.format(Locale.ENGLISH, "%.2f", shopModel.getDelivery_charge()));
            subTotalAmount += shopModel.getDelivery_charge();
        }
        tvTotalAmount.setText("€" + String.format(Locale.ENGLISH, "%.2f", subTotalAmount));
    }

    private void onPlaceOrderButtonClick(ShopModel shopModel) {
        if (TextUtils.isEmpty(inputName.getText().toString())) {
            inputName.setError("Please enter name ");
            return;
        } else if (isDeliveryOn && TextUtils.isEmpty(inputAddress.getText().toString())) {
            inputAddress.setError("Please enter address ");
            return;
        } else if (isDeliveryOn && TextUtils.isEmpty(inputCity.getText().toString())) {
            inputCity.setError("Please enter city ");
            return;
        } else if (isDeliveryOn && TextUtils.isEmpty(inputState.getText().toString())) {
            inputState.setError("Please enter zip ");
            return;
        } else if (TextUtils.isEmpty(inputCardNumber.getText().toString())) {
            inputCardNumber.setError("Please enter card number ");
            return;
        } else if (TextUtils.isEmpty(inputCardExpiry.getText().toString())) {
            inputCardExpiry.setError("Please enter card expiry ");
            return;
        } else if (TextUtils.isEmpty(inputCardPin.getText().toString())) {
            inputCardPin.setError("Please enter card pin/cvv ");
            return;
        }

        Intent i = new Intent(PlaceYourOrderActivity.this, OrderSuccessActivity.class);
        i.putExtra("ShopModel", shopModel);
        startForResult.launch(i);
    }

    private void initRecyclerView(ShopModel shopModel) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlaceYourOrderAdapter placeYourOrderAdapter = new PlaceYourOrderAdapter(shopModel.getProducts());
        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}