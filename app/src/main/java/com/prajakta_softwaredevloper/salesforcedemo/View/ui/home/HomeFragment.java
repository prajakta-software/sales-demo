package com.prajakta_softwaredevloper.salesforcedemo.View.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.ShopDetails;
import com.prajakta_softwaredevloper.salesforcedemo.R;
import com.prajakta_softwaredevloper.salesforcedemo.View.adapter.ShopAdapter;
import com.prajakta_softwaredevloper.salesforcedemo.ViewModel.ShopViewModel;
import com.prajakta_softwaredevloper.salesforcedemo.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private ShopViewModel shopViewModel;
    private RecyclerView recyclerView;
    private ShopAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView1);
        shopViewModel = new ViewModelProvider(this.requireActivity()).get(ShopViewModel.class);

        ShopAdapter adapter = new ShopAdapter(getActivity(),shopViewModel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ShopDetails shop) {

                // Handle item click here
                Toast.makeText(getActivity(), "Clicked on shop: " + shop.getShopName(), Toast.LENGTH_SHORT).show();
                OrderFragment fragment = new OrderFragment();
                Bundle args = new Bundle();
                args.putParcelable("shop", shop); // Pass shop name to fragment
                fragment.setArguments(args);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_dashboard, fragment)
                        .addToBackStack(null)
                        .commit();            }
        });
        recyclerView.setAdapter(adapter);

        shopViewModel.getGetAllData().observe(getViewLifecycleOwner(), new Observer<List<ShopDetails>>() {
            @Override
            public void onChanged(List<ShopDetails> shops) {
                adapter.setShops(shops);
            }
        });
    }
}