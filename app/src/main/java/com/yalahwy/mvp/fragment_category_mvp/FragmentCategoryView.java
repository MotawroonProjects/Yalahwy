package com.yalahwy.mvp.fragment_category_mvp;

import com.yalahwy.models.CategoryModel;
import com.yalahwy.models.SingleCategoryModel;

import java.util.List;

public interface FragmentCategoryView {
    void onSuccess(List<SingleCategoryModel> data);
    void onSubCategorySuccess(List<CategoryModel> data);
    void onFailed(String msg);
    void onProgressCategoryShow();
    void onProgressCategoryHide();
    void onProgressSubCategoryShow();
    void onProgressSubCategoryHide();
}
