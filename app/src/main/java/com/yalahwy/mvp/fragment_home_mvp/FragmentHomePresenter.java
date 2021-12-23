package com.yalahwy.mvp.fragment_home_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yalahwy.R;
import com.yalahwy.models.AddFavoriteDataModel;
import com.yalahwy.models.BottomImageDataModel;
import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.CategorySubCategoryDataModel;
import com.yalahwy.models.LogoutModel;
import com.yalahwy.models.ProductDataModel;
import com.yalahwy.models.ProductModel;
import com.yalahwy.models.SliderDataModel;
import com.yalahwy.models.UserModel;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.remote.Api;
import com.yalahwy.share.Common;
import com.yalahwy.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentHomePresenter {
    private Context context;
    private FragmentHomeView view;
    private Preferences preference;
    private UserModel userModel;
    private double lat = 0.0, lng = 0.0;
    private String lang="ar";
    private List<CartDataModel.CartModel> cartModelList;
    private CartDataModel cartDataModel;
    public FragmentHomePresenter(Context context, FragmentHomeView view, double lat, double lng) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        this.lat = lat;
        this.lng = lng;
        Paper.init(context);
        lang = Paper.book().read("lang","ar");
        cartDataModel = preference.getCartData(context);

        if (cartDataModel==null){

            cartModelList = new ArrayList<>();
            cartDataModel = new CartDataModel();
            cartDataModel.setCartModelList(cartModelList);
        }else {
            if (cartDataModel.getCartModelList()==null){
                cartModelList = new ArrayList<>();

            }else {
                cartModelList = cartDataModel.getCartModelList();

            }

        }
    }


    public void getSlider() {
        view.onProgressSliderShow();
        String type;
        if (lang.equals("ar")){
            type="2";
        }else {
            type="1";
        }
        Api.getService(Tags.base_url).get_slider(type).enqueue(new Callback<SliderDataModel>() {
            @Override
            public void onResponse(Call<SliderDataModel> call, Response<SliderDataModel> response) {
                view.onProgressSliderHide();

                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                        view.onSliderSuccess(response.body().getData());

                    }

                } else {
                    try {
                        view.onFailed(context.getString(R.string.failed));
                        Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<SliderDataModel> call, Throwable t) {
                try {
                    view.onProgressSliderHide();

                    Log.e("Error", t.getMessage());

                } catch (Exception e) {

                }

            }
        });

    }


    public void getFeaturedProducts() {
        String user_id = "all";
        if (userModel != null) {
            user_id = String.valueOf(userModel.getData().getId());
        }
        view.onProgressFeaturedProductsShow();
        Api.getService(Tags.base_url)
                .getFeatureProducts(user_id)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        view.onProgressFeaturedProductsHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onFeaturedProductSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressFeaturedProductsHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {
                            view.onProgressFeaturedProductsHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    public void getMostSellerProducts() {
        String user_id = "all";
        if (userModel != null) {
            user_id = String.valueOf(userModel.getData().getId());
        }
        view.onProgressMostSellerShow();
        Api.getService(Tags.base_url)
                .getMostSellerProducts(user_id)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        view.onProgressMostSellerHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onMostSellerSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressMostSellerHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {
                            view.onProgressMostSellerHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    public void getOtherProducts() {
        String user_id = "all";
        if (userModel != null) {
            user_id = String.valueOf(userModel.getData().getId());
        }
        view.onProgressOtherProductsShow();
        Api.getService(Tags.base_url)
                .getOtherProducts(user_id)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        view.onProgressOtherProductsHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onOtherProductSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressOtherProductsHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {
                            view.onProgressOtherProductsHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }



    public void getOfferProducts()
    {
        String user_id = "all";
        if (userModel != null) {
            user_id = String.valueOf(userModel.getData().getId());
        }
        String type;
        if (lang.equals("ar")){
            type="2";
        }else {
            type="1";
        }
        view.onProgressOfferShow();
        Api.getService(Tags.base_url)
                .getOfferProducts(user_id,type)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        view.onProgressOfferHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onOfferSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressOfferHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {
                            view.onProgressOfferHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.something));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    public void add_remove_favorite(ProductModel productModel, int position, String type)
    {
        if (userModel == null) {
            if (productModel.getIs_wishlist()!=null){
                productModel.setIs_wishlist(null);
            }else {
                productModel.setIs_wishlist(new ProductModel.IsWishList());
            }
            view.onUserNotRegister(context.getString(R.string.pls_signin_signup),productModel,position,type);
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
                                view.onFavoriteActionSuccess(productModel,position,type);
                            }


                        } else {

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (productModel.getIs_wishlist()!=null){
                                productModel.setIs_wishlist(null);
                            }else {
                                productModel.setIs_wishlist(new ProductModel.IsWishList());
                            }
                            view.onFavoriteActionSuccess(productModel,position,type);

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
                            if (productModel.getIs_wishlist()!=null){
                                productModel.setIs_wishlist(null);
                            }else {
                                productModel.setIs_wishlist(new ProductModel.IsWishList());
                            }
                            view.onFavoriteActionSuccess(productModel,position,type);
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


    public void getData(){
        view.onProgressShow();
        Api.getService(Tags.base_url)
                .getCategorySubCategory()
                .enqueue(new Callback<CategorySubCategoryDataModel>() {
                    @Override
                    public void onResponse(Call<CategorySubCategoryDataModel> call, Response<CategorySubCategoryDataModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onDataSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressOtherProductsHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CategorySubCategoryDataModel> call, Throwable t) {
                        try {
                            view.onProgressHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }
    public void getTopImage(){
        view.onProgressTopImageShow();
        Api.getService(Tags.base_url)
                .getTopImages()
                .enqueue(new Callback<SliderDataModel>() {
                    @Override
                    public void onResponse(Call<SliderDataModel> call, Response<SliderDataModel> response) {
                        view.onProgressTopImageHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onDataTopImegSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressTopImageHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SliderDataModel> call, Throwable t) {
                        try {
                            view.onProgressTopImageHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }
    public void getBottomImage(){
        view.onProgressBottomImegShow();
        Api.getService(Tags.base_url)
                .getBottomImages()
                .enqueue(new Callback<BottomImageDataModel>() {
                    @Override
                    public void onResponse(Call<BottomImageDataModel> call, Response<BottomImageDataModel> response) {
                        view.onProgressBottomImegHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onDataBottomImegSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressBottomImegHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BottomImageDataModel> call, Throwable t) {
                        try {
                            view.onProgressBottomImegHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }
    public void getProducts() {
        Log.e("mmmmmmmmm","cccccc");
        if (userModel == null) {
            return;
        }
        String user_id = String.valueOf(userModel.getData().getId());
        view.onProgressfavShow();
        Api.getService(Tags.base_url)
                .getMyFavorite(userModel.getData().getToken(),user_id)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        view.onProgressfavHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onSuccess(response.body().getData());

                                Log.e("xxxxxx","xxxxxxxxx");
                            }


                        } else {
                            view.onProgressfavHide();
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
                            view.onProgressfavHide();


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


    public void remove_favorite(ProductModel productModel)
    {

        if (userModel==null){
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String user_id = String.valueOf(userModel.getData().getId());
        Api.getService(Tags.base_url)
                .add_remove_favorite(userModel.getData().getToken(),user_id, String.valueOf(productModel.getId()))
                .enqueue(new Callback<AddFavoriteDataModel>() {
                    @Override
                    public void onResponse(Call<AddFavoriteDataModel> call, Response<AddFavoriteDataModel> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body() != null) {
                                view.onRemoveFavoriteSuccess();
                            }


                        } else {

                            dialog.dismiss();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

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
                            dialog.dismiss();
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
    public void add_to_menu(ProductModel productModel, int amount)
    {
        if (userModel == null) {
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String user_id = String.valueOf(userModel.getData().getId());
        Api.getService(Tags.base_url)
                .addToMenu(userModel.getData().getToken(),user_id, String.valueOf(productModel.getId()),"no",amount)
                .enqueue(new Callback<LogoutModel>() {
                    @Override
                    public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null&&response.body().getStatus()==200) {
                                view.onAddToMenuSuccess();
                            }else {
                                view.onFailed(context.getString(R.string.failed));

                            }

                        } else {
                            dialog.dismiss();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            if (response.code() == 500) {
                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LogoutModel> call, Throwable t) {
                        try {
                            dialog.dismiss();

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

    public void add_to_cart(ProductModel productModel,int amount)
    {
        int pos = isProductItemSelected(productModel);

        if (pos==-1){

            CartDataModel.CartModel cartModel = new CartDataModel.CartModel(String.valueOf(productModel.getId()),productModel.getPhoto(),productModel.getName(),amount,Double.parseDouble(productModel.getPrice()));
            cartModelList.add(cartModel);
        }else {
            CartDataModel.CartModel cartModel = cartModelList.get(pos);
            cartModel.setAmount(amount);
            cartModelList.set(pos,cartModel);
        }
        if (cartDataModel==null){
            cartDataModel = new CartDataModel();
        }

        cartDataModel.setCartModelList(cartModelList);

        calculateTotalCost();
    }
    private void calculateTotalCost() {
        double total =0.0;
        for (CartDataModel.CartModel cartModel:cartModelList){
            total += cartModel.getAmount()*cartModel.getCost();
        }
        cartDataModel.setTotal(total);
        preference.createUpdateCartData(context,cartDataModel);
        view.onCartUpdated(total,cartModelList.size(),cartModelList);
    }


    public int isProductItemSelected(ProductModel productModel){

        int pos = -1;

        cartDataModel = preference.getCartData(context);
        if (cartDataModel!=null&&cartDataModel.getCartModelList()!=null)
        {
            cartModelList = cartDataModel.getCartModelList();
            for (int index =0;index<cartModelList.size();index++){
                CartDataModel.CartModel cartModel = cartModelList.get(index);
                if (String.valueOf(productModel.getId()).equals(cartModel.getId())){
                    pos = index;
                    return pos;
                }
            }
        }


        return pos;
    }

    public void getCartCount(){
        if (cartDataModel!=null&&cartDataModel.getCartModelList()!=null){
            view.onCartCountUpdated(cartDataModel.getCartModelList().size());

        }else {
            view.onCartCountUpdated(0);

        }
    }

    public void getItemAmount(ProductModel productModel){
        int pos = isProductItemSelected(productModel);
        if (pos==-1){
            view.onAmountSelectedFromCart(1);
        }else {
            view.onAmountSelectedFromCart(cartDataModel.getCartModelList().get(pos).getAmount());
        }

    }
}
