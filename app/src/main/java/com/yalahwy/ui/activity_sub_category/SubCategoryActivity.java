package com.yalahwy.ui.activity_sub_category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yalahwy.R;
import com.yalahwy.adapters.SubCategories2Adapter;
import com.yalahwy.databinding.ActivitySubCategoryBinding;
import com.yalahwy.language.Language;
import com.yalahwy.models.CategorySubCategoryModel;
import com.yalahwy.models.SubCategoryModel;
import com.yalahwy.ui.activity_products.ProductsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SubCategoryActivity extends AppCompatActivity {

    private ActivitySubCategoryBinding binding;
    private String lang;
    private CategorySubCategoryModel categorySubCategoryModel;
    private List<SubCategoryModel> subCategoryModelList;
    private SubCategories2Adapter adapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_category);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        categorySubCategoryModel = (CategorySubCategoryModel) intent.getSerializableExtra("data");

    }

    private void initView() {
        subCategoryModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setModel(categorySubCategoryModel);
        binding.recView.setLayoutManager(new GridLayoutManager(this,2));
        subCategoryModelList.addAll(categorySubCategoryModel.getSubs());
        adapter = new SubCategories2Adapter(subCategoryModelList, this);
        binding.recView.setAdapter(adapter);

        if (subCategoryModelList.size() > 0) {
            binding.tvNoData.setVisibility(View.GONE);
        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }


        binding.llBack.setOnClickListener(view -> finish());

    }


    public void setItemModel(SubCategoryModel subCategoryModel) {
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtra("data", subCategoryModel);
        startActivity(intent);
    }
}