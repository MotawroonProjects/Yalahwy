package com.yalahwy.ui.activity_home.fragments;

import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yalahwy.R;
import com.yalahwy.adapters.BottomImageAdapter;
import com.yalahwy.adapters.FavoriteProductAdapter;
import com.yalahwy.adapters.MainCategoriesAdapter;
import com.yalahwy.adapters.OfferProductAdapter;
import com.yalahwy.adapters.ProductAdapter;
import com.yalahwy.adapters.SliderAdapter;
import com.yalahwy.adapters.TopImageAdapter;
import com.yalahwy.databinding.DialogCartBinding;
import com.yalahwy.databinding.FragmentHomeBinding;

import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.CategorySubCategoryModel;
import com.yalahwy.models.GalleryModel;
import com.yalahwy.models.ProductModel;
import com.yalahwy.models.SliderDataModel;
import com.yalahwy.models.SubCategoryModel;
import com.yalahwy.models.UserModel;
import com.yalahwy.mvp.fragment_home_mvp.FragmentHomePresenter;
import com.yalahwy.mvp.fragment_home_mvp.FragmentHomeView;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.ui.activity_home.HomeActivity;
import com.yalahwy.ui.activity_product_details.ProductDetailsActivity;
import com.yalahwy.ui.activity_products.ProductsActivity;
import com.yalahwy.ui.activity_search.SearchActivity;
import com.yalahwy.ui.activity_sub_category.SubCategoryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;


public class Fragment_Home extends Fragment implements FragmentHomeView {
    private FragmentHomeBinding binding;
    private double lat = 0.0, lng = 0.0;
    private HomeActivity activity;
    private FragmentHomePresenter presenter;
    private SliderAdapter sliderAdapter;
    private ProductAdapter featuredProductAdapter, mostSellerAdapter, otherProductAdapter;
    private OfferProductAdapter offerProductAdapter;
    private List<ProductModel> featuredProductList, mostSellerProductList, offerProductList, otherProductList;
    private List<SliderDataModel.SliderModel> topimagelist;
    private TopImageAdapter topImageAdapter;
    private List<CategorySubCategoryModel> list;
    private MainCategoriesAdapter adapter;
    private List<GalleryModel> galleryModelList;
    private BottomImageAdapter bottomImageAdapter;
    private List<ProductModel> productModelList;
    private FavoriteProductAdapter favoriteProductAdapter;
    private Preferences preferences;
    private UserModel userModel;
    private boolean isDataChanged = false;
    private int selectedPos = -1;
    private Timer timer;
    private TimerTask timerTask;
    private String lang = "ar";


    public static Fragment_Home newInstance(double lat, double lng) {
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", lat);
        bundle.putDouble("lng", lng);
        Fragment_Home fragment_home = new Fragment_Home();
        fragment_home.setArguments(bundle);
        return fragment_home;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        preferences = Preferences.getInstance();
        activity = (HomeActivity) getActivity();
        userModel = preferences.getUserData(activity);
        topimagelist = new ArrayList<>();
        mostSellerProductList = new ArrayList<>();
        featuredProductList = new ArrayList<>();
        offerProductList = new ArrayList<>();
        otherProductList = new ArrayList<>();
        galleryModelList = new ArrayList<>();
        productModelList = new ArrayList<>();
        favoriteProductAdapter = new FavoriteProductAdapter(productModelList, activity, this);

        binding.recViewfavourite.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewfavourite.setAdapter(favoriteProductAdapter);
        list = new ArrayList<>();
        adapter = new MainCategoriesAdapter(list, activity, this);

        binding.recView.setLayoutManager(new LinearLayoutManager(activity));

        binding.recView.setAdapter(adapter);


        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        topImageAdapter = new TopImageAdapter(topimagelist, activity, this, lang);
        binding.recViewimage.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recViewimage.setAdapter(topImageAdapter);
        bottomImageAdapter = new BottomImageAdapter(galleryModelList, activity, this, lang);
        binding.recViewbotoomimage.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recViewbotoomimage.setAdapter(bottomImageAdapter);
        Bundle bundle = getArguments();
        if (bundle != null) {
            lat = bundle.getDouble("lat");
            lng = bundle.getDouble("lng");
        }
        binding.progBarbottomimage.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        binding.progBarimage.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        binding.progBarSlider.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        binding.tab.setupWithViewPager(binding.pager);




    /*    binding.recViewCategories.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) binding.recViewCategories.getLayoutManager();
                int firstPos = manager.findFirstCompletelyVisibleItemPosition();
                int lastPos = manager.findLastCompletelyVisibleItemPosition();

                try {
                    if (lastPos == singleCategoryModelList.size()-1){
                        binding.card2.setVisibility(View.GONE);
                    }else {
                        binding.card2.setVisibility(View.VISIBLE);

                    }

                    if (firstPos==0){
                        binding.card1.setVisibility(View.GONE);
                        binding.card2.setVisibility(View.VISIBLE);
                    }else {
                        binding.card1.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){}






            }
        });*/

        binding.llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
            }
        });
        presenter = new FragmentHomePresenter(activity, this, lat, lng);
        presenter.getSlider();
        presenter.getData();
        presenter.getTopImage();
        presenter.getBottomImage();
        presenter.getProducts();
        if (userModel == null) {
            binding.tvfav.setVisibility(View.GONE);
            binding.flfav.setVisibility(View.GONE);
        }
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getSlider();
                presenter.getData();
                presenter.getTopImage();
                presenter.getBottomImage();
                presenter.getProducts();
            }
        });
      /*  presenter.getFeaturedProducts();
        presenter.getMostSellerProducts();
        presenter.getOfferProducts();
        presenter.getOtherProducts();*/


    }

    @Override
    public void onSliderSuccess(List<SliderDataModel.SliderModel> sliderModelList) {
        if (sliderModelList.size() > 0) {
            binding.flSlider.setVisibility(View.VISIBLE);
            sliderAdapter = new SliderAdapter(sliderModelList, activity);
            binding.pager.setAdapter(sliderAdapter);
            if (sliderModelList.size() > 1) {
                timer = new Timer();
                timerTask = new MyTask();
                timer.scheduleAtFixedRate(timerTask, 6000, 6000);
            }

        } else {
            binding.flSlider.setVisibility(View.GONE);
        }


    }


    @Override
    public void onFeaturedProductSuccess(List<ProductModel> data) {

    }

    @Override
    public void onMostSellerSuccess(List<ProductModel> data) {

    }

    @Override
    public void onOtherProductSuccess(List<ProductModel> data) {

    }

    @Override
    public void onOfferSuccess(List<ProductModel> data) {

    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNotRegister(String msg, ProductModel productModel, int position, String type) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

        if (type.equals("1")) {
            featuredProductList.set(position, productModel);
            featuredProductAdapter.notifyItemChanged(position);
        } else if (type.equals("2")) {
            mostSellerProductList.set(position, productModel);
            mostSellerAdapter.notifyItemChanged(position);
        } else if (type.equals("3")) {
            offerProductList.set(position, productModel);
            offerProductAdapter.notifyItemChanged(position);
        } else if (type.equals("4")) {
            otherProductList.set(position, productModel);
            otherProductAdapter.notifyItemChanged(position);
        }

    }

    @Override
    public void onFavoriteActionSuccess(ProductModel productModel, int position, String type) {

        if (type.equals("1")) {
            featuredProductList.set(position, productModel);
            featuredProductAdapter.notifyItemChanged(position);
        } else if (type.equals("2")) {
            mostSellerProductList.set(position, productModel);
            mostSellerAdapter.notifyItemChanged(position);
        } else if (type.equals("3")) {
            offerProductList.set(position, productModel);
            offerProductAdapter.notifyItemChanged(position);
        } else if (type.equals("4")) {
            otherProductList.set(position, productModel);
            otherProductAdapter.notifyItemChanged(position);
        }
        presenter.getProducts();
    }

    @Override
    public void onProgressSliderShow() {
        binding.progBarSlider.setVisibility(View.VISIBLE);

    }

    @Override
    public void onProgressSliderHide() {
        binding.progBarSlider.setVisibility(View.GONE);

    }


    @Override
    public void onProgressFeaturedProductsShow() {

    }

    @Override
    public void onProgressFeaturedProductsHide() {

    }

    @Override
    public void onProgressMostSellerShow() {

    }

    @Override
    public void onProgressMostSellerHide() {

    }

    @Override
    public void onProgressOfferShow() {

    }

    @Override
    public void onProgressOfferHide() {

    }

    @Override
    public void onProgressOtherProductsShow() {

    }

    @Override
    public void onProgressOtherProductsHide() {

    }

    @Override
    public void onDataSuccess(List<CategorySubCategoryModel> list) {
        this.list.clear();
        adapter.notifyDataSetChanged();

        this.list.addAll(list);
        adapter.notifyDataSetChanged();

        if (this.list.size() > 0) {
            binding.tvNoData.setVisibility(View.GONE);
        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }

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
    public void onProgressTopImageShow() {
        binding.progBarimage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressTopImageHide() {
        binding.progBarimage.setVisibility(View.GONE);
    }

    @Override
    public void onDataTopImegSuccess(List<SliderDataModel.SliderModel> data) {
//        if (data.size() == 0) {
//            binding.tvNoDataimage.setVisibility(View.VISIBLE);
//        } else {
//            binding.tvNoDataimage.setVisibility(View.GONE);
        topimagelist.clear();
        topimagelist.addAll(data);
        topImageAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onProgressBottomImegShow() {
        binding.progBarbottomimage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressBottomImegHide() {
        binding.progBarbottomimage.setVisibility(View.GONE);
    }

    @Override
    public void onDataBottomImegSuccess(List<GalleryModel> data) {
//        if (data.size() == 0) {
//            binding.tvNoDatabottomimage.setVisibility(View.VISIBLE);
//        } else {
        binding.tvNoDatabottomimage.setVisibility(View.GONE);
        galleryModelList.clear();
        galleryModelList.addAll(data);
        bottomImageAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onProgressfavShow() {
        binding.progBarfav.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressfavHide() {
        binding.progBarfav.setVisibility(View.GONE);
    }

/*
    public void setItemData(int pos) {
        activity.displayFragmentCategory(pos);
    }
*/

    public void setProductItemModel(ProductModel model) {
        Intent intent = new Intent(activity, ProductDetailsActivity.class);
        intent.putExtra("data", model);
        startActivityForResult(intent, 100);
    }

    public void setProductItemModel(SliderDataModel.SliderModel model) {
        Intent intent = new Intent(activity, ProductsActivity.class);
        intent.putExtra("top", model);
        startActivityForResult(intent, 100);
    }


    public void add_remove_favorite(ProductModel productModel, int adapterPosition, String type) {

        presenter.add_remove_favorite(productModel, adapterPosition, type);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            refreshData();
        }
    }

    public void refreshData() {
        presenter.getOfferProducts();
        presenter.getMostSellerProducts();
        presenter.getFeaturedProducts();
    }

    public void setSubCategoryData(CategorySubCategoryModel categorySubCategoryModel) {
        Intent intent = new Intent(activity, SubCategoryActivity.class);
        intent.putExtra("data", categorySubCategoryModel);
        startActivity(intent);
    }

    public void setItemSubCategoryModel(SubCategoryModel subCategoryModel) {
        Intent intent = new Intent(activity, ProductsActivity.class);
        intent.putExtra("data", subCategoryModel);
        startActivity(intent);
    }


    public class MyTask extends TimerTask {
        @Override
        public void run() {
            activity.runOnUiThread(() -> {
                int current_page = binding.pager.getCurrentItem();
                if (current_page < sliderAdapter.getCount() - 1) {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1);
                } else {
                    binding.pager.setCurrentItem(0);

                }
            });

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }

    }

    @Override
    public void onSuccess(List<ProductModel> data) {
        productModelList.clear();
        favoriteProductAdapter.notifyDataSetChanged();
        binding.swipeRefresh.setRefreshing(false);

        if (data.size() > 0) {
            binding.tvfav.setVisibility(View.VISIBLE);

            productModelList.addAll(data);
            binding.tvNoData.setVisibility(View.GONE);
            favoriteProductAdapter.notifyDataSetChanged();
        } else {
            binding.tvfav.setVisibility(View.GONE);

            // binding.tvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRemoveFavoriteSuccess() {
        isDataChanged = true;
        if (productModelList.size() > 0 && selectedPos != -1) {
            productModelList.remove(selectedPos);
            adapter.notifyItemRemoved(selectedPos);
            if (productModelList.size() > 0) {
                binding.tvNoData.setVisibility(View.GONE);
            } else {
                binding.tvNoData.setVisibility(View.VISIBLE);

            }
            selectedPos = -1;
        }
        presenter.getProducts();
    }

    public void removeFavorite(ProductModel model, int adapterPosition) {
        selectedPos = adapterPosition;
        presenter.remove_favorite(model);

    }

    @Override
    public void onAddToMenuSuccess() {
        Toast.makeText(activity, R.string.suc, Toast.LENGTH_SHORT).show();

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

    public void createDialogAlert(ProductModel productModel, int amount) {
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .create();

        DialogCartBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_cart, null, false);

        binding.btnCancel.setOnClickListener(v -> dialog.dismiss()

        );
        binding.btnAdd.setOnClickListener(v -> {

                    if (binding.checkboxMenu.isChecked() && binding.checkboxCart.isChecked()) {
                        presenter.add_to_menu(productModel, amount);
                        presenter.add_to_cart(productModel, amount);
                        Toast.makeText(activity, R.string.suc, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    } else if (binding.checkboxCart.isChecked()) {
                        presenter.add_to_cart(productModel, amount);
                        Toast.makeText(activity, R.string.suc, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    } else if (binding.checkboxMenu.isChecked()) {
                        presenter.add_to_menu(productModel, amount);
                        Toast.makeText(activity, R.string.suc, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();

                    } else {
                        Toast.makeText(activity, R.string.ch_cart_menu, Toast.LENGTH_SHORT).show();
                    }

                }


        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

}
