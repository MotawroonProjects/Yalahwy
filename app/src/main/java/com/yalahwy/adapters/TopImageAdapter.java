package com.yalahwy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.TopImageRowBinding;
import com.yalahwy.models.SliderDataModel;
import com.yalahwy.ui.activity_home.fragments.Fragment_Home;

import java.util.List;

public class TopImageAdapter extends RecyclerView.Adapter<TopImageAdapter.MyHolder> {

    private List<SliderDataModel.SliderModel> list;
    private Context context;
    private Fragment_Home fragment_home;
    private String type;
    private String lang;

    public TopImageAdapter(List<SliderDataModel.SliderModel> list, Context context, Fragment_Home fragment_home, String lang) {
        this.list = list;
        this.context = context;
        this.fragment_home = fragment_home;
        this.lang = lang;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TopImageRowBinding topImageRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.top_image_row, parent, false);
        return new MyHolder(topImageRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        SliderDataModel.SliderModel model = list.get(position);
        holder.binding.setModel(model);
        holder.binding.setLang(lang);
        holder.itemView.setOnClickListener(view -> {
            SliderDataModel.SliderModel model2 = list.get(holder.getAdapterPosition());
            fragment_home.setProductItemModel(model2);

        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private TopImageRowBinding binding;

        public MyHolder(TopImageRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
