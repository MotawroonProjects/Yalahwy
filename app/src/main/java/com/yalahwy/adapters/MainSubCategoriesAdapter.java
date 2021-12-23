package com.yalahwy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.MainRowSubCategoryBinding;
import com.yalahwy.models.SubCategoryModel;
import com.yalahwy.ui.activity_home.fragments.Fragment_Home;

import java.util.List;

import io.paperdb.Paper;

public class MainSubCategoriesAdapter extends RecyclerView.Adapter<MainSubCategoriesAdapter.MyHolder> {

    private List<SubCategoryModel> list;
    private Context context;
    private int parent_pos = 0;
    private String lang;
    private Fragment_Home fragment_home;

    public MainSubCategoriesAdapter(List<SubCategoryModel> list, Context context, int parent_pos, Fragment_Home fragment_home) {
        this.list = list;
        this.context = context;
        this.parent_pos = parent_pos;
        Paper.init(context);
        lang = Paper.book().read("lang","ar");
        this.fragment_home = fragment_home;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainRowSubCategoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_row_sub_category, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        holder.binding.setModel(list.get(position));
        holder.binding.setLang(lang);
        holder.itemView.setOnClickListener(v -> {
            fragment_home.setItemSubCategoryModel(list.get(holder.getAdapterPosition()));
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private MainRowSubCategoryBinding binding;

        public MyHolder(MainRowSubCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
