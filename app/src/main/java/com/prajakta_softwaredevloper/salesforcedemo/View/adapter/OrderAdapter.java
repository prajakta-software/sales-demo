package com.prajakta_softwaredevloper.salesforcedemo.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.OrderItem;
import com.prajakta_softwaredevloper.salesforcedemo.R;
import com.prajakta_softwaredevloper.salesforcedemo.ViewModel.OrderViewModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<OrderItem> orderItemList;

    public OrderAdapter(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;

    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderItem orderItem = orderItemList.get(position);

        holder.itemNameEditText.setText(String.format("Product Name:%s", orderItem.getItemName()));
        holder.quantityEditText.setText(String.format("Product Qty:%s",orderItem.getQuantity()));
        holder.priceEditText.setText(String.format("Product Price:%s",orderItem.getPrice()));
        holder.gstEditText.setText(String.format("Product GST:%s",orderItem.getGst()));


    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView itemNameEditText;
        TextView quantityEditText;
        TextView priceEditText;
        TextView gstEditText;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameEditText = itemView.findViewById(R.id.itemNameEditText);
            quantityEditText = itemView.findViewById(R.id.quantityEditText);
            priceEditText = itemView.findViewById(R.id.priceEditText);
            gstEditText = itemView.findViewById(R.id.gstEditText);
        }
    }
}
