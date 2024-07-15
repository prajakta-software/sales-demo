package com.prajakta_softwaredevloper.salesforcedemo.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.ShopDetails;
import com.prajakta_softwaredevloper.salesforcedemo.R;
import com.prajakta_softwaredevloper.salesforcedemo.ViewModel.ShopViewModel;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<ShopDetails> shops;
    private ShopViewModel shopViewModel;
    private Context context;
    private OnItemClickListener mListener; // Listener member variable

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(ShopDetails shop);
    }

    // Method to set the listener from the fragment or activity
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ShopAdapter(Context context, ShopViewModel shopViewModel) {
        this.context = context;
        this.shopViewModel = shopViewModel;
    }

    public void setShops(List<ShopDetails> shops) {
        this.shops = shops;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        ShopDetails shop = shops.get(position);
        holder.bind(shop);

        // Set click listener on the whole item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(shop); // Pass the shop name to the listener
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shops != null ? shops.size() : 0;
    }

    // ViewHolder class
    class ShopViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, contactTextView, addressTextView;
        ImageView shopImageView;

        ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            contactTextView = itemView.findViewById(R.id.contactTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            shopImageView = itemView.findViewById(R.id.shopImageView);
        }

        void bind(ShopDetails shop) {
            nameTextView.setText(shop.getShopName());
            contactTextView.setText(shop.getContactNumber());
            addressTextView.setText(shop.getAddress());

            // Load image using Glide
            Glide.with(context)
                    .load(shop.getImagePath())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(shopImageView);
        }
    }
}
