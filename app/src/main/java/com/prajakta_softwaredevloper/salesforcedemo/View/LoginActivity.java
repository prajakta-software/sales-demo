package com.prajakta_softwaredevloper.salesforcedemo.View;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.prajakta_softwaredevloper.salesforcedemo.Model.DataBinding.ClickListener;
import com.prajakta_softwaredevloper.salesforcedemo.R;
import com.prajakta_softwaredevloper.salesforcedemo.ViewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements ClickListener {
    private LoginViewModel loginViewModel;
    private EditText phoneNumber, password;
    private TextView loginButton,tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        manageLayout();
        initViews();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }
    private void manageLayout() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void initViews() {
        phoneNumber = findViewById(R.id.etPhoneNumber);
        password = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        tvLogin = findViewById(R.id.tvLogin);
        loginButton.setOnClickListener(this::onClick);
        tvLogin.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View view) {
        if (view == loginButton) {
            registerUser();
        }
        if(view==tvLogin){
            startLoginActivity();
        }
    }
    private void registerUser() {
        String strEmail = phoneNumber.getText().toString().trim();
        String strPassword = password.getText().toString().trim();
        if (TextUtils.isEmpty(strEmail)) {
            phoneNumber.setError("Please Enter Your Mobile Number");
        } else if (TextUtils.isEmpty(strPassword)) {
            password.setError("Please Enter Your Password");
        } else {
            loginUser(strEmail,strPassword);
        }
    }
    private void loginUser(String email,String pass) {
        loginViewModel.getUser(email, pass).observe(this, user -> {
            if (user != null) {
                Toast.makeText(this, "Welcome: "+email, Toast.LENGTH_SHORT).show();
            statDashActivity(email);
            } else {
                Toast.makeText(this, "User Not Register!", Toast.LENGTH_SHORT).show();
                startLoginActivity();
            }
        });
    }
    private void startLoginActivity() {
        Intent i =new Intent(this, MainActivity.class);
        startActivity(i);
    }
    private void statDashActivity(String email) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userId", email);
        editor.apply();

        Intent i1 =new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(i1);
    }
}