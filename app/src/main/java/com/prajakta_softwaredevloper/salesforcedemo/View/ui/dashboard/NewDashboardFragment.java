package com.prajakta_softwaredevloper.salesforcedemo.View.ui.dashboard;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jjoe64.graphview.GraphView;
import com.prajakta_softwaredevloper.salesforcedemo.R;
import com.prajakta_softwaredevloper.salesforcedemo.ViewModel.OrderViewModel;
import com.prajakta_softwaredevloper.salesforcedemo.ViewModel.ShopViewModel;

public class NewDashboardFragment extends Fragment {
    OrderViewModel orderViewModel;
    ShopViewModel shopViewModel;
    private TextView tVTotalOrder,tVTotalShop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the TextView
        tVTotalOrder = view.findViewById(R.id.tVTotalOrder);
        tVTotalShop = view.findViewById(R.id.tVTotalShop);

        // Initialize the ViewModel
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        // Observe the LiveData in the ViewModel
        orderViewModel.getTotalOrderCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer orderCount) {
                if (orderCount != null) {
                    tVTotalOrder.setText(String.valueOf(orderCount));
                }
            }
        });
        // Observe the LiveData in the ViewModel
        shopViewModel.getTotalShopCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer orderCount) {
                if (orderCount != null) {
                    tVTotalShop.setText(String.valueOf(orderCount));
                }
            }
        });
    }
}