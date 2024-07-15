package com.prajakta_softwaredevloper.salesforcedemo.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.prajakta_softwaredevloper.salesforcedemo.R;

public class SpashScreenActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 5000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spash_screen);
        manageViewUI();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start LoginActivity
                Intent i = new Intent(SpashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                // Close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void manageViewUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}