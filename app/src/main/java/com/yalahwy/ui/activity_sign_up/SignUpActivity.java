package com.yalahwy.ui.activity_sign_up;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.yalahwy.models.UserModel;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.ui.activity_confirm_code.ConfirmCodeActivity;
import com.yalahwy.ui.activity_home.HomeActivity;
import com.yalahwy.ui.activity_login.LoginActivity;
import com.yalahwy.R;
import com.yalahwy.databinding.ActivitySignUpBinding;
import com.yalahwy.language.Language;
import com.yalahwy.models.SignUpModel;
import com.yalahwy.mvp.activity_sign_up_mvp.ActivitySignUpPresenter;
import com.yalahwy.mvp.activity_sign_up_mvp.ActivitySignUpView;
import com.yalahwy.share.Common;

import java.util.ArrayList;

import io.paperdb.Paper;

public class SignUpActivity extends AppCompatActivity implements ActivitySignUpView {
    private ActivitySignUpBinding binding;
    private ActivitySignUpPresenter presenter;
    private SignUpModel model;
    private Preferences preference;
    private double lat = 0.0, lng = 0.0;
    private String lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 0.0);
        lng = intent.getDoubleExtra("lng", 0.0);

    }

    private void initView() {
        preference = Preferences.getInstance();
        model = new SignUpModel();
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.setModel(model);

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

        presenter = new ActivitySignUpPresenter(this, this);
        binding.btnSignUp.setOnClickListener(view -> {
            if (model.isDataValid(this)) {
                Common.CloseKeyBoard(this, binding.edtPhone);
                navigateToConfirmCodeActivity();
            }
        });
        binding.tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            startActivity(intent);
            finish();
        });

    }

    private void navigateToConfirmCodeActivity() {
        Intent intent = new Intent(this, ConfirmCodeActivity.class);
        intent.putExtra("phone_code", model.getPhone_code());
        intent.putExtra("phone", model.getPhone());
        startActivityForResult(intent, 100);
    }


    @Override
    public void onSuccess(UserModel userModel) {
        preference.create_update_userdata(this, userModel);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            presenter.sign_up(model);

        }
    }
}