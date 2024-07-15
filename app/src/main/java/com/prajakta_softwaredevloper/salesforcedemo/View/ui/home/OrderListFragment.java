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
import android.widget.TextView;

import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.OrderItem;
import com.prajakta_softwaredevloper.salesforcedemo.R;
import com.prajakta_softwaredevloper.salesforcedemo.View.adapter.OrderAdapter;
import com.prajakta_softwaredevloper.salesforcedemo.ViewModel.OrderViewModel;

import java.util.List;

public class OrderListFragment extends Fragment {

    private OrderViewModel orderViewModel;
    private RecyclerView orderListRecyclerView;
    private OrderAdapter orderAdapter;
    String shopId,shopName;
    TextView tvShopname;

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

        tvShopname = view.findViewById(R.id.tvShopname);
        orderListRecyclerView = view.findViewById(R.id.orderListRecyclerView);
        orderListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        // Retrieve shop details from arguments
        Bundle args = getArguments();
        if (args != null) {
            shopId = args.getString("shopId");
            shopName = args.getString("shopName");
            if (shopId != null) {
                displayShopOrderDetails(shopId);
            }
            if (shopName != null) {
                tvShopname.setText("Order Details of "+shopName.toUpperCase());
            }else{
                tvShopname.setText("Order Details");

            }
        }
        requireActivity().setTitle("Order Details of :"+shopId);



        ; // Get this from arguments or some other source
    }

    private void displayShopOrderDetails(String shopId) {
        orderViewModel.getOrderItemsByShop(shopId).observe(getViewLifecycleOwner(), new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
                orderAdapter = new OrderAdapter(orderItems);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                orderListRecyclerView.setLayoutManager(layoutManager);
                orderListRecyclerView.setAdapter(orderAdapter);
            }
        });

    }
}
