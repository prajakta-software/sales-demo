package com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopOrderDetailsTable")
public class OrderItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int id;
    @ColumnInfo(name = "itemName")
    private String itemName;
    @ColumnInfo(name = "quantity")
    private int quantity;
    @ColumnInfo(name = "price")
    private double price;
    @ColumnInfo(name = "gst")
    private double gst;
    @ColumnInfo(name = "shopId")
    private String shopId;// Assuming each shop has a unique ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
