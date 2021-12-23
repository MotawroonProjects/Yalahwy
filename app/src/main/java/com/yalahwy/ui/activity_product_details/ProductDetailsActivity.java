package com.yalahwy.ui.activity_product_details;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.yalahwy.R;
import com.yalahwy.adapters.ProductSliderAdapter;
import com.yalahwy.adapters.ViewPagerAdapter;
import com.yalahwy.databinding.ActivityProductDetailsBinding;
import com.yalahwy.databinding.DialogCartBinding;
import com.yalahwy.language.Language;
import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.GalleryModel;
import com.yalahwy.models.ProductModel;
import com.yalahwy.mvp.activity_product_details_mvp.ActivityProductDetailsPresenter;
import com.yalahwy.mvp.activity_product_details_mvp.ActivityProductDetailsView;
import com.yalahwy.tags.Tags;
import com.yalahwy.ui.activity_cart.CartActivity;
import com.yalahwy.ui.activity_product_details.fragments.Fragment_Comments;
import com.yalahwy.ui.activity_product_details.fragments.Fragment_Details;
import com.yalahwy.ui.activity_product_details.fragments.Fragment_Policy;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ProductDetailsActivity extends AppCompatActivity implements ActivityProductDetailsView {
    private ActivityProductDetailsBinding binding;
    private ActivityProductDetailsPresenter presenter;
    private String lang;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private ProductSliderAdapter sliderAdapter;
    private ProductModel productModel;
    private int amount = 1;
    private boolean isDataChanged = false;
    private int product_id;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        getDataFromIntent();
        initView();


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("data")){
            productModel = (ProductModel) intent.getSerializableExtra("data");
            product_id = productModel.getId();
        }else {
            product_id = Integer.parseInt(intent.getData().getLastPathSegment());
        }

    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        presenter = new ActivityProductDetailsPresenter(this, this);
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();

        presenter.getProductById(String.valueOf(product_id));
        binding.imageIncrease.setOnClickListener(view -> {
            amount++;
            binding.tvAmount.setText(String.valueOf(amount));
        });

        binding.imageDecrease.setOnClickListener(view -> {
            if (amount > 1) {
                amount--;
                binding.tvAmount.setText(String.valueOf(amount));
            }
        });


        binding.flAddToCart.setOnClickListener(view -> createDialogAlert());

        binding.llBack.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.checkbox.setOnClickListener(view -> {
            if (binding.checkbox.isChecked()) {
                productModel.setIs_wishlist(new ProductModel.IsWishList());

            } else {
                productModel.setIs_wishlist(null);

            }

            presenter.add_remove_favorite(productModel);
        });

        binding.flCart.setOnClickListener(view -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });

        binding.btnShare.setOnClickListener(v -> {
            String shareLink = Tags.base_url+"api/shareLink/"+productModel.getId();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,shareLink);
            startActivity(intent);
        });
    }


    private void createDialogAlert() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();

        DialogCartBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_cart, null, false);

        binding.btnCancel.setOnClickListener(v -> dialog.dismiss()

        );
        binding.btnAdd.setOnClickListener(v -> {

                    if (binding.checkboxMenu.isChecked() && binding.checkboxCart.isChecked()) {
                        presenter.add_to_menu(productModel, amount);
                        presenter.add_to_cart(productModel,amount);
                        dialog.dismiss();
                    } else if (binding.checkboxCart.isChecked()) {
                        presenter.add_to_cart(productModel,amount);
                        dialog.dismiss();
                    } else if (binding.checkboxMenu.isChecked()) {
                        presenter.add_to_menu(productModel, amount);
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


    @Override
    public void onSuccess(ProductModel data) {
        this.productModel = data;
        binding.setModel(data);

        binding.tab.setupWithViewPager(binding.pager);
        if (productModel.getGalleries().size() > 0) {
            List<GalleryModel> galleryModelList = new ArrayList<>(productModel.getGalleries());
            sliderAdapter = new ProductSliderAdapter(galleryModelList, this);
            binding.pager.setAdapter(sliderAdapter);
            binding.flSlider.setVisibility(View.VISIBLE);
            binding.image.setVisibility(View.GONE);

        } else {
            binding.image.setVisibility(View.VISIBLE);
            binding.flSlider.setVisibility(View.GONE);

        }


        fragmentList.add(Fragment_Details.newInstance(data));
        fragmentList.add(Fragment_Comments.newInstance(data));
        fragmentList.add(Fragment_Policy.newInstance(data));

        titles.add(getString(R.string.descriptions));
        titles.add(getString(R.string.comments));
        titles.add(getString(R.string.policy));


        binding.tabLayout.setupWithViewPager(binding.pager2);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragments_Titles(fragmentList, titles);
        binding.pager2.setAdapter(adapter);
        binding.pager2.setOffscreenPageLimit(fragmentList.size());
        binding.pager2.setCurrentItem(0, true);

        binding.view.setVisibility(View.GONE);
        binding.setCommentsCount(String.valueOf(data.getComments().size()));

    }

    public void updateCommentsCount(int count) {
        binding.setCommentsCount(String.valueOf(count));

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
    public void onUserNotRegister(String msg, ProductModel productModel) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        this.productModel = productModel;
        binding.setModel(productModel);
        isDataChanged = false;


    }

    @Override
    public void onFavoriteActionSuccess(ProductModel productModel) {
        this.productModel = productModel;
        binding.setModel(productModel);
        isDataChanged = true;
    }

    @Override
    public void onAddToMenuSuccess() {
        Toast.makeText(this, R.string.suc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartUpdated(double totalCost, int itemCount, List<CartDataModel.CartModel> cartModelList) {
        binding.setCount(itemCount);
    }

    @Override
    public void onCartCountUpdated(int count) {
        binding.setCount(count);
    }

    @Override
    public void onAmountSelectedFromCart(int amount) {
        this.amount = amount;
        binding.tvAmount.setText(String.valueOf(amount));

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCartCount();
      //  presenter.getItemAmount(productModel);


    }

    @Override
    public void onBackPressed() {
        if (isDataChanged) {
            setResult(RESULT_OK);
        }
        finish();
    }
}