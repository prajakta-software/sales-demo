package com.prajakta_softwaredevloper.salesforcedemo.Model.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.LoginDao;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.LoginTable;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.SalesForceDatabase;
import java.util.List;

public class LoginRepository {

    private LoginDao loginDao;
    private LiveData<List<LoginTable>> allData;

    public LoginRepository(Application application) {

        SalesForceDatabase db = SalesForceDatabase.getDatabase(application);
        loginDao = db.loginDoa();
        allData = loginDao.getDetails();

    }

    public LiveData<LoginTable> getUser(String Email, String Password) {
        return loginDao.getUser(Email, Password);
    }

    public LiveData<LoginTable> getUserByEmail(String Email) {
        return loginDao.getUserByEmail(Email);
    }


    public void deleteData() {
        loginDao.deleteAllData();
    }

    public LiveData<List<LoginTable>> getAllData() {
        return allData;
    }

    public void insertData(LoginTable data) {
        new LoginInsertion(loginDao).execute(data);
    }

    private static class LoginInsertion extends AsyncTask<LoginTable, Void, Void> {

        private LoginDao loginDao;

        private LoginInsertion(LoginDao loginDao) {

            this.loginDao = loginDao;

        }

        @Override
        protected Void doInBackground(LoginTable... data) {

          //  loginDao.deleteAllData();

            loginDao.insertDetails(data[0]);
            return null;

        }

    }

}