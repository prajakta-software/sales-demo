package com.prajakta_softwaredevloper.salesforcedemo.View;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.prajakta_softwaredevloper.salesforcedemo.R;
import com.prajakta_softwaredevloper.salesforcedemo.View.ui.dashboard.NewDashboardFragment;
import com.prajakta_softwaredevloper.salesforcedemo.View.ui.home.DashboardFragment;
import com.prajakta_softwaredevloper.salesforcedemo.View.ui.home.HomeFragment;
import android.view.View;
import androidx.fragment.app.FragmentTransaction;
import com.prajakta_softwaredevloper.salesforcedemo.View.ui.notifications.NotificationsFragment;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dashboard);


        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_dashboard, new NewDashboardFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.navigation_home) {
                        selectedFragment = new NewDashboardFragment();

                    }else  if (item.getItemId() == R.id.navigation_shop) {
                        showShopSubmenu();


                        //selectedFragment = new DashboardFragment();
                    }else  if (item.getItemId() == R.id.navigation_salesorder) {
                        selectedFragment = new NotificationsFragment();
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_dashboard, selectedFragment).commit();
                    }

                    return true;
                }
            };
    private void showShopSubmenu() {
        View view = findViewById(R.id.navigation_shop);
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.submenu_shop, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_add_shop) {
                    replaceFragment(new DashboardFragment());
                }else  if (item.getItemId() == R.id.action_view_shops) {
                    replaceFragment(new HomeFragment());

                }

                return false;
            }
        });
        popupMenu.show();
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_dashboard, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
