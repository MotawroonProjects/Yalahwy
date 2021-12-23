package com.yalahwy.mvp.activity_confirm_code_mvp;


public interface ActivityConfirmCodeView {
    void onSuccessCode();
    void onCounterStarted(String time);
    void onCounterFinished();
    void onCodeFailed(String msg);

}
