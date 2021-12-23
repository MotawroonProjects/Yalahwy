package com.yalahwy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.SubRowBinding;
import com.yalahwy.models.SubCategoryModel;
import com.yalahwy.ui.activity_sub_category.SubCategoryActivity;

import java.util.List;

import io.paperdb.Paper;

public class SubCategories2Adapter extends RecyclerView.Adapter<SubCategories2Adapter.MyHolder> {

    private List<SubCategoryModel> list;
    private Context context;
    private SubCategoryActivity activity;
    private String lang;

    public SubCategories2Adapter(List<SubCategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (SubCategoryActivity) context;
        Paper.init(context);
        lang = Paper.book().read("lang","ar");


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.sub_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        SubCategoryModel model = list.get(position);
        holder.binding.setModel(model);
        holder.binding.setLang(lang);
        holder.itemView.setOnClickListener(v -> {
            activity.setItemModel(list.get(holder.getAdapterPosition()));
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private SubRowBinding binding;

        public MyHolder(SubRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
