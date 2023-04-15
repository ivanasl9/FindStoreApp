package com.example.shopslistapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private final String name;
    private final float price;
    private int totalInCart;
    private final String url;

    public int getTotalInCart() {
        return totalInCart;
    }

    public void setTotalInCart(int totalInCart) {
        this.totalInCart = totalInCart;
    }

    protected Product(Parcel in) {
        name = in.readString();
        price = in.readFloat();
        url = in.readString();
        totalInCart = in.readInt();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }


    public float getPrice() {
        return price;
    }


    public String getUrl() {
        return url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(url);
        dest.writeInt(totalInCart);
    }
}
