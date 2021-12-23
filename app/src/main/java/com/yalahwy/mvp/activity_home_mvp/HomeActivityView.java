package com.yalahwy.mvp.activity_home_mvp;

import com.yalahwy.models.CategorySubCategoryModel;

import java.util.List;

public interface HomeActivityView {
    void onHomeFragmentSelected();

    void onCategoryFragmentSelected();

    void onNavigateToLoginActivity();

    void onFinished();

    void onNavigateToCartActivity();

    void onFailed(String msg);

    void onCartCountUpdate(int count);

    void onSuccess(List<CategorySubCategoryModel> data);

    void onProgressCategoryShow();

    void onProgressCategoryHide();
}
