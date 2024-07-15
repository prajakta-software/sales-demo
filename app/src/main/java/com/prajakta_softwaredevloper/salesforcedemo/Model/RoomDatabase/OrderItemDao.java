package com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface OrderItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDetails(OrderItem data);

    @Query("SELECt * FROM shopOrderDetailsTable WHERE shopId=:shopId")
    LiveData<List<OrderItem>> getOrderItemsByShop(String shopId);

    @Query("SELECT COUNT(*) FROM shopOrderDetailsTable")
    LiveData<Integer> getTotalOrderCount();
}
