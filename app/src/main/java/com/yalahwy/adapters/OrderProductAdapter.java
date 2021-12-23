package com.yalahwy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.ProductRow3Binding;
import com.yalahwy.models.OrderModel;

import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.MyHolder> {

    private List<OrderModel.CartProductModel> list;
    private Context context;

    public OrderProductAdapter(List<OrderModel.CartProductModel> list, Context context) {
        this.list = list;
        this.context = context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductRow3Binding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_row3, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        OrderModel.CartProductModel model = list.get(position);
        holder.binding.setModel(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ProductRow3Binding binding;

        public MyHolder(ProductRow3Binding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
