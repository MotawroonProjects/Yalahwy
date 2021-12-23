package com.yalahwy.mvp.activity_login_presenter;

import com.yalahwy.models.UserModel;

public interface ActivityLoginView {
    void onLoginSuccess(UserModel userModel);
    void onFailed(String msg);

}
