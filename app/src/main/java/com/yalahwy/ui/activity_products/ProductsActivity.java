package com.yalahwy.ui.activity_products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.yalahwy.R;
import com.yalahwy.adapters.ProductAdapter2;
import com.yalahwy.databinding.ActivityProductsBinding;
import com.yalahwy.databinding.DialogCartBinding;
import com.yalahwy.language.Language;
import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.ProductModel;
import com.yalahwy.models.SliderDataModel;
import com.yalahwy.models.SubCategoryModel;
import com.yalahwy.mvp.activity_product_mvp.ActivityProductPresenter;
import com.yalahwy.mvp.activity_product_mvp.ActivityProductView;
import com.yalahwy.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ProductsActivity extends AppCompatActivity implements ActivityProductView {
    private ActivityProductsBinding binding;
    private String lang;
    private SubCategoryModel subCategoryModel;
    private List<ProductModel> productModelList;
    private ProductAdapter2 adapter;
    private ActivityProductPresenter presenter;
    private SliderDataModel.SliderModel sliderModel;
    private boolean isDataChanged = false;
    private int selectedPos=-1;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if(intent.getSerializableExtra("data")!=null){
        subCategoryModel = (SubCategoryModel) intent.getSerializableExtra("data");}
        else {
            sliderModel = (SliderDataModel.SliderModel) intent.getSerializableExtra("top");}



    }

    private void initView() {
        productModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter2(productModelList,this);
        binding.recView.setAdapter(adapter);

        presenter= new ActivityProductPresenter(this,this);
        if(subCategoryModel!=null) {
            presenter.getProducts(subCategoryModel.getId());
        }
        else {
            presenter.getProductsbytype(sliderModel.getKey());}
        binding.imageBack.setOnClickListener(view -> finish());

    }


    @Override
    public void onSuccess(List<ProductModel> data) {
        productModelList.clear();
        if (data.size()>0){
            productModelList.addAll(data);
            adapter.notifyDataSetChanged();
            binding.tvNoData.setVisibility(View.GONE);
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);

    }

    @Override
    public void onAddToMenuSuccess() {
        Toast.makeText(this, R.string.suc, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCartUpdated(double totalCost, int itemCount, List<CartDataModel.CartModel> cartModelList) {

    }

    @Override
    public void onCartCountUpdated(int count) {

    }

    @Override
    public void onAmountSelectedFromCart(int amount) {

    }

    public void setProductItemModel(ProductModel model) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("data", model);
        startActivityForResult(intent, 100);
    }
    public void createDialogAlert(ProductModel productModel,int amount) {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();

        DialogCartBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_cart, null, false);

        binding.btnCancel.setOnClickListener(v -> dialog.dismiss()

        );
        binding.btnAdd.setOnClickListener(v -> {

                    if (binding.checkboxMenu.isChecked() && binding.checkboxCart.isChecked()) {
                        presenter.add_to_menu(productModel, amount);
                        presenter.add_to_cart(productModel,amount);
                        Toast.makeText(this, R.string.suc, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    } else if (binding.checkboxCart.isChecked()) {
                        presenter.add_to_cart(productModel,amount);
                        Toast.makeText(this, R.string.suc, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    } else if (binding.checkboxMenu.isChecked()) {
                        presenter.add_to_menu(productModel, amount);
                        Toast.makeText(this, R.string.suc, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();

                    } else {
                        Toast.makeText(this, R.string.ch_cart_menu, Toast.LENGTH_SHORT).show();
                    }

                }


        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }
    public void removeFavorite(ProductModel model, int adapterPosition) {
        selectedPos = adapterPosition;
        presenter.remove_favorite(model);

    }
    @Override
    public void onRemoveFavoriteSuccess()
    {
        isDataChanged = true;
        if (productModelList.size()>0&&selectedPos!=-1){
            productModelList.remove(selectedPos);
            adapter.notifyItemRemoved(selectedPos);
            if (productModelList.size()>0){
                binding.tvNoData.setVisibility(View.GONE);
            }else {
                binding.tvNoData.setVisibility(View.VISIBLE);

            }
            selectedPos=-1;
        }
    }
    public void add_remove_favorite(ProductModel productModel, int adapterPosition, String type) {

        presenter.add_remove_favorite(productModel, adapterPosition, type);
    }
    @Override
    public void onUserNotRegister(String msg, ProductModel productModel, int position, String type) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        productModelList.set(position, productModel);
        adapter.notifyItemChanged(position);


    }

    @Override
    public void onFavoriteActionSuccess(ProductModel productModel, int position, String type) {

if(productModel.getIs_wishlist()!=null){
    Log.e("dllddkk",productModel.getIs_wishlist().getId()+"");
}
        productModelList.set(position, productModel);
        adapter.notifyItemChanged(position);

        //  presenter.getSearch(type);
    }
}