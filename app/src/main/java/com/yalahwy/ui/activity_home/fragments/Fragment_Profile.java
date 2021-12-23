package com.yalahwy.ui.activity_home.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.yalahwy.R;
import com.yalahwy.databinding.FragmentProfileBinding;
import com.yalahwy.interfaces.Listeners;
import com.yalahwy.models.SocialSettingsModel;
import com.yalahwy.models.UserModel;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.remote.Api;
import com.yalahwy.share.Common;
import com.yalahwy.tags.Tags;
import com.yalahwy.ui.activity_address.AddressActivity;
import com.yalahwy.ui.activity_contact_us.ContactUsActivity;
import com.yalahwy.ui.activity_favorite.FavoriteActivity;
import com.yalahwy.ui.activity_home.HomeActivity;
import com.yalahwy.ui.activity_language.LanguageActivity;
import com.yalahwy.ui.activity_menu.MenuActivity;
import com.yalahwy.ui.activity_select_address.SelectAddressActivity;
import com.yalahwy.ui.activity_web_view.WebViewActivity;

import java.io.IOException;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Profile extends Fragment implements Listeners.ProfileActions {
    private FragmentProfileBinding binding;
    private String lang;
    private HomeActivity activity;
    private Preferences preferences;
    private UserModel userModel;
    private SocialSettingsModel socialSettingModel;
    public static Fragment_Profile newInstance(){
        return new Fragment_Profile();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        if (userModel!=null){
            binding.setModel(userModel);
            binding.llLogout.setVisibility(View.VISIBLE);
            binding.cardBaarcode.setVisibility(View.VISIBLE);
            binding.lluser.setVisibility(View.VISIBLE);
            binding.fluser.setVisibility(View.GONE);

        }else{
            binding.llLogout.setVisibility(View.GONE);
            binding.cardBaarcode.setVisibility(View.GONE);
            binding.fluser.setVisibility(View.VISIBLE);
            binding.lluser.setVisibility(View.GONE);

        }
        binding.fluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onNavigateToLoginActivity();
            }
        });
        binding.setActions(this);
        getSocialSetting();
    }

    @Override
    public void onMyWallet() {

    }

    @Override
    public void onFavorite() {
        userModel = preferences.getUserData(activity);
        if (userModel!=null){
            Intent intent = new Intent(activity, FavoriteActivity.class);
            startActivityForResult(intent,100);
        }else {
            Common.CreateDialogAlert(activity,getString(R.string.pls_signin_signup));
        }

    }

    @Override
    public void onAddress() {
        userModel = preferences.getUserData(activity);
        if (userModel!=null){
            Intent intent = new Intent(activity, SelectAddressActivity.class);
            startActivity(intent);
        }else {
            Common.CreateDialogAlert(activity,getString(R.string.pls_signin_signup));
        }
    }

    @Override
    public void onChangeLanguage() {
        Intent intent = new Intent(activity, LanguageActivity.class);
        startActivityForResult(intent,200);
    }

    @Override
    public void onTerms() {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url","https://athomegy.com/terms");
        startActivity(intent);
    }

    @Override
    public void onContactUs() {
        Intent intent = new Intent(activity, ContactUsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMenu() {
        userModel = preferences.getUserData(activity);
        if (userModel!=null){
            Intent intent = new Intent(activity, MenuActivity.class);
            startActivity(intent);
        }else {
            Common.CreateDialogAlert(activity,getString(R.string.pls_signin_signup));
        }
    }

    @Override
    public void onAddresses() {
      /*  String url = Tags.base_url+"branches";
        String lang_url = "";
        if (lang.equals("ar")){
            lang_url = Tags.base_url+"language/2";
        }else {
            lang_url = Tags.base_url+"language/1";

        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);*/

        Intent intent = new Intent(activity, AddressActivity.class);
        startActivity(intent);

    }

    @Override
    public void onFacebook() {
        if (socialSettingModel != null && socialSettingModel.getData() != null && socialSettingModel.getData().getFacebook() != null && !socialSettingModel.getData().getFacebook().equals("#")) {
            open("http:"+ socialSettingModel.getData().getFacebook());
        } else {
            Toast.makeText(activity, R.string.not_avail_now, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onYoutube() {
        if (socialSettingModel != null && socialSettingModel.getData() != null && socialSettingModel.getData().getTwitter() != null && !socialSettingModel.getData().getTwitter().equals("#")) {
            open("http:"+socialSettingModel.getData().getTwitter());
        } else {
            Toast.makeText(activity, R.string.not_avail_now, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTiktok() {
        if (socialSettingModel != null && socialSettingModel.getData() != null && socialSettingModel.getData().getLinkedin() != null && !socialSettingModel.getData().getLinkedin().equals("#")) {
            open("http:"+socialSettingModel.getData().getLinkedin());
        } else {
            Toast.makeText(activity, R.string.not_avail_now, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onInstagram() {
        if (socialSettingModel != null && socialSettingModel.getData() != null && socialSettingModel.getData().getGplus() != null && !socialSettingModel.getData().getGplus().equals("#")) {
            open("http:"+socialSettingModel.getData().getGplus());
        } else {
            Toast.makeText(activity, R.string.not_avail_now, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLogout() {
        activity.logout();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode== Activity.RESULT_OK){
            activity.refreshFragmentHomeData();
        }else if (requestCode==200&&resultCode== Activity.RESULT_OK&&data!=null){
            String lang = data.getStringExtra("lang");
            activity.refreshActivity(lang);
        }

    }

    private void getSocialSetting() {
        ProgressDialog dialog = Common.createProgressDialog(activity, getString(R.string.wait));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url).getSocialSetting()
                .enqueue(new Callback<SocialSettingsModel>() {
                    @Override
                    public void onResponse(Call<SocialSettingsModel> call, Response<SocialSettingsModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                socialSettingModel = response.body();


                            }
                        } else {

                            dialog.dismiss();

                            try {
                                Log.e("error_code", response.code() + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<SocialSettingsModel> call, Throwable t) {
                        try {
                            dialog.dismiss();

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else if (t.getMessage().toLowerCase().contains("socket") || t.getMessage().toLowerCase().contains("canceled")) {
                                } else {
                                    Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }


                        } catch (Exception e) {

                        }
                    }
                });
    }
    private void open(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        startActivity(intent);
    }
}
