package com.yalahwy.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.ProductMenuRowBinding;
import com.yalahwy.models.MenuModel;
import com.yalahwy.ui.activity_menu.MenuActivity;

import java.util.List;

public class MenuProductAdapter extends RecyclerView.Adapter<MenuProductAdapter.MyHolder> {

    private List<MenuModel> list;
    private Context context;
    private MenuActivity activity;

    public MenuProductAdapter(List<MenuModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (MenuActivity) context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductMenuRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_menu_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        MenuModel model = list.get(position);
        holder.binding.setModel(model);

        if (model.getStatus().equals("yes")){
            holder.binding.tvPrice.setPaintFlags(holder.binding.tvPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            holder.binding.tvPrice.setTextColor(ContextCompat.getColor(context,R.color.gray8));
        }else {
            holder.binding.tvPrice.setTextColor(ContextCompat.getColor(context,R.color.black));

        }

        holder.binding.imageFavorite.setOnClickListener(view -> {
            MenuModel model2 = list.get(holder.getAdapterPosition());
            activity.removeFavorite(model2,holder.getAdapterPosition());

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ProductMenuRowBinding binding;

        public MyHolder(ProductMenuRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
