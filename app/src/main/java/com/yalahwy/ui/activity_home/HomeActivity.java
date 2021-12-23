package com.yalahwy.ui.activity_home;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yalahwy.adapters.HomeCategoriesAdapter;
import com.yalahwy.models.CategorySubCategoryModel;
import com.yalahwy.models.UserModel;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.tags.Tags;
import com.yalahwy.ui.activity_cart.CartActivity;
import com.yalahwy.ui.activity_edit_profile.EditprofileActivity;
import com.yalahwy.ui.activity_sub_category.SubCategoryActivity;
import com.yalahwy.R;
import com.yalahwy.databinding.ActivityHomeBinding;
import com.yalahwy.language.Language;
import com.yalahwy.mvp.activity_home_mvp.ActivityHomePresenter;
import com.yalahwy.mvp.activity_home_mvp.HomeActivityView;
import com.yalahwy.ui.activity_login.LoginActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements HomeActivityView {
    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private ActivityHomePresenter presenter;
    private double lat = 0.0, lng = 0.0;
    private boolean onCategorySelected = false;
    private ActionBarDrawerToggle toggle;
    private HomeCategoriesAdapter categoriesAdapter;
    private List<CategorySubCategoryModel> singleCategoryModelList;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCartItemCount();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 0.0);
        lng = intent.getDoubleExtra("lng", 0.0);
    }

    private void initView() {

        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        binding.setModel(userModel);
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolBar, R.string.open, R.string.close);
        toggle.syncState();
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);        fragmentManager = getSupportFragmentManager();
        presenter = new ActivityHomePresenter(this, this, fragmentManager, lat, lng);
        binding.navigationView.setOnNavigationItemSelectedListener(item -> {
            if (!onCategorySelected) {
                presenter.manageFragments(item);

            }
            onCategorySelected = false;
            return true;
        });
        binding.toolBar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        singleCategoryModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        categoriesAdapter = new HomeCategoriesAdapter(singleCategoryModelList, this);
        binding.recView.setAdapter(categoriesAdapter);

        binding.flCart.setOnClickListener(view -> {
            presenter.cart();
        });

        binding.swipeRefresh.setOnRefreshListener(() -> presenter.getCategory());

        binding.tvUserName.setOnClickListener(v -> {
            if (userModel == null) {
                presenter.backPress();

            } else {
                Intent intent = new Intent(HomeActivity.this, EditprofileActivity.class);
                startActivity(intent);
            }
        });

        binding.fluser.setOnClickListener(v -> {
            if (userModel == null) {
                presenter.backPress();

            } else {
                Intent intent = new Intent(HomeActivity.this, EditprofileActivity.class);
                startActivityForResult(intent,500);
            }
        });
        if(userModel!=null){
            Picasso.get().load(Tags.IMAGE_User_URL + userModel.getData().getPhoto()).placeholder(R.drawable.ic_user).into(binding.image);

        }
    }

    public void displayFragmentCategory(int pos) {
        presenter.displayFragmentCategories(pos);
    }

    public void refreshFragmentHomeData() {
        presenter.refreshFragmentHomeData();
    }

    public void logout() {

        presenter.logout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        if(requestCode==500){
            userModel=preferences.getUserData(this);
            if(userModel!=null){
                Picasso.get().load(Tags.IMAGE_User_URL + userModel.getData().getPhoto()).placeholder(R.drawable.ic_user).into(binding.image);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void onBackPressed() {
        presenter.backPress();
    }

    @Override
    public void onHomeFragmentSelected() {
        onCategorySelected = false;
        binding.navigationView.setSelectedItemId(R.id.home);
    }

    @Override
    public void onCategoryFragmentSelected() {
        onCategorySelected = true;
        binding.navigationView.setSelectedItemId(R.id.categories);

    }


    @Override
    public void onNavigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onNavigateToCartActivity() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);

    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartCountUpdate(int count) {
        binding.setCartcount(count);
    }


    @Override
    public void onFinished() {
        finish();
    }


    @Override
    public void onSuccess(List<CategorySubCategoryModel> data) {
        if (data.size() > 0) {
            singleCategoryModelList.clear();
            singleCategoryModelList.addAll(data);
            categoriesAdapter.notifyDataSetChanged();
            binding.tvNoData.setVisibility(View.GONE);


        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onProgressCategoryShow() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressCategoryHide() {
        binding.progBar.setVisibility(View.GONE);
        binding.swipeRefresh.setRefreshing(false);

    }


    public void refreshActivity(String lang) {
        Paper.init(this);
        Paper.book().write("lang", lang);
        Language.updateResources(this, lang);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.updateCartModel();
    }


    public void setItemData(CategorySubCategoryModel model, int adapterPosition) {
        Intent intent = new Intent(this, SubCategoryActivity.class);
        intent.putExtra("data", model);
        startActivity(intent);
    }

}