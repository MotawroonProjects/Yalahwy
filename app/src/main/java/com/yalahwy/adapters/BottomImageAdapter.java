package com.yalahwy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.BottomImageRowBinding;
import com.yalahwy.models.GalleryModel;
import com.yalahwy.ui.activity_home.fragments.Fragment_Home;

import java.util.List;

public class BottomImageAdapter extends RecyclerView.Adapter<BottomImageAdapter.MyHolder> {

    private List<GalleryModel> list;
    private Context context;
    private Fragment_Home fragment_home;
    private String type;
    private String lang;

    public BottomImageAdapter(List<GalleryModel> list, Context context, Fragment_Home fragment_home, String lang) {
        this.list = list;
        this.context = context;
        this.fragment_home = fragment_home;
        this.lang = lang;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BottomImageRowBinding bottomImageRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_image_row, parent, false);
        return new MyHolder(bottomImageRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        GalleryModel model = list.get(position);
        holder.binding.setModel(model);
        holder.binding.setLang(lang);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private BottomImageRowBinding binding;

        public MyHolder(BottomImageRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
