package com.example.shopslistapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ShopModel implements Parcelable {
    private final String name;
    private final String address;
    private final String image;
    private final float delivery_charge;
    private Hours hours;
    private List<Product> products;

    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }


    public String getImage() {
        return image;
    }


    public float getDelivery_charge() {
        return delivery_charge;
    }


    public Hours getHours() {
        return hours;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    protected ShopModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        image = in.readString();
        delivery_charge = in.readFloat();
        products = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Parcelable.Creator<ShopModel> CREATOR = new Parcelable.Creator<ShopModel>() {
        @Override
        public ShopModel createFromParcel(Parcel in) {
            return new ShopModel(in);
        }

        @Override
        public ShopModel[] newArray(int size) {
            return new ShopModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(image);
        dest.writeFloat(delivery_charge);
        dest.writeTypedList(products);
    }
}
