package com.yalahwy.ui.activity_order_checkout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yalahwy.R;
import com.yalahwy.databinding.ActivityOrderCheckoutBinding;
import com.yalahwy.language.Language;
import com.yalahwy.mvp.activity_order_checkout_mvp.ActivityOrderCheckoutPresenter;
import com.yalahwy.mvp.activity_order_checkout_mvp.OrderCheckoutActivityView;

import io.paperdb.Paper;

public class OrderCheckoutActivity extends AppCompatActivity implements OrderCheckoutActivityView {
    private ActivityOrderCheckoutBinding binding;
    private ActivityOrderCheckoutPresenter presenter;
    private String lang;
    private int delivery_type =0,packaging_type=0,payment_type=0;



    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_checkout);
        initView();
    }


    private void initView() {

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        presenter = new ActivityOrderCheckoutPresenter(this, this);

        binding.rbDeliveryFree.setOnClickListener(view -> {
            delivery_type = 0;
        });

        binding.rbDeliveryPaid.setOnClickListener(view -> {
            delivery_type = 1;
        });

        binding.rbPackagingFree.setOnClickListener(view -> {
            packaging_type = 0;
        });

        binding.rbPackagingPaid.setOnClickListener(view -> {
            packaging_type = 1;
        });
        binding.rbPaymentOnReceive.setOnClickListener(view -> {
            payment_type = 0;
        });

        binding.rbOnlinePayment.setOnClickListener(view -> {
            payment_type = 1;
        });
        binding.btnPay.setOnClickListener(view -> {
            Intent intent = getIntent();
            intent.putExtra("delivery",delivery_type);
            intent.putExtra("packaging",packaging_type);
            intent.putExtra("payment",payment_type);
            setResult(RESULT_OK,intent);
            finish();
        });
         binding.back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 presenter.backPress();
             }
         });
    }

    @Override
    public void onBackPressed() {
        presenter.backPress();
    }


    @Override
    public void onFinished() {
        finish();
    }



}