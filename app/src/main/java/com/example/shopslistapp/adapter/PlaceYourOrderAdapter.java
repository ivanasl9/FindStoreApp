package com.example.shopslistapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopslistapp.R;
import com.example.shopslistapp.model.Product;

import java.util.List;
import java.util.Locale;

public class PlaceYourOrderAdapter extends RecyclerView.Adapter<PlaceYourOrderAdapter.ViewHolder> {
    private final List<Product> productList;

    public PlaceYourOrderAdapter(List<Product> productList) {
        this.productList = productList;
    }


    @NonNull
    @Override
    public PlaceYourOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_your_order_recycler_row, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlaceYourOrderAdapter.ViewHolder holder, int position) {
        holder.productName.setText(productList.get(position).getName());
        holder.productPrice.setText("Price: €" + String.format(Locale.ENGLISH, "%.2f", productList.get(position).getPrice() * productList.get(position).getTotalInCart()));
        holder.productQty.setText("Qty: " + productList.get(position).getTotalInCart());
        Glide.with(holder.thumbImage)
                .load(productList.get(position).getUrl())
                .into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView productName;
        final TextView productPrice;
        final TextView productQty;
        final ImageView thumbImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQty = itemView.findViewById(R.id.productQty);
            thumbImage = itemView.findViewById(R.id.thumbImage);
        }
    }
}
