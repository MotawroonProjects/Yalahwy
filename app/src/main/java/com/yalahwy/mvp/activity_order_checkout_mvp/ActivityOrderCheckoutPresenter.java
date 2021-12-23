package com.yalahwy.mvp.activity_order_checkout_mvp;

import android.content.Context;

import com.yalahwy.models.UserModel;
import com.yalahwy.preferences.Preferences;

public class ActivityOrderCheckoutPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private OrderCheckoutActivityView view;
    private Context context;

    public ActivityOrderCheckoutPresenter(OrderCheckoutActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }



}
