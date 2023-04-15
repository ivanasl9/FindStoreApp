package com.example.shopslistapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopslistapp.R;
import com.example.shopslistapp.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final List<Product> productList;
    private final ProductClickListener clickListener;

    public ProductAdapter(List<Product> productList, ProductClickListener clickListener) {
        this.productList = productList;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recycler_row, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.productName.setText(productList.get(position).getName());
        holder.productPrice.setText("Price: €" + productList.get(position).getPrice());
        holder.addToCartButton.setOnClickListener(v -> {
            Product product = productList.get(position);
            product.setTotalInCart(1);
            clickListener.onAddToCartClick(product);
            holder.addMoreLayout.setVisibility(View.VISIBLE);
            holder.addToCartButton.setVisibility(View.GONE);
            holder.tvCount.setText(product.getTotalInCart() + "");
        });
        holder.imageMinus.setOnClickListener(v -> {
            Product product = productList.get(position);
            int total = product.getTotalInCart();
            total--;
            if (total > 0) {
                product.setTotalInCart(total);
                clickListener.onUpdateCartClick(product);
                holder.tvCount.setText(total + "");
            } else {
                holder.addMoreLayout.setVisibility(View.GONE);
                holder.addToCartButton.setVisibility(View.VISIBLE);
                product.setTotalInCart(total);
                clickListener.onRemoveFromCartClick(product);
            }
        });

        holder.imageAddOne.setOnClickListener(v -> {
            Product product = productList.get(position);
            int total = product.getTotalInCart();
            total++;
            if (total <= 10) {
                product.setTotalInCart(total);
                clickListener.onUpdateCartClick(product);
                holder.tvCount.setText(total + "");
            }
        });

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
        final TextView addToCartButton;
        final ImageView thumbImage;
        final ImageView imageMinus;
        final ImageView imageAddOne;
        final TextView tvCount;
        final LinearLayout addMoreLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            thumbImage = itemView.findViewById(R.id.thumbImage);
            imageMinus = itemView.findViewById(R.id.imageMinus);
            imageAddOne = itemView.findViewById(R.id.imageAddOne);
            tvCount = itemView.findViewById(R.id.tvCount);
            addMoreLayout = itemView.findViewById(R.id.addMoreLayout);
        }
    }

    public interface ProductClickListener {
        void onAddToCartClick(Product product);

        void onUpdateCartClick(Product product);

        void onRemoveFromCartClick(Product product);
    }
}
