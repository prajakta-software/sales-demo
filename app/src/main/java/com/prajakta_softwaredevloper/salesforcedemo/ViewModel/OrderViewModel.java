package com.prajakta_softwaredevloper.salesforcedemo.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.prajakta_softwaredevloper.salesforcedemo.Model.Repository.OrderRepository;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.OrderItem;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private OrderRepository repository;
    private LiveData<List<OrderItem>> orderItems;

    public OrderViewModel(Application application) {
        super(application);
        repository = new OrderRepository(application);
    }

    public void insert(OrderItem orderItem) {
        repository.insertData(orderItem);
    }

    public LiveData<List<OrderItem>> getOrderItemsByShop(String shopId) {
        return repository.getOrderItemsByShop(shopId);
    }
}

