package com.prajakta_softwaredevloper.salesforcedemo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.prajakta_softwaredevloper.salesforcedemo.Model.Repository.LoginRepository;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.LoginTable;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository repository;
    private LiveData<List<LoginTable>> getAllData;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        repository = new LoginRepository(application);
        getAllData = repository.getAllData();

    }

    public void insert(LoginTable data) {
        repository.insertData(data);
    }

    public LiveData<List<LoginTable>> getGetAllData() {
        return getAllData;
    }

    public LiveData<LoginTable> getUser(String Email, String Password) {
        return repository.getUser(Email, Password);
    }

    public LiveData<LoginTable> getUserByEmail(String Email) {
        return repository.getUserByEmail(Email);
    }


}