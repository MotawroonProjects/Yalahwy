package com.yalahwy.ui.activity_product_details.fragments;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.yalahwy.R;
import com.yalahwy.databinding.FragmentDetailsBinding;
import com.yalahwy.models.ProductModel;
import com.yalahwy.ui.activity_product_details.ProductDetailsActivity;

public class Fragment_Details extends Fragment {
    private FragmentDetailsBinding binding;
    private ProductDetailsActivity activity;
    private ProductModel productModel;


    public static Fragment_Details newInstance(ProductModel productModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", productModel);
        Fragment_Details fragment_details = new Fragment_Details();
        fragment_details.setArguments(bundle);
        return fragment_details;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e("mmmmmmmmmm",productModel.getDetails()+"_________");
           // if (!productModel.getDetails().equals(null)){
                binding.tvDetails.setText(Html.fromHtml(productModel.getDetails(), Html.FROM_HTML_MODE_COMPACT));

          /*  }else {
                binding.tvDetails.setText(Html.fromHtml(productModel.getDetails()));

            }*/
        } else {
            binding.tvDetails.setText(Html.fromHtml(productModel.getDetails()));
        }
    }
}
