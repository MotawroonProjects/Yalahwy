package com.yalahwy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.MainCategoryRowBinding;
import com.yalahwy.models.CategorySubCategoryModel;
import com.yalahwy.ui.activity_home.HomeActivity;

import java.util.List;

import io.paperdb.Paper;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.MyHolder> {

    private List<CategorySubCategoryModel> singleCategoryModelList;
    private Context context;
    private int i = -1;
    private HomeActivity activity;
    private String lang;

    public HomeCategoriesAdapter(List<CategorySubCategoryModel> singleCategoryModelList, Context context) {
        this.singleCategoryModelList = singleCategoryModelList;
        this.context = context;
        activity = (HomeActivity) context;
        Paper.init(context);
        lang = Paper.book().read("lang", "ar");

    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainCategoryRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_category_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        holder.binding.setModel(singleCategoryModelList.get(position));
        holder.binding.setLang(lang);
        holder.itemView.setOnClickListener(view -> {
            activity.setItemData(singleCategoryModelList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return singleCategoryModelList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private MainCategoryRowBinding binding;

        public MyHolder(MainCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
