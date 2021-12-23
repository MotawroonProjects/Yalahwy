package com.yalahwy.mvp.activity_product_details_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yalahwy.R;
import com.yalahwy.models.AddFavoriteDataModel;
import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.LogoutModel;
import com.yalahwy.models.ProductModel;
import com.yalahwy.models.SingleProductDataModel;
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


public class ActivityProductDetailsPresenter {
    private Context context;
    private ActivityProductDetailsView view;
    private Preferences preference;
    private UserModel userModel;
    private List<CartDataModel.CartModel> cartModelList;
    private CartDataModel cartDataModel;
    private String lang="ar";

    public ActivityProductDetailsPresenter(Context context, ActivityProductDetailsView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        cartDataModel = preference.getCartData(context);
        lang = Paper.book().read("lang","ar");

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


    public void getProductById(String product_id)
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
        view.onProgressShow();
        Api.getService(Tags.base_url)
                .getProductById(user_id, product_id,type)
                .enqueue(new Callback<SingleProductDataModel>() {
                    @Override
                    public void onResponse(Call<SingleProductDataModel> call, Response<SingleProductDataModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressHide();
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
                    public void onFailure(Call<SingleProductDataModel> call, Throwable t) {
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
    public void add_remove_favorite(ProductModel productModel)
    {
        if (userModel == null) {
            if (productModel.getIs_wishlist() != null) {
                Log.e("r","r");
                productModel.setIs_wishlist(null);
            } else {
                Log.e("a","a");
                productModel.setIs_wishlist(new ProductModel.IsWishList());
            }
            view.onUserNotRegister(context.getString(R.string.pls_signin_signup), productModel);
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
                                view.onFavoriteActionSuccess(productModel);
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
                            view.onFavoriteActionSuccess(productModel);

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
                            view.onFavoriteActionSuccess(productModel);
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
    public void add_to_menu(ProductModel productModel,int amount)
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
        int pos = isProductItemSelected(productModel.getId());

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


    public int isProductItemSelected(int product_id){

        int pos = -1;

        cartDataModel = preference.getCartData(context);
        if (cartDataModel!=null&&cartDataModel.getCartModelList()!=null)
        {
            cartModelList = cartDataModel.getCartModelList();
            for (int index =0;index<cartModelList.size();index++){
                CartDataModel.CartModel cartModel = cartModelList.get(index);
                if (String.valueOf(product_id).equals(cartModel.getId())){
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
        int pos = isProductItemSelected(productModel.getId());
        if (pos==-1){
            view.onAmountSelectedFromCart(1);
        }else {
            view.onAmountSelectedFromCart(cartDataModel.getCartModelList().get(pos).getAmount());
        }

    }


}
