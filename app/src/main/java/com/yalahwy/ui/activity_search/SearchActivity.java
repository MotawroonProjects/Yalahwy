package com.yalahwy.ui.activity_search;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yalahwy.R;
import com.yalahwy.adapters.ProductAdapter2;
import com.yalahwy.databinding.ActivitySearchBinding;
import com.yalahwy.databinding.DialogCartBinding;
import com.yalahwy.language.Language;
import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.ProductModel;
import com.yalahwy.mvp.activity_product_mvp.ActivityProductPresenter;
import com.yalahwy.mvp.activity_product_mvp.ActivityProductView;
import com.yalahwy.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SearchActivity extends AppCompatActivity implements ActivityProductView {
    private ActivitySearchBinding binding;
    private String lang;
    private List<ProductModel> productModelList;
    private ProductAdapter2 productAdapter;
    private ActivityProductPresenter presenter;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        initView();

    }



    private void initView() {
        productModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter2(productModelList,this);
        binding.recView.setAdapter(productAdapter);

        presenter= new ActivityProductPresenter(this,this);
//        presenter.getProducts(subCategoryModel.getId());

        binding.imageBack.setOnClickListener(view -> finish());
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
                    productAdapter.notifyDataSetChanged();
                }else {

                    presenter.getSearch(editable.toString());

                }
            }
        });

    }




    @Override
    public void onSuccess(List<ProductModel> data) {
        productModelList.clear();
        if (data.size()>0){
            productModelList.addAll(data);
            productAdapter.notifyDataSetChanged();
            binding.tvNoData.setVisibility(View.GONE);
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }

        productAdapter.notifyDataSetChanged();

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
            productAdapter.notifyItemRemoved(selectedPos);
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
            productAdapter.notifyItemChanged(position);


    }

    @Override
    public void onFavoriteActionSuccess(ProductModel productModel, int position, String type) {


            productModelList.set(position, productModel);
            productAdapter.notifyItemChanged(position);

      //  presenter.getSearch(type);
    }


}