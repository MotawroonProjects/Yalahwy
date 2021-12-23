package com.yalahwy.mvp.activity_splash_mvp;

import android.content.Context;

import com.yalahwy.models.UserSettingsModel;
import com.yalahwy.preferences.Preferences;

public class SplashPresenter {
    private Context context;
    private SplashView view;
    private Preferences preferences;
    private UserSettingsModel userSettingsModel;

    public SplashPresenter(Context context, SplashView view) {
        this.context = context;
        this.view = view;
        preferences = Preferences.getInstance();
        userSettingsModel = preferences.getUserSettings(context);
    }

    public void delaySplash(){

            if (userSettingsModel!=null&&userSettingsModel.isLanguageSelected()){
                view.onNavigateToLocationActivity();
            }else {
                view.onNavigateToLanguageActivity();

            }
    }
}
