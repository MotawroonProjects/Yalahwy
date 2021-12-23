package com.yalahwy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yalahwy.R;
import com.yalahwy.databinding.CategotyRowBinding;
import com.yalahwy.models.SingleCategoryModel;
import com.yalahwy.ui.activity_home.fragments.Fragment_Categories;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyHolder> {

    private List<SingleCategoryModel> list;
    private Context context;
    private Fragment_Categories fragment_categories;
    private int i = 0;
    private int old_pos = 0;

    public CategoriesAdapter(List<SingleCategoryModel> list, Context context, int selectedSubCategoryPos, Fragment_Categories fragment_categories) {
        this.list = list;
        this.context = context;
        this.fragment_categories = fragment_categories;
        i = selectedSubCategoryPos;
        old_pos = i;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategotyRowBinding categotyRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.categoty_row, parent, false);
        return new MyHolder(categotyRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        SingleCategoryModel singleCategoryModel = list.get(position);
        holder.binding.setModel(singleCategoryModel);

        if (singleCategoryModel.isSelected()) {
            holder.binding.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            holder.binding.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.gray3));


        }

        holder.itemView.setOnClickListener(view -> {
            SingleCategoryModel model1 = list.get(old_pos);
            model1.setSelected(false);
            list.set(old_pos,model1);
            notifyItemChanged(old_pos);

            i = holder.getAdapterPosition();
            SingleCategoryModel model2 = list.get(i);
            model2.setSelected(true);
            list.set(i,model2);
            notifyItemChanged(i);
            old_pos =i;

            fragment_categories.setItemData(model2);

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private CategotyRowBinding binding;

        public MyHolder(CategotyRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }

    public void setSelectedPos(int pos){
        Log.e("pos",pos+"___"+old_pos);
        SingleCategoryModel model1 = list.get(old_pos);
        model1.setSelected(false);
        list.set(old_pos,model1);
        notifyItemChanged(old_pos);
        i = pos;
        SingleCategoryModel model2 = list.get(i);
        model2.setSelected(true);
        list.set(i,model2);
        notifyItemChanged(i);
        old_pos =i;
    }
}
