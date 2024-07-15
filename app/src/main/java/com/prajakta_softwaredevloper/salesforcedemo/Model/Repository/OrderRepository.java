package com.prajakta_softwaredevloper.salesforcedemo.Model.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.LoginDao;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.LoginTable;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.OrderItem;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.OrderItemDao;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.SalesForceDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRepository {
    private OrderItemDao orderItemDao;

    public OrderRepository(Application application) {
        SalesForceDatabase db = SalesForceDatabase.getDatabase(application);
        orderItemDao = db.orderItemDao();
    }

    public void insertData(OrderItem data) {
        new OrderInsertion(orderItemDao).execute(data);
    }


    public LiveData<List<OrderItem>> getOrderItemsByShop(String shopId) {
        return orderItemDao.getOrderItemsByShop(shopId);
    }
    private static class OrderInsertion extends AsyncTask<OrderItem, Void, Void> {

        private OrderItemDao orderItemDao;

        private OrderInsertion(OrderItemDao orderItemDao) {

            this.orderItemDao = orderItemDao;

        }

        @Override
        protected Void doInBackground(OrderItem... data) {

            //  loginDao.deleteAllData();

            orderItemDao.insertDetails(data[0]);
            return null;

        }

    }

}
