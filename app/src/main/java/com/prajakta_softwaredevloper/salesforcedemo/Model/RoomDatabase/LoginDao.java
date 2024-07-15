package com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDetails(LoginTable data);

    @Query("select * from LoginDetails ORDER BY Id DESC")
    LiveData<List<LoginTable>> getDetails();

    @Query("SELECT * FROM LoginDetails WHERE Email = :Email")
    LiveData<LoginTable> getUserByEmail(String Email);

    @Query("SELECT * FROM LoginDetails WHERE Email = :Email AND Password = :Password")
    LiveData<LoginTable> getUser(String Email, String Password);

    @Query("delete from LoginDetails")
    void deleteAllData();
}
