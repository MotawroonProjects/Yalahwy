package com.yalahwy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.ProductRow2Binding;
import com.yalahwy.models.ProductModel;
import com.yalahwy.ui.activity_products.ProductsActivity;
import com.yalahwy.ui.activity_search.SearchActivity;

import java.util.List;

import io.paperdb.Paper;

public class ProductAdapter2 extends RecyclerView.Adapter<ProductAdapter2.MyHolder> {

    private List<ProductModel> list;
    private Context context;
    private String lang;

    public ProductAdapter2(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
        Paper.init(context);
        lang = Paper.book().read("lang", "ar");

    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductRow2Binding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_row2, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.binding.setModel(model);
        holder.binding.setLang(lang);
        if(model.getIs_wishlist()!=null){
            Log.e("ssssssss",model.getIs_wishlist().getId()+"");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof ProductsActivity) {
                    ProductsActivity productsActivity = (ProductsActivity) context;
                    productsActivity.setProductItemModel(list.get(holder.getLayoutPosition()));
                } else {
                    SearchActivity productsActivity = (SearchActivity) context;
                    productsActivity.setProductItemModel(list.get(holder.getLayoutPosition()));
                }
            }
        });

        holder.binding.checkbox.setOnClickListener(view -> {
            if (context instanceof ProductsActivity) {
                ProductsActivity productsActivity = (ProductsActivity) context;
                ProductModel model2 = list.get(holder.getAdapterPosition());
                if (holder.binding.checkbox.isChecked()) {
                    model2.setIs_wishlist(new ProductModel.IsWishList());

                } else {
                    model2.setIs_wishlist(null);


                }
                productsActivity.add_remove_favorite(model2,holder.getAdapterPosition(),"1");

//                list.set(holder.getAdapterPosition(), model2);
//                notifyItemChanged(holder.getAdapterPosition());


            } else {
                SearchActivity activity = (SearchActivity) context;
                ProductModel model2 = list.get(holder.getAdapterPosition());
                if (holder.binding.checkbox.isChecked()) {
                    model2.setIs_wishlist(new ProductModel.IsWishList());

                } else {
                    model2.setIs_wishlist(null);

                }
                activity.add_remove_favorite(model2,holder.getAdapterPosition(),"1");

//                list.set(holder.getAdapterPosition(), model2);
//                notifyItemChanged(holder.getAdapterPosition());            }
            }
        });
        holder.binding.imageCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof ProductsActivity) {

                    ProductsActivity productsActivity = (ProductsActivity) context;
                    productsActivity.createDialogAlert(list.get(holder.getLayoutPosition()), 1);
                } else {
                    SearchActivity productsActivity = (SearchActivity) context;
                    productsActivity.createDialogAlert(list.get(holder.getLayoutPosition()), 1);
                }

            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ProductRow2Binding binding;

        public MyHolder(ProductRow2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
