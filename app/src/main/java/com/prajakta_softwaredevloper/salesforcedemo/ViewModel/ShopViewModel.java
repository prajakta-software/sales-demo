package com.prajakta_softwaredevloper.salesforcedemo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.prajakta_softwaredevloper.salesforcedemo.Model.Repository.ShopRepository;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.ShopDetails;

import java.util.List;

public class ShopViewModel extends AndroidViewModel {

    private ShopRepository repository;
    private LiveData<List<ShopDetails>> getAllData;
    private LiveData<Integer> totalShopCount;


    public ShopViewModel(@NonNull Application application) {
        super(application);

        repository = new ShopRepository(application);
        getAllData = repository.getAllData();
        totalShopCount = repository.getTotalShopCount();

    }

    public LiveData<Integer> getTotalShopCount() {
        return totalShopCount;
    }
    public void insert(ShopDetails data) {
        repository.insertData(data);
    }

    public LiveData<List<ShopDetails>> getGetAllData() {
        return getAllData;
    }

    public LiveData<ShopDetails> getShop(String ShopName, String ShopContactNumber) {
        return repository.getShop(ShopName, ShopContactNumber);
    }

    public LiveData<ShopDetails> getShopByName(String ShopName) {
        return repository.getShopByName(ShopName);
    }


}