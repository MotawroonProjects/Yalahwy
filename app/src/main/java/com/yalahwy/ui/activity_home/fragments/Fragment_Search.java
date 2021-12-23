package com.yalahwy.ui.activity_home.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.yalahwy.R;
import com.yalahwy.adapters.SearchAdapter;
import com.yalahwy.databinding.FragmentSearchBinding;
import com.yalahwy.models.ProductModel;
import com.yalahwy.mvp.fragment_search_mvp.FragmentSearchPresenter;
import com.yalahwy.mvp.fragment_search_mvp.FragmentSearchView;
import com.yalahwy.ui.activity_home.HomeActivity;
import com.yalahwy.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Fragment_Search extends Fragment implements FragmentSearchView {
    private FragmentSearchBinding binding;
    private HomeActivity activity;
    private SearchAdapter searchAdapter;
    private List<ProductModel> productModelList;
    private FragmentSearchPresenter presenter;
    private String lang;
    private int selectedPos=-1;

    public static Fragment_Search newInstance(){
        return new Fragment_Search();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        productModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        searchAdapter = new SearchAdapter(productModelList,activity,this,lang);
        binding.recView.setLayoutManager(new GridLayoutManager(activity,2));
        binding.recView.setAdapter(searchAdapter);
        presenter = new FragmentSearchPresenter(activity,this,0.0,0.0);
        binding.edtSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i== EditorInfo.IME_ACTION_SEARCH){
                String query = binding.edtSearch.getText().toString();
                if (!query.isEmpty()){
                    presenter.getSearch(query);
                }
            }

            return false;
        });
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()){
                    binding.progBar.setVisibility(View.GONE);
                    binding.tvNoData.setVisibility(View.VISIBLE);
                    productModelList.clear();
                    searchAdapter.notifyDataSetChanged();
                }else {

                    presenter.getSearch(editable.toString());

                }
            }
        });

    }

    @Override
    public void onSuccess(List<ProductModel> data)
    {
        if (data.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            productModelList.clear();
            productModelList.addAll(data);
            searchAdapter.notifyDataSetChanged();
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onUserNotRegister(String msg, ProductModel productModel, int position) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        productModelList.set(position,productModel);
        searchAdapter.notifyItemChanged(position);
        activity.refreshFragmentHomeData();


    }

    @Override
    public void onFavoriteActionSuccess(ProductModel productModel, int position) {
        productModelList.set(position,productModel);
        searchAdapter.notifyItemChanged(position);
        activity.refreshFragmentHomeData();


    }


    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow()
    {
        binding.progBar.setVisibility(View.VISIBLE);
        binding.tvNoData.setVisibility(View.GONE);
    }
    @Override
    public void onProgressHide()
    {
        binding.progBar.setVisibility(View.GONE);

    }
    public void setProductItemModel(ProductModel model, int adapterPosition)
    {
        selectedPos = adapterPosition;
        Intent intent = new Intent(activity, ProductDetailsActivity.class);
        intent.putExtra("data",model);
        startActivityForResult(intent,100);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode== Activity.RESULT_OK){
            ProductModel model = productModelList.get(selectedPos);
            model.setIs_wishlist(new ProductModel.IsWishList());
            productModelList.set(selectedPos,model);
            searchAdapter.notifyItemChanged(selectedPos);
            selectedPos=-1;
            activity.refreshFragmentHomeData();
        }
    }

    public void add_remove_favorite(ProductModel model, int adapterPosition) {
        presenter.add_remove_favorite(model,adapterPosition);
    }
}
