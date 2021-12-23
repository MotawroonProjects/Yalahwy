package com.yalahwy.mvp.activity_sign_up_mvp;


import com.yalahwy.models.UserModel;

public interface ActivitySignUpView {
    void onSuccess(UserModel userModel);
    void onFailed(String msg);

}
