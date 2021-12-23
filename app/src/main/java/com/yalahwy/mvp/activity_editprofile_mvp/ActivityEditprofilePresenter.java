package com.yalahwy.mvp.activity_editprofile_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;


import com.yalahwy.R;
import com.yalahwy.models.EditProfileModel;
import com.yalahwy.models.UserModel;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.remote.Api;
import com.yalahwy.share.Common;
import com.yalahwy.tags.Tags;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditprofilePresenter {
    private UserModel userModel;
    private Preferences preferences;
    private EditprofileActivityView view;
    private Context context;

    public ActivityEditprofilePresenter(EditprofileActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }

    public void checkData(EditProfileModel editProfileModel, UserModel userModel) {
        if (editProfileModel.isDataValid(context)) {
            if (editProfileModel.getImage() == null) {
                editprofile(editProfileModel, userModel);
            } else {
                updateProfileWithImage(editProfileModel, userModel);
            }


        }
    }


    private void editprofile(EditProfileModel editProfileModel, UserModel userModel) {
        view.onLoad();
        Api.getService(Tags.base_url)
                .Editprofile(userModel.getData().getToken(), userModel.getData().getId() + "", editProfileModel.getName(), editProfileModel.getEmail(), editProfileModel.getPassword())
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        view.onFinishload();
                        if (response.isSuccessful() && response.body() != null) {
                            //  Log.e("eeeeee", response.body().getUser().getName());
                            // view.onUserFound(response.body());
                            if (response.body().getStatus() == 200) {

                                view.onSuccess(response.body());
                            } else {
                                view.onFailed(response.message() + "");
                            }
                        } else {
                            try {
                                Log.e("mmmmmmmmmm", response.code() + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            if (response.code() == 500) {
                                // view.onServer();
                            } else {
                                view.onFailed(response.message());
                                //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            view.onFinishload();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //   view.onnotconnect(t.getMessage().toLowerCase());
                                    //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    view.onFailed(t.getMessage());
                                    // Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });

    }

    private void updateProfileWithImage(EditProfileModel editProfileModel, UserModel userModel) {

        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        RequestBody user_id = Common.getRequestBodyText(String.valueOf(userModel.getData().getId()));
        RequestBody name_part = Common.getRequestBodyText(editProfileModel.getName());
        RequestBody password_part = Common.getRequestBodyText(editProfileModel.getPassword());
        RequestBody email_part = Common.getRequestBodyText(editProfileModel.getEmail());


        MultipartBody.Part image = Common.getMultiPart(context, Uri.parse(editProfileModel.getImage()), "photo");


        Api.getService(Tags.base_url)
                .updateProfileWithImage(userModel.getData().getToken(), user_id, name_part, email_part, password_part, image)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {

                                view.onSuccess(response.body());
                            } else {
                                view.onFailed(response.message() + "");
                            }

                        } else {
                            try {
                                Log.e("mmmmmmmmmm", response.code() + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            if (response.code() == 500) {
                                // view.onServer();
                            } else {
                                view.onFailed(response.message());
                                //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //   view.onnotconnect(t.getMessage().toLowerCase());
                                    //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    view.onFailed(t.getMessage());
                                    // Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });

    }


}
