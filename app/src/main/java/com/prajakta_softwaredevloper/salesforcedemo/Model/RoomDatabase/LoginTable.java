package com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "LoginDetails", indices = {@Index(value = {"Email"},unique = true)})

public class LoginTable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int id;

    @ColumnInfo(name = "Email")
    private String Email;

    @ColumnInfo(name = "Password")
    private String Password;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
