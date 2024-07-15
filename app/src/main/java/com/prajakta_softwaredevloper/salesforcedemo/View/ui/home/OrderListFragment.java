package com.prajakta_softwaredevloper.salesforcedemo.View.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.OrderItem;
import com.prajakta_softwaredevloper.salesforcedemo.R;
import com.prajakta_softwaredevloper.salesforcedemo.View.adapter.OrderAdapter;
import com.prajakta_softwaredevloper.salesforcedemo.ViewModel.OrderViewModel;

import java.util.List;

public class OrderListFragment extends Fragment {

    private OrderViewModel orderViewModel;
    private RecyclerView orderListRecyclerView;
    private OrderAdapter orderAdapter;

    public OrderListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderListRecyclerView = view.findViewById(R.id.orderListRecyclerView);
        orderListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        String shopId = "shopId"; // Get this from arguments or some other source
        orderViewModel.getOrderItemsByShop(shopId).observe(getViewLifecycleOwner(), new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
                orderAdapter = new OrderAdapter(orderItems);
                orderListRecyclerView.setAdapter(orderAdapter);
            }
        });
    }
}
