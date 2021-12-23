package com.yalahwy.ui.activity_login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yalahwy.R;
import com.yalahwy.databinding.ActivityLoginBinding;
import com.yalahwy.language.Language;
import com.yalahwy.models.LoginModel;
import com.yalahwy.models.UserModel;
import com.yalahwy.mvp.activity_login_presenter.ActivityLoginPresenter;
import com.yalahwy.mvp.activity_login_presenter.ActivityLoginView;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.ui.activity_home.HomeActivity;
import com.yalahwy.ui.activity_sign_up.SignUpActivity;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity implements ActivityLoginView {
    private ActivityLoginBinding binding;
    private LoginModel model;
    private ActivityLoginPresenter presenter;
    private Preferences preferences;
    private double lat=0.0,lng=0.0;
    private String lang;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat",0.0);
        lng = intent.getDoubleExtra("lng",0.0);
    }

    private void initView() {
        preferences = Preferences.getInstance();
        model = new LoginModel();
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.tvSkip.setPaintFlags(binding.tvSkip.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        binding.setModel(model);
        presenter = new ActivityLoginPresenter(this,this);
        binding.btnLogin.setOnClickListener(view -> {
            presenter.checkData(model);
        });

        binding.tvSkip.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("lat",lat);
            intent.putExtra("lng",lng);
            startActivity(intent);
            finish();
        });
        binding.tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.putExtra("lat",lat);
            intent.putExtra("lng",lng);
            startActivity(intent);
            finish();
        });

        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().startsWith("0")){
                   binding.edtPhone.setText(null);
                }
            }
        });
    }



    @Override
    public void onLoginSuccess(UserModel userModel) {
        preferences.create_update_userdata(this,userModel);
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}