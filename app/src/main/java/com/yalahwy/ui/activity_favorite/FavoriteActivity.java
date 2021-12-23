package com.yalahwy.ui.activity_favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.yalahwy.R;
import com.yalahwy.adapters.FavoriteProductAdapter;
import com.yalahwy.databinding.ActivityFavoriteBinding;
import com.yalahwy.databinding.DialogCartBinding;
import com.yalahwy.language.Language;
import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.ProductModel;
import com.yalahwy.mvp.activity_favorite_mvp.ActivityFavoritePresenter;
import com.yalahwy.mvp.activity_favorite_mvp.ActivityFavoriteView;
import com.yalahwy.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FavoriteActivity extends AppCompatActivity implements ActivityFavoriteView {
    private ActivityFavoriteBinding binding;
    private String lang;
    private List<ProductModel> productModelList;
    private FavoriteProductAdapter adapter;
    private ActivityFavoritePresenter presenter;
    private boolean isDataChanged = false;
    private int selectedPos=-1;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);
        initView();

    }
    private void initView()
    {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        productModelList = new ArrayList<>();
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoriteProductAdapter(productModelList,this,null);
        binding.recView.setAdapter(adapter);
        presenter = new ActivityFavoritePresenter(this,this);
        presenter.getProducts();
        binding.llBack.setOnClickListener(view -> onBackPressed());


    }

    public void setProductItemModel(ProductModel model) {
        Intent intent = new Intent(FavoriteActivity.this, ProductDetailsActivity.class);
        intent.putExtra("data",model);
        startActivityForResult(intent,100);
    }


    @Override
    public void onSuccess(List<ProductModel> data)
    {

        if (data.size()>0){
            productModelList.addAll(data);
            binding.tvNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);
        }
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
    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void onProgressShow()
    {
        productModelList.clear();
        binding.progBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void onProgressHide()
    {
        binding.progBar.setVisibility(View.GONE);

    }
    @Override
    public void onBackPressed()
    {
        if (isDataChanged){
            setResult(RESULT_OK);
        }
        finish();
    }

    public void removeFavorite(ProductModel model, int adapterPosition) {
        selectedPos = adapterPosition;
        presenter.remove_favorite(model);

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
}