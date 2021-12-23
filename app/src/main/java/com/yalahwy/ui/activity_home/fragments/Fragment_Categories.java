package com.yalahwy.ui.activity_home.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yalahwy.R;
import com.yalahwy.adapters.CategoriesAdapter;
import com.yalahwy.adapters.SubCategoriesAdapter;
import com.yalahwy.databinding.FragmentCategoriesBinding;
import com.yalahwy.models.CategoryModel;
import com.yalahwy.models.ProductModel;
import com.yalahwy.models.SingleCategoryModel;
import com.yalahwy.mvp.fragment_category_mvp.FragmentCategoryPresenter;
import com.yalahwy.mvp.fragment_category_mvp.FragmentCategoryView;
import com.yalahwy.ui.activity_home.HomeActivity;
import com.yalahwy.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Fragment_Categories extends Fragment implements FragmentCategoryView {
    private FragmentCategoriesBinding binding;
    private HomeActivity activity;
    private SubCategoriesAdapter subCategoriesAdapter;
    private CategoriesAdapter categoriesAdapter;
    private List<SingleCategoryModel> singleCategoryModelList;
    private List<CategoryModel> subCategoryModelList;
    private FragmentCategoryPresenter presenter;
    private double lat = 0.0, lng = 0.0;
    private int selectedSubCategoryPos = 0;
    private SingleCategoryModel selectedSingleCategoryModel;
    private String lang;
    public static Fragment_Categories newInstance(double lat, double lng, int selectedSubCategoryPos) {
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", lat);
        bundle.putDouble("lng", lng);
        bundle.putInt("pos", selectedSubCategoryPos);
        Fragment_Categories fragment_categories = new Fragment_Categories();
        fragment_categories.setArguments(bundle);
        return fragment_categories;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        lang = Paper.book().read("lang");

        Bundle bundle = getArguments();
        if (bundle != null) {
            lat = bundle.getDouble("lat");
            lng = bundle.getDouble("lng");
            selectedSubCategoryPos = bundle.getInt("pos");

        }
        subCategoryModelList = new ArrayList<>();
        singleCategoryModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.progBarCategories.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        subCategoriesAdapter = new SubCategoriesAdapter(subCategoryModelList, activity, this);
        binding.recView.setAdapter(subCategoriesAdapter);


        binding.recViewCategories.setLayoutManager(new LinearLayoutManager(activity));
        categoriesAdapter = new CategoriesAdapter(singleCategoryModelList, activity,selectedSubCategoryPos, this);
        binding.recViewCategories.setAdapter(categoriesAdapter);


        presenter = new FragmentCategoryPresenter(activity, this, lat, lng,lang);
        presenter.getCategory();
    }

    public void setSubCategory(int pos) {
        this.selectedSubCategoryPos = pos;
        if (singleCategoryModelList.size() > 0) {
            selectedSingleCategoryModel = singleCategoryModelList.get(selectedSubCategoryPos);
            presenter.getSubCategory(selectedSingleCategoryModel.getId());
            setSelectedSubCategory(this.selectedSubCategoryPos);
        }
    }


    @Override
    public void onSuccess(List<SingleCategoryModel> data) {
        if (data.size() > 0) {
            selectedSingleCategoryModel = data.get(selectedSubCategoryPos);
            selectedSingleCategoryModel.setSelected(true);
            data.set(selectedSubCategoryPos, selectedSingleCategoryModel);
            presenter.getSubCategory(selectedSingleCategoryModel.getId());
            binding.tvNoDataCategories.setVisibility(View.GONE);
            singleCategoryModelList.clear();
            singleCategoryModelList.addAll(data);
            categoriesAdapter.notifyDataSetChanged();
        } else {
            binding.tvNoDataCategories.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSubCategorySuccess(List<CategoryModel> data) {
        if (data.size() > 0) {
            subCategoryModelList.clear();
            subCategoryModelList.addAll(data);
            subCategoriesAdapter.notifyDataSetChanged();
            binding.tvNoData.setVisibility(View.GONE);
        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressCategoryShow() {
        binding.progBarCategories.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressCategoryHide() {
        binding.progBarCategories.setVisibility(View.GONE);

    }

    @Override
    public void onProgressSubCategoryShow() {
        binding.tvNoData.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);
        subCategoryModelList.clear();
        subCategoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onProgressSubCategoryHide() {
        binding.progBar.setVisibility(View.GONE);
    }


    public void setItemData(SingleCategoryModel model) {
        this.selectedSingleCategoryModel = model;
        presenter.getSubCategory(model.getId());
    }

    public void setSelectedSubCategory(int pos) {
        if (categoriesAdapter != null) {
            categoriesAdapter.setSelectedPos(pos);
        }
    }

    public void setChildItemData(ProductModel model, int parent_pos) {
        Intent intent = new Intent(activity, ProductDetailsActivity.class);
        intent.putExtra("data", model);
        startActivity(intent);
    }
}
