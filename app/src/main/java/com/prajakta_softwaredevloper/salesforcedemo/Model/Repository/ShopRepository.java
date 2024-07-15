package com.prajakta_softwaredevloper.salesforcedemo.Model.Repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.LoginDao;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.LoginTable;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.SalesForceDatabase;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.ShopDao;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.ShopDetails;

import java.util.List;

public class ShopRepository {

    private ShopDao shopDao;
    private LiveData<List<ShopDetails>> allData;
    private LiveData<Integer> totalShopCount;


    public ShopRepository(Application application) {

        SalesForceDatabase db = SalesForceDatabase.getDatabase(application);
        shopDao = db.shopDao();
        allData = shopDao.getShopDetails();
        totalShopCount = shopDao.getTotalShopCount();


    }

    public LiveData<ShopDetails> getShop(String ShopName, String ShopContactNumber) {
        return shopDao.getShop(ShopName, ShopContactNumber);
    }

    public LiveData<ShopDetails> getShopByName(String ShopName) {
        return shopDao.getShopByName(ShopName);
    }


    public void deleteData() {
        shopDao.deleteAllShopsData();
    }

    public LiveData<List<ShopDetails>> getAllData() {
        return allData;
    }
    public LiveData<List<ShopDetails>> getShopDetails() {
        return allData;
    }

    public void insertData(ShopDetails data) {
        new ShopRepository.shopInsertion(shopDao).execute(data);
    }

    private static class shopInsertion extends AsyncTask<ShopDetails, Void, Void> {

        private ShopDao shopDao;

        private shopInsertion(ShopDao shopDao) {

            this.shopDao = shopDao;

        }

        @Override
        protected Void doInBackground(ShopDetails... data) {

            //  loginDao.deleteAllData();

            shopDao.insert(data[0]);
            return null;

        }

    }
    public LiveData<Integer> getTotalShopCount() {
        return totalShopCount;
    }

}