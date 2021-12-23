package com.yalahwy.mvp.activity_order_steps_mvp;

import android.content.Context;

import com.yalahwy.models.UserModel;
import com.yalahwy.preferences.Preferences;

public class ActivityOrderStepsPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private OrderStepsActivityView view;
    private Context context;

    public ActivityOrderStepsPresenter(OrderStepsActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }



}
