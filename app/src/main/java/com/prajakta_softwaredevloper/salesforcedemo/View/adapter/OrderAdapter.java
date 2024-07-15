package com.prajakta_softwaredevloper.salesforcedemo.View.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.OrderItem;
import com.prajakta_softwaredevloper.salesforcedemo.R;

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

        holder.itemNameEditText.setText(orderItem.getItemName());
        holder.quantityEditText.setText(String.valueOf(orderItem.getQuantity()));
        holder.priceEditText.setText(String.valueOf(orderItem.getPrice()));
        holder.gstEditText.setText(String.valueOf(orderItem.getGst()));

        holder.itemNameEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                orderItem.setItemName(s.toString());
            }
        });

        holder.quantityEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    orderItem.setQuantity(Integer.parseInt(s.toString()));
                } catch (NumberFormatException e) {
                    orderItem.setQuantity(0);
                }
            }
        });

        holder.priceEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    orderItem.setPrice(Double.parseDouble(s.toString()));
                } catch (NumberFormatException e) {
                    orderItem.setPrice(0.0);
                }
            }
        });

        holder.gstEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    orderItem.setGst(Double.parseDouble(s.toString()));
                } catch (NumberFormatException e) {
                    orderItem.setGst(0.0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        EditText itemNameEditText;
        EditText quantityEditText;
        EditText priceEditText;
        EditText gstEditText;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameEditText = itemView.findViewById(R.id.itemNameEditText);
            quantityEditText = itemView.findViewById(R.id.quantityEditText);
            priceEditText = itemView.findViewById(R.id.priceEditText);
            gstEditText = itemView.findViewById(R.id.gstEditText);
        }
    }
}
