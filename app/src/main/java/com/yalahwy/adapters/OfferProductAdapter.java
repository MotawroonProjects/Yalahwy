package com.yalahwy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.ProductOfferRowBinding;
import com.yalahwy.models.ProductModel;
import com.yalahwy.ui.activity_home.fragments.Fragment_Home;

import java.util.List;

public class OfferProductAdapter extends RecyclerView.Adapter<OfferProductAdapter.MyHolder> {

    private List<ProductModel> list;
    private Context context;
    private Fragment_Home fragment_home;

    public OfferProductAdapter(List<ProductModel> list, Context context,Fragment_Home fragment_home) {
        this.list = list;
        this.context = context;
        this.fragment_home = fragment_home;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductOfferRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_offer_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.binding.setModel(model);
        holder.itemView.setOnClickListener(view -> {
            ProductModel model2 = list.get(holder.getAdapterPosition());
            fragment_home.setProductItemModel(model2);

        });

        holder.binding.checkbox.setOnClickListener(view -> {
            ProductModel model2 = list.get(holder.getAdapterPosition());
            if (holder.binding.checkbox.isChecked()){
                model2.setIs_wishlist(new ProductModel.IsWishList());

            }else {
                model2.setIs_wishlist(null);

            }
            list.set(holder.getAdapterPosition(),model2);
            notifyItemChanged(holder.getAdapterPosition());
            fragment_home.add_remove_favorite(model2,holder.getAdapterPosition(),"3");
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ProductOfferRowBinding binding;

        public MyHolder(ProductOfferRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
