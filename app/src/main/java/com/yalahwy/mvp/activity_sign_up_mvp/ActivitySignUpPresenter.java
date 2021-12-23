package com.yalahwy.mvp.activity_sign_up_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.yalahwy.R;
import com.yalahwy.models.UserModel;
import com.yalahwy.remote.Api;
import com.yalahwy.share.Common;
import com.yalahwy.tags.Tags;
import com.yalahwy.models.SignUpModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySignUpPresenter {
    private Context context;
    private ActivitySignUpView view;


    public ActivitySignUpPresenter(Context context, ActivitySignUpView view) {
        this.context = context;
        this.view = view;


    }





    public void sign_up(SignUpModel signUpModel) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .signUp(signUpModel.getPhone_code() + signUpModel.getPhone(), signUpModel.getName(), signUpModel.getEmail(), signUpModel.getPassword())
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().status == 200) {
                                view.onSuccess(response.body());
                            } else if (response.body().status == 409) {
                                view.onFailed(context.getString(R.string.phone_found));
                            } else if (response.body().status == 406) {
                                view.onFailed(context.getString(R.string.email_found));

                            } else {
                                view.onFailed(response.message() + "");
                            }
                        } else {
                            dialog.dismiss();
                            try {
                                Log.e("error_code", response.code() + "___" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            if (response.code() == 500) {
                                view.onFailed("Server Error");
                            } else {
                                view.onFailed(context.getResources().getString(R.string.failed));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getResources().getString(R.string.failed));
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });


    }


}
