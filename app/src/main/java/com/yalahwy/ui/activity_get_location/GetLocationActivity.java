package com.yalahwy.ui.activity_get_location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.yalahwy.R;
import com.yalahwy.databinding.ActivityGetLocationBinding;
import com.yalahwy.language.Language;
import com.yalahwy.models.UserModel;
import com.yalahwy.mvp.activity_location_presenter.ActivityLocationPresenter;
import com.yalahwy.mvp.activity_location_presenter.ActivityLocationView;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.ui.activity_home.HomeActivity;
import com.yalahwy.ui.activity_login.LoginActivity;
import com.google.android.gms.maps.model.LatLng;

import io.paperdb.Paper;

public class GetLocationActivity extends AppCompatActivity implements ActivityLocationView {
    private ActivityGetLocationBinding binding;
    private ActivityLocationPresenter presenter;
    private UserModel userModel;
    private Preferences preference;
    private String lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_get_location);
        initView();
    }

    private void initView() {
        preference = Preferences.getInstance();
        userModel = preference.getUserData(this);
        presenter = new ActivityLocationPresenter(this,this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == ActivityLocationPresenter.loc_req) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.initGoogleApiClient();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1255 && resultCode == RESULT_OK) {
            presenter.startLocationUpdate();
        }
    }

    @Override
    public void onLocationChanged(LatLng latLng) {
        Intent intent;
        if (userModel!=null){
            intent = new Intent(this, HomeActivity.class);
        }else {
            intent = new Intent(this, LoginActivity.class);
        }
        intent.putExtra("lat",latLng.latitude);
        intent.putExtra("lng",latLng.longitude);
        startActivity(intent);
        finish();
    }
}