package com.yalahwy.mvp.activity_cart_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yalahwy.R;
import com.yalahwy.models.AddressModel;
import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.CouponDataModel;
import com.yalahwy.models.SendCartModel;
import com.yalahwy.models.SingleOrderModel;
import com.yalahwy.models.UserModel;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.remote.Api;
import com.yalahwy.share.Common;
import com.yalahwy.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCartPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private CartActivityView view;
    private Context context;
    private CartDataModel cartDataModel;

    public ActivityCartPresenter(CartActivityView view, Context context) {
        this.view = view;
        this.context = context;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);
        cartDataModel = preferences.getCartData(context);
        if (cartDataModel==null){
            cartDataModel = new CartDataModel();
            cartDataModel.setCartModelList(new ArrayList<>());
        }


    }


    public void update_cart(CartDataModel.CartModel cartModel, int amount) {
        int pos = getCartItemPos(cartModel);
        if (pos != -1) {
            cartModel.setAmount(amount);
            List<CartDataModel.CartModel> cartModelList = cartDataModel.getCartModelList();
            cartModelList.set(pos, cartModel);
            cartDataModel.setCartModelList(cartModelList);
            calculateTotalCost();
        }

    }

    public void getCartData() {
        view.onDataSuccess(cartDataModel);
        calculateTotalCost();
    }

    public void removeCartItem(CartDataModel.CartModel cartModel) {
        int pos = getCartItemPos(cartModel);
        if (pos != -1) {
            List<CartDataModel.CartModel> cartModelList = cartDataModel.getCartModelList();
            cartModelList.remove(pos);
            cartDataModel.setCartModelList(cartModelList);
            preferences.createUpdateCartData(context, cartDataModel);
            calculateTotalCost();
            view.onCartItemRemoved(pos);

        }
    }

    private int getCartItemPos(CartDataModel.CartModel cartModel) {

        int pos = -1;
        for (int index = 0; index < cartDataModel.getCartModelList().size(); index++) {
            CartDataModel.CartModel model = cartDataModel.getCartModelList().get(index);


            if (model.getId().equals(cartModel.getId())) {
                pos = index;
                return pos;
            }
        }

        return pos;
    }

    private void calculateTotalCost() {
        double total = 0.0;
        for (CartDataModel.CartModel cartModel : cartDataModel.getCartModelList()) {
            total += cartModel.getCost() * cartModel.getAmount();
        }


        cartDataModel.setTotal(total);
        double totalAfterDiscount=0.0;
        if (cartDataModel.getType()==0){
             totalAfterDiscount = (total - (cartDataModel.getCoupon_discount()*total/100)) + cartDataModel.getDelivery_cost() + cartDataModel.getPackaging_cost();
            cartDataModel.setCoupon_discount(cartDataModel.getCoupon_discount()*total/100);

        }else {
            totalAfterDiscount = (total - cartDataModel.getCoupon_discount()) + cartDataModel.getDelivery_cost() + cartDataModel.getPackaging_cost();

        }
        preferences.createUpdateCartData(context, cartDataModel);
        view.onCostUpdate(total, cartDataModel.getCoupon_discount(), totalAfterDiscount);

    }

    public void checkCoupon(String code) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .checkCouponData(code)
                .enqueue(new Callback<CouponDataModel>() {
                    @Override
                    public void onResponse(Call<CouponDataModel> call, Response<CouponDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                if (response.body().getData() != null) {
                                    cartDataModel.setCoupon_code(response.body().getData().getCode());
                                    cartDataModel.setCoupon_id(response.body().getData().getId());
                                    cartDataModel.setType(response.body().getData().getType());
                                    cartDataModel.setCoupon_discount(Double.parseDouble(response.body().getData().getPrice()));
                                    calculateTotalCost();
                                    view.onCouponSuccess(response.body().getData());
                                } else {
                                    view.onCouponFailed();

                                }

                            }


                        }else if (response.body() != null && response.body().getStatus() != 200){
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }else {

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
                    public void onFailure(Call<CouponDataModel> call, Throwable t) {
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

    public void backPress() {

        view.onFinished();


    }

    public void checkOut() {

        if (userModel == null) {
            Common.CreateDialogAlert(context, context.getString(R.string.pls_signin_signup));
        } else {
            view.onCheckOut();
        }


    }

    public void updateAddress(AddressModel addressModel) {
        if (cartDataModel != null) {
            cartDataModel.setAddress(addressModel.getAddress());
            cartDataModel.setPhone(addressModel.getPhone());
        }
    }

    public void updateDelivery(int delivery_type, int packaging_type,int payment_type) {
        if (cartDataModel != null) {
            if (delivery_type == 1) {
                cartDataModel.setDelivery_cost(50);
                view.onDeliveryPriceSuccess(50);
            } else {
                view.onDeliveryPriceSuccess(0);
            }

            if (packaging_type == 1) {
                cartDataModel.setPackaging_cost(15);
                view.onPackagingPriceSuccess(15);
            } else {
                view.onPackagingPriceSuccess(0);

            }
            if (payment_type == 1) {

                view.onPaymentSuccess(1);
            } else {
                view.onPaymentSuccess(0);

            }
        }

        calculateTotalCost();
    }

    public void sendOrder() {
        if (userModel == null && cartDataModel == null) {
            return;
        }

        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        double totalAfterDiscount = (cartDataModel.getTotal() - cartDataModel.getCoupon_discount()) + cartDataModel.getDelivery_cost() + cartDataModel.getPackaging_cost();
        List<SendCartModel.Cart> cartList = getCartList();

        SendCartModel sendCartModel = new SendCartModel(String.valueOf(userModel.getData().getId()), String.valueOf(totalAfterDiscount), cartDataModel.getAddress(), cartDataModel.getAddress(), String.valueOf(cartDataModel.getDelivery_cost()), String.valueOf(cartDataModel.getPackaging_cost()), cartDataModel.getPhone(), cartDataModel.getCoupon_code(),cartDataModel.getCoupon_id(), String.valueOf(cartDataModel.getCoupon_discount()), String.valueOf(cartDataModel.getTotal()), cartList);


        Api.getService(Tags.base_url)
                .sendOrder(userModel.getData().getToken(), sendCartModel)
                .enqueue(new Callback<SingleOrderModel>() {
                    @Override
                    public void onResponse(Call<SingleOrderModel> call, Response<SingleOrderModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if (response.body() != null && response.body().getStatus() == 200) {

                                if (response.body().getOrder() != null) {
                                    Log.e("ddddddd", response+"____");
                                    Log.e("ccccccc", response.code()+"____");

                                    view.onOrderSendSuccessfully(response.body());
                                    preferences.clearCart(context);
                                    cartDataModel = null;

                                } else {

                                    view.onFailed(context.getString(R.string.failed));

                                }

                            }else if (response.body() != null && response.body().getStatus() == 400){
                                Toast.makeText(context, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();

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
                    public void onFailure(Call<SingleOrderModel> call, Throwable t) {
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

    private List<SendCartModel.Cart> getCartList() {
        List<SendCartModel.Cart> cartList = new ArrayList<>();
        for (CartDataModel.CartModel model : cartDataModel.getCartModelList()) {

            for (int index=0;index<model.getAmount();index++){
                String product_id = model.getId();
                cartList.add(new SendCartModel.Cart(product_id));
            }

        }

        return cartList;
    }


}
