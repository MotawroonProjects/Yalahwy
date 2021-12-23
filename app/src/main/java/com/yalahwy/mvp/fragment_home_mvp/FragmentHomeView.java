package com.yalahwy.mvp.fragment_home_mvp;

import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.CategorySubCategoryModel;
import com.yalahwy.models.GalleryModel;
import com.yalahwy.models.ProductModel;
import com.yalahwy.models.SliderDataModel;

import java.util.List;

public interface FragmentHomeView {
    void onSliderSuccess(List<SliderDataModel.SliderModel> sliderModelList);
    void onFeaturedProductSuccess(List<ProductModel> data);
    void onMostSellerSuccess(List<ProductModel> data);
    void onOtherProductSuccess(List<ProductModel> data);

    void onOfferSuccess(List<ProductModel> data);
    void onFailed(String msg);
    void onUserNotRegister(String msg,ProductModel productModel,int position,String type);
    void onFavoriteActionSuccess(ProductModel productModel,int position,String type);
    void onProgressSliderShow();
    void onProgressSliderHide();

    void onProgressFeaturedProductsShow();
    void onProgressFeaturedProductsHide();
    void onProgressMostSellerShow();
    void onProgressMostSellerHide();
    void onProgressOfferShow();
    void onProgressOfferHide();

    void onProgressOtherProductsShow();
    void onProgressOtherProductsHide();

    void onSuccess(List<ProductModel> data);
    void onRemoveFavoriteSuccess();


    void onDataSuccess(List<CategorySubCategoryModel> list);

    void onProgressShow();
    void onProgressHide();

    void onProgressTopImageShow();

    void onProgressTopImageHide();

    void onDataTopImegSuccess(List<SliderDataModel.SliderModel> data);

    void onProgressBottomImegShow();

    void onProgressBottomImegHide();

    void onDataBottomImegSuccess(List<GalleryModel> data);

    void onProgressfavShow();

    void onProgressfavHide();
    void onAddToMenuSuccess();
    void onCartUpdated(double totalCost, int itemCount, List<CartDataModel.CartModel> cartModelList);

    void onCartCountUpdated(int count);
    void onAmountSelectedFromCart(int amount);
}
