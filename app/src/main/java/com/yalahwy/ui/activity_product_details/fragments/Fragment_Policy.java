package com.yalahwy.ui.activity_product_details.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.yalahwy.R;
import com.yalahwy.databinding.FragmentPolicyBinding;
import com.yalahwy.models.ProductModel;
import com.yalahwy.ui.activity_product_details.ProductDetailsActivity;

public class Fragment_Policy extends Fragment {
    private FragmentPolicyBinding binding;
    private ProductDetailsActivity activity;

    private ProductModel productModel;


    public static Fragment_Policy newInstance(ProductModel productModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", productModel);
        Fragment_Policy fragment_policy = new Fragment_Policy();
        fragment_policy.setArguments(bundle);
        return fragment_policy;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_policy,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (ProductDetailsActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            productModel = (ProductModel) bundle.getSerializable("data");
        }
        binding.setModel(productModel);
    }
}
