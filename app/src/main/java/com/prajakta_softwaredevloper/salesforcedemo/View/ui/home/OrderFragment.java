package com.prajakta_softwaredevloper.salesforcedemo.View.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.OrderItem;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.ShopDetails;
import com.prajakta_softwaredevloper.salesforcedemo.R;
import com.prajakta_softwaredevloper.salesforcedemo.View.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private ImageView shopImageView;
    private TextView nameTextView, contactTextView, addressTextView;
    private Button placeOrderButton, addItemButton;
    private ShopDetails shop;
    private FusedLocationProviderClient fusedLocationClient;
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<OrderItem> orderItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orderdetails, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopImageView = view.findViewById(R.id.shopImageView);
        nameTextView = view.findViewById(R.id.nameTextView);
        contactTextView = view.findViewById(R.id.contactTextView);
        addressTextView = view.findViewById(R.id.addressTextView);
        placeOrderButton = view.findViewById(R.id.placeOrderButton);
        addItemButton = view.findViewById(R.id.addItemButton);
        orderRecyclerView = view.findViewById(R.id.orderRecyclerView);

        // Initialize order list and adapter
        orderItemList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderItemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderRecyclerView.setLayoutManager(layoutManager);
        orderRecyclerView.setAdapter(orderAdapter);


        // Retrieve shop details from arguments
        Bundle args = getArguments();
        if (args != null) {
            shop = args.getParcelable("shop");
            if (shop != null) {
                displayShopDetails();
            }
        }

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Handle place order button click
        placeOrderButton.setOnClickListener(v -> checkLocationAndPlaceOrder());

        // Handle add item button click
        addItemButton.setOnClickListener(v -> {
            // Placeholder logic to add an item to the order list
            addSampleItem();
        });
    }

    private void displayShopDetails() {
        // Load shop details into views
        Glide.with(requireContext())
                .load(shop.getImagePath())
                .placeholder(R.drawable.ic_launcher_background)
                .into(shopImageView);

        nameTextView.setText(shop.getShopName());
        contactTextView.setText(shop.getContactNumber());
        addressTextView.setText(shop.getAddress());
    }

    private void checkLocationAndPlaceOrder() {
        // Check location permission
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Get last known location
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Calculate distance between current location and shop location
                    float[] results = new float[1];
                    Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                            shop.getLatitude(), shop.getLongitude(), results);
                    float distanceInMeters = results[0];
                    boolean isWithin100Meters = distanceInMeters < 100;

                    // If within 100 meters, place order
                    if (isWithin100Meters) {
                        placeSampleOrder();
                    } else {
                        // Prompt user to go to the shop location
                        Toast.makeText(requireContext(), "Please go to the shop location to place an order.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Unable to fetch current location.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void placeSampleOrder() {
        // Replace with your logic to place an order
       OrderListFragment fragment = new OrderListFragment();

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_dashboard, fragment)
                .addToBackStack(null)
                .commit();
        Toast.makeText(requireContext(), "Sample order placed successfully!", Toast.LENGTH_SHORT).show();
    }

    private void addSampleItem() {
        // Placeholder logic to add an item to the order list
        OrderItem newItem = new OrderItem();
        newItem.setItemName("test");
        newItem.setPrice(100.00);
        newItem.setQuantity(12);
        newItem.setGst(18.00);
        orderItemList.add(newItem);
        orderAdapter.notifyItemInserted(orderItemList.size() - 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with location check or other actions
                checkLocationAndPlaceOrder();
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
