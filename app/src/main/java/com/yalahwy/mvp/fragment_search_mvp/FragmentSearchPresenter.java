package com.yalahwy.mvp.fragment_search_mvp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yalahwy.R;
import com.yalahwy.models.AddFavoriteDataModel;
import com.yalahwy.models.ProductDataModel;
import com.yalahwy.models.ProductModel;
import com.yalahwy.models.UserModel;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.remote.Api;
import com.yalahwy.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentSearchPresenter {
    private Context context;
    private FragmentSearchView view;
    private Preferences preference;
    private UserModel userModel;
    private double lat = 0.0, lng = 0.0;

    public FragmentSearchPresenter(Context context, FragmentSearchView view, double lat, double lng) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        this.lat = lat;
        this.lng = lng;
    }


    public void getSearch(String query) {
        String user_id = "all";
        if (userModel != null) {
            user_id = String.valueOf(userModel.getData().getId());
        }
        //view.onProgressShow();
        Api.getService(Tags.base_url)
                .search(user_id, query)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        //view.onProgressHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onSuccess(response.body().getData());

                            }


                        } else {
                            //view.onProgressHide();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {
                            view.onProgressHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    public void add_remove_favorite(ProductModel productModel, int position) {
        if (userModel == null) {
            if (productModel.getIs_wishlist() != null) {
                productModel.setIs_wishlist(null);
            } else {
                productModel.setIs_wishlist(new ProductModel.IsWishList());
            }
            view.onUserNotRegister(context.getString(R.string.pls_signin_signup), productModel, position);
            return;
        }
        String user_id = String.valueOf(userModel.getData().getId());
        Api.getService(Tags.base_url)
                .add_remove_favorite(userModel.getData().getToken(),user_id, String.valueOf(productModel.getId()))
                .enqueue(new Callback<AddFavoriteDataModel>() {
                    @Override
                    public void onResponse(Call<AddFavoriteDataModel> call, Response<AddFavoriteDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                productModel.setIs_wishlist(response.body().getData());
                                view.onFavoriteActionSuccess(productModel, position);
                            }


                        } else {

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (productModel.getIs_wishlist() != null) {
                                productModel.setIs_wishlist(null);
                            } else {
                                productModel.setIs_wishlist(new ProductModel.IsWishList());
                            }
                            view.onFavoriteActionSuccess(productModel, position);

                            if (response.code() == 500) {
                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddFavoriteDataModel> call, Throwable t) {
                        try {
                            if (productModel.getIs_wishlist() != null) {
                                productModel.setIs_wishlist(null);
                            } else {
                                productModel.setIs_wishlist(new ProductModel.IsWishList());
                            }
                            view.onFavoriteActionSuccess(productModel, position);
                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

}
