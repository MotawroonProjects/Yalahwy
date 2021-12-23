package com.yalahwy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.ProductFavoriteRowBinding;
import com.yalahwy.models.ProductModel;
import com.yalahwy.ui.activity_favorite.FavoriteActivity;
import com.yalahwy.ui.activity_home.fragments.Fragment_Home;

import java.util.List;

public class FavoriteProductAdapter extends RecyclerView.Adapter<FavoriteProductAdapter.MyHolder> {

    private List<ProductModel> list;
    private Context context;
    private FavoriteActivity activity;
    private Fragment fragment;

    public FavoriteProductAdapter(List<ProductModel> list, Context context, Fragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;

    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductFavoriteRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_favorite_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.binding.setModel(model);
        holder.itemView.setOnClickListener(view -> {
            if (fragment != null) {
                Fragment_Home fragment_home = (Fragment_Home) fragment;
                ProductModel model2 = list.get(holder.getAdapterPosition());
                fragment_home.setProductItemModel(model2);
            } else {
                activity = (FavoriteActivity) context;
                ProductModel model2 = list.get(holder.getAdapterPosition());
                activity.setProductItemModel(model2);
            }
        });
        holder.binding.imageFavorite.setOnClickListener(view -> {
            if (fragment != null) {
                Fragment_Home fragment_home = (Fragment_Home) fragment;
                ProductModel model2 = list.get(holder.getAdapterPosition());
                fragment_home.removeFavorite(model2, holder.getAdapterPosition());


            } else {
                activity = (FavoriteActivity) context;
                ProductModel model2 = list.get(holder.getAdapterPosition());
                activity.removeFavorite(model2, holder.getAdapterPosition());
            }

        });
        holder.binding.imageCart.setOnClickListener(view -> {
            if (fragment != null) {
                Fragment_Home fragment_home = (Fragment_Home) fragment;
                ProductModel model2 = list.get(holder.getAdapterPosition());
                fragment_home.createDialogAlert(model2, 1);


            } else {
                activity = (FavoriteActivity) context;
                ProductModel model2 = list.get(holder.getAdapterPosition());
                activity.createDialogAlert(model2, 1);
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ProductFavoriteRowBinding binding;

        public MyHolder(ProductFavoriteRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }


}
