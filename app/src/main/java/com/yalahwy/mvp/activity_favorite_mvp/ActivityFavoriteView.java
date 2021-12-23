package com.yalahwy.mvp.activity_favorite_mvp;

import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.ProductModel;

import java.util.List;

public interface ActivityFavoriteView {
    void onSuccess(List<ProductModel> data);
    void onRemoveFavoriteSuccess();
    void onFailed(String msg);
    void showProgressDialog();
    void hideProgressDialog();
    void onProgressShow();
    void onProgressHide();
    void onAddToMenuSuccess();
    void onCartUpdated(double totalCost, int itemCount, List<CartDataModel.CartModel> cartModelList);

    void onCartCountUpdated(int count);
    void onAmountSelectedFromCart(int amount);

}
