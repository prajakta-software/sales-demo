package com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LoginTable.class,ShopDetails.class, OrderItem.class}, version = 1, exportSchema = false)
public abstract class SalesForceDatabase extends RoomDatabase {

    public abstract LoginDao loginDoa();
    public abstract ShopDao shopDao();
    public abstract OrderItemDao orderItemDao();


    private static SalesForceDatabase INSTANCE;

    public static SalesForceDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {

            synchronized (SalesForceDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                                    context, SalesForceDatabase.class, "SALESFORCE_DATABASE")
                            .fallbackToDestructiveMigration()
                            .build();

                }

            }

        }

        return INSTANCE;

    }

}
