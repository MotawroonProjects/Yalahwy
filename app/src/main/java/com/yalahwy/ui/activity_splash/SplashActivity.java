package com.yalahwy.ui.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yalahwy.R;
import com.yalahwy.databinding.ActivitySplashBinding;
import com.yalahwy.language.Language;
import com.yalahwy.models.UserModel;
import com.yalahwy.models.UserSettingsModel;
import com.yalahwy.mvp.activity_splash_mvp.SplashPresenter;
import com.yalahwy.mvp.activity_splash_mvp.SplashView;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.ui.activity_home.HomeActivity;
import com.yalahwy.ui.activity_language.LanguageActivity;
import com.yalahwy.ui.activity_login.LoginActivity;

import java.util.ArrayList;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity implements SplashView {
    private ActivitySplashBinding binding;
    private SplashPresenter presenter;
    private Preferences preferences;
    private String lang;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();
    }

    private void initView() {
        presenter = new SplashPresenter(this, this);
        preferences = Preferences.getInstance();
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        new Handler()
                .postDelayed(() -> {

                            presenter.delaySplash();

                        }
                        , 2000);
    }


    @Override
    public void onNavigateToLanguageActivity() {
        Intent intent = new Intent(this, LanguageActivity.class);
        startActivityForResult(intent, 100);

    }

    @Override
    public void onNavigateToLocationActivity() {
        /*Intent intent = new Intent(this, GetLocationActivity.class);
        startActivity(intent);
        finish();*/

        UserModel userModel = preferences.getUserData(this);

        Intent intent;
        if (userModel != null) {
            intent = new Intent(this, HomeActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        intent.putExtra("lat", 0.0);
        intent.putExtra("lng", 0.0);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            String lang = data.getStringExtra("lang");
            refreshActivity(lang);
        }
    }

    private void refreshActivity(String lang) {
        Paper.init(this);
        Paper.book().write("lang", lang);
        UserSettingsModel userSettings = preferences.getUserSettings(this);
        if (userSettings == null) {
            userSettings = new UserSettingsModel();
        }
        userSettings.setLanguageSelected(true);
        preferences.create_update_user_settings(this, userSettings);
        Language.updateResources(this, lang);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}