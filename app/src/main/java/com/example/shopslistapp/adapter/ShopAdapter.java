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

import com.example.shopslistapp.model.ShopModel;

import java.util.List;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private final List<ShopModel> shopModelList;
    private final ShopClickListener clickListener;

    public ShopAdapter(List<ShopModel> shopModelList, ShopClickListener clickListener) {
        this.shopModelList = shopModelList;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {
        holder.spName.setText(shopModelList.get(position).getName());
        holder.spAddress.setText("Address: " + shopModelList.get(position).getAddress());
        holder.spHours.setText("Today's hours: " + shopModelList.get(position).getHours().getTodaysHours());

        holder.itemView.setOnClickListener(v -> clickListener.onItemClick(shopModelList.get(position)));
        Glide.with(holder.thumbImage)
                .load(shopModelList.get(position).getImage())
                .into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        return shopModelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView spName;
        final TextView spAddress;
        final TextView spHours;
        final ImageView thumbImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spName = itemView.findViewById(R.id.shopName);
            spAddress = itemView.findViewById(R.id.shopAddress);
            spHours = itemView.findViewById(R.id.shopHours);
            thumbImage = itemView.findViewById(R.id.thumbImage);
        }
    }

    public interface ShopClickListener {
        void onItemClick(ShopModel shopModel);
    }
}
