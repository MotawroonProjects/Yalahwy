package com.yalahwy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.SubcategoryRowBinding;
import com.yalahwy.models.CategoryModel;
import com.yalahwy.ui.activity_home.fragments.Fragment_Categories;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.MyHolder> {

    private List<CategoryModel> list;
    private Context context;
    private Fragment_Categories fragment_categories;

    public SubCategoriesAdapter(List<CategoryModel> list, Context context,Fragment_Categories fragment_categories) {
        this.list = list;
        this.context = context;
        this.fragment_categories = fragment_categories;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubcategoryRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.subcategory_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        CategoryModel model = list.get(position);
        holder.binding.setModel(model);

        holder.binding.recView.setLayoutManager(new GridLayoutManager(context,3));
        if (model.getProducts()!=null&&model.getProducts().size()>0){
            ChildAdapter childAdapter = new ChildAdapter(model.getProducts(),context,position,fragment_categories);
            holder.binding.recView.setAdapter(childAdapter);
            holder.binding.tvNoData.setVisibility(View.GONE);

        }else {
            ChildAdapter childAdapter = new ChildAdapter(new ArrayList<>(),context,position,fragment_categories);
            holder.binding.recView.setAdapter(childAdapter);
            holder.binding.tvNoData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private SubcategoryRowBinding binding;

        public MyHolder(SubcategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
