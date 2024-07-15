package com.prajakta_softwaredevloper.salesforcedemo.View;
import android.content.Intent;
import android.os.Bundle;
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
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.LoginTable;
import com.prajakta_softwaredevloper.salesforcedemo.R;
import com.prajakta_softwaredevloper.salesforcedemo.ViewModel.LoginViewModel;

public class MainActivity extends AppCompatActivity implements ClickListener {
    private LoginViewModel loginViewModel;
    private EditText phoneNumber, password, confirmPassword;
    private TextView registerButton,tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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
        confirmPassword = findViewById(R.id.etConfirmPassword);
        registerButton = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
        registerButton.setOnClickListener(this::onClick);
        tvLogin.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View view) {
        if (view == registerButton) {
            registerUser();
        }
        if(view==tvLogin){
            startLoginActivity();
        }
    }
    private void registerUser() {
        String strEmail = phoneNumber.getText().toString().trim();
        String strPassword = password.getText().toString().trim();
        String strConfirm = confirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(strEmail)) {
            phoneNumber.setError("Please Enter Your Mobile Number");
        } else if (TextUtils.isEmpty(strPassword)) {
            password.setError("Please Enter Your Password");
        } else if (TextUtils.isEmpty(strConfirm)) {
            password.setError("Please Enter Your Password");
        } else if (TextUtils.isEmpty(strConfirm)) {
            confirmPassword.setError("Please Enter Your Password");
        } else if (!TextUtils.equals(strPassword, strConfirm)) {
            confirmPassword.setError("Incorrect Password");
        } else {
            registerNewUser(strEmail,strPassword);
        }
    }
    private void registerNewUser(String email,String pass) {
        loginViewModel.getUser(email, pass).observe(this, user -> {
            if (user != null) {
                // User exists, log in
                Toast.makeText(this, "Already Register", Toast.LENGTH_SHORT).show();
            } else {
                // User does not exist, register
                LoginTable newUser = new LoginTable();
                newUser.setEmail(email);
                newUser.setPassword(pass);
                loginViewModel.insert(newUser);
                Toast.makeText(this, "User registered!", Toast.LENGTH_SHORT).show();
            }
        });
        startLoginActivity();
    }
    private void startLoginActivity() {
        Intent i =new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}