package com.yalahwy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.MainRowBinding;
import com.yalahwy.models.CategorySubCategoryModel;
import com.yalahwy.ui.activity_home.fragments.Fragment_Home;

import java.util.List;

import io.paperdb.Paper;

public class MainCategoriesAdapter extends RecyclerView.Adapter<MainCategoriesAdapter.MyHolder> {

    private List<CategorySubCategoryModel> list;
    private Context context;
    private String lang;
    private Fragment_Home fragment_home;

    public MainCategoriesAdapter(List<CategorySubCategoryModel> list, Context context,Fragment_Home fragment_home) {
        this.list = list;
        this.context = context;
        Paper.init(context);
        lang = Paper.book().read("lang", "ar");
        this.fragment_home = fragment_home;

    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        holder.binding.setModel(list.get(position));
        holder.binding.setLang(lang);
        MainSubCategoriesAdapter adapter = new MainSubCategoriesAdapter(list.get(position).getSubs(), context, position,fragment_home);
        holder.binding.recView.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false));
        holder.binding.recView.setAdapter(adapter);

        holder.binding.cardData.setOnClickListener(v -> {
            fragment_home.setSubCategoryData(list.get(holder.getAdapterPosition()));
        });
        holder.binding.tvseemore.setOnClickListener(v -> {
            fragment_home.setSubCategoryData(list.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private MainRowBinding binding;

        public MyHolder(MainRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
