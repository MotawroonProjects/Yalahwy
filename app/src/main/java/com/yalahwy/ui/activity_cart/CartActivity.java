package com.yalahwy.ui.activity_cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.yalahwy.R;
import com.yalahwy.adapters.CartAdapter;
import com.yalahwy.databinding.ActivityCartBinding;
import com.yalahwy.language.Language;
import com.yalahwy.models.AddressModel;
import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.CouponDataModel;
import com.yalahwy.models.SingleOrderModel;
import com.yalahwy.mvp.activity_cart_mvp.ActivityCartPresenter;
import com.yalahwy.mvp.activity_cart_mvp.CartActivityView;
import com.yalahwy.share.Common;
import com.yalahwy.ui.activity_order_checkout.OrderCheckoutActivity;
import com.yalahwy.ui.activity_order_details.OrderDetailsActivity;
import com.yalahwy.ui.activity_select_address.SelectAddressActivity;
import com.yalahwy.ui.activity_web_view.WebViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity implements CartActivityView {
    private ActivityCartBinding binding;
    private ActivityCartPresenter presenter;
    private String lang;
    private CartAdapter adapter;
    private List<CartDataModel.CartModel> cartModelList;

    int payment_type=0;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        initView();
    }


    private void initView() {
        cartModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        presenter = new ActivityCartPresenter(this, this);

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(cartModelList, this);
        binding.recView.setAdapter(adapter);
        presenter.getCartData();
        binding.llBack.setOnClickListener(view -> {
            presenter.backPress();
        });

        binding.flCouponCheck.setOnClickListener(view -> {
            String code = binding.edtCoupon.getText().toString();
            if (!code.isEmpty()){
                Common.CloseKeyBoard(this,binding.edtCoupon);
                presenter.checkCoupon(code);
            }
        });
        binding.btnNext.setOnClickListener(view -> presenter.checkOut());
    }

    @Override
    public void onBackPressed() {
        presenter.backPress();
    }


    @Override
    public void onFinished() {
        finish();
    }

    @Override
    public void onCheckOut() {

        Intent intent = new Intent(this, SelectAddressActivity.class);
        intent.putExtra("cart",true);
        startActivityForResult(intent,100);
    }

    @Override
    public void onDataSuccess(CartDataModel cartDataModel) {
        cartModelList.clear();
        cartModelList.addAll(cartDataModel.getCartModelList());
        if (cartModelList.size()>0){
            adapter.notifyDataSetChanged();
            binding.llCoupon.setVisibility(View.VISIBLE);
            binding.llCost.setVisibility(View.VISIBLE);
            binding.llEmptyCart.setVisibility(View.GONE);
        }else {
            binding.llCoupon.setVisibility(View.GONE);
            binding.llCost.setVisibility(View.GONE);
            binding.llEmptyCart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCartItemRemoved(int pos) {
        cartModelList.remove(pos);
        adapter.notifyItemRemoved(pos);
        if (cartModelList.size()>0){
            adapter.notifyDataSetChanged();
            binding.llCoupon.setVisibility(View.VISIBLE);
            binding.llCost.setVisibility(View.VISIBLE);
            binding.llEmptyCart.setVisibility(View.GONE);
        }else {
            binding.llCoupon.setVisibility(View.GONE);
            binding.llCost.setVisibility(View.GONE);
            binding.llEmptyCart.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onCostUpdate(double totalItemCost, double discount, double totalCost) {
        binding.tvTotalItemCost.setText(String.format(Locale.ENGLISH,"%.2f %s",totalItemCost,getString(R.string.sar)));
        binding.tvDiscount.setText(String.format(Locale.ENGLISH,"%.2f %s",discount,getString(R.string.sar)));
        binding.tvTotal.setText(String.format(Locale.ENGLISH,"%.2f %s",totalCost,getString(R.string.sar)));

    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCouponSuccess(CouponDataModel.CouponModel couponModel) {
        if (couponModel.getType()==0){
            binding.tvCouponDiscount.setText(String.format(Locale.ENGLISH,"%s %s %s",getString(R.string.you_got_discount),couponModel.getPrice(),"%"));

        }else {
            binding.tvCouponDiscount.setText(String.format(Locale.ENGLISH,"%s %s %s",getString(R.string.you_got_discount),couponModel.getPrice(),getString(R.string.sar)));

        }
    }

    @Override
    public void onCouponFailed() {
        Toast.makeText(this, R.string.inv_coupon, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeliveryPriceSuccess(double cost) {
        if (cost>0){
            binding.viewDelivery.setVisibility(View.VISIBLE);
            binding.llDelivery.setVisibility(View.VISIBLE);
            binding.tvDelivery.setText(String.format(Locale.ENGLISH,"%.2f %s",cost,getString(R.string.sar)));

        }else {
            binding.viewDelivery.setVisibility(View.GONE);
            binding.viewDelivery.setVisibility(View.GONE);
            binding.llDelivery.setVisibility(View.GONE);

        }
    }

    @Override
    public void onPackagingPriceSuccess(double cost) {
        if (cost>0){

            binding.viewPackaging.setVisibility(View.VISIBLE);
            binding.llPackaging.setVisibility(View.VISIBLE);
            binding.tvPackaging.setText(String.format(Locale.ENGLISH,"%.2f %s",cost,getString(R.string.sar)));

        }else {
            binding.viewPackaging.setVisibility(View.GONE);
            binding.viewPackaging.setVisibility(View.GONE);
            binding.llPackaging.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPaymentSuccess(int method) {
        if (method==1){

            payment_type=1;
        }else {
            payment_type=0;
        }

    }

    @Override
    public void onOrderSendSuccessfully(SingleOrderModel singleOrderModel) {

        Log.e("ccccccc",payment_type+"___");
        if (payment_type==1){
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url","http://athomegy.com/api/paymob-check/"+singleOrderModel.getOrder().getId());
            startActivity(intent);
            finish();
            Log.e("url","http://athomegy.com/api/paymob-check/"+singleOrderModel.getOrder().getId());
        }else {
            Toast.makeText(this, getString(R.string.suc), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, OrderDetailsActivity.class);
            intent.putExtra("data",singleOrderModel.getOrder());
            startActivity(intent);
            finish();
        }

    }


    public void removeCartItem(CartDataModel.CartModel model) {
        presenter.removeCartItem(model);
    }

    public void increase_decrease_item_count(CartDataModel.CartModel cartModel,int amount){
        presenter.update_cart(cartModel,amount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK&&data!=null){
            AddressModel addressModel = (AddressModel) data.getSerializableExtra("data");
            presenter.updateAddress(addressModel);
            Intent intent = new Intent(this, OrderCheckoutActivity.class);
            startActivityForResult(intent,200);
        }else if (requestCode==200&&resultCode==RESULT_OK&&data!=null){
            Log.e("eeeee", "666666666___");

            int delivery_type = data.getIntExtra("delivery",0);
            int packaging_type = data.getIntExtra("packaging",0);
            int payment_type = data.getIntExtra("payment",0);
            presenter.updateDelivery(delivery_type,packaging_type,payment_type);
            presenter.sendOrder();
        }
    }
}