package com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao

public interface ShopDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShopDetails shop);

    @Query("SELECT * FROM ShopDetails")
    LiveData<List<ShopDetails>> getAllShops();

    @Query("select * from ShopDetails ORDER BY Id DESC")
    LiveData<List<ShopDetails>> getShopDetails();

    @Query("SELECT * FROM ShopDetails WHERE ShopName = :ShopName")
    LiveData<ShopDetails> getShopByName(String ShopName);

    @Query("SELECT * FROM ShopDetails WHERE ShopName = :ShopName AND ContactNumber = :ContactNumber")
    LiveData<ShopDetails> getShop(String ShopName, String ContactNumber);

    @Query("delete from ShopDetails")
    void deleteAllShopsData();

    @Query("delete from ShopDetails where Id=:id")
    void deleteSelectedShopsData(int id);

    @Query("SELECT * FROM ShopDetails WHERE Id = :id")
    LiveData<ShopDetails> getSelectedShop(int id);

}
