package com.yalahwy.ui.activity_address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.yalahwy.R;
import com.yalahwy.databinding.ActivityAddressBinding;
import com.yalahwy.language.Language;
import com.yalahwy.ui.FragmentMapTouchListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import io.paperdb.Paper;

public class AddressActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityAddressBinding binding;
    private GoogleMap googleMap1, googleMap2, googleMap3, googleMap4,googleMap5;
    private FragmentMapTouchListener fragment1, fragment2, fragment3, fragment4,fragment5;
    private String lang;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        initView();
    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);

        binding.llBack.setOnClickListener(v -> finish());
        setUpFragments();

        binding.tv1.setOnClickListener(v -> openMap(30.11873120098478, 31.359209169317133));
        binding.tv2.setOnClickListener(v -> openMap(30.59768647659405, 31.48549762956018));
        binding.tv3.setOnClickListener(v -> openMap(31.253041842560517, 29.972409015902773));
        binding.tv4.setOnClickListener(v -> openMap(30.47272442689041, 31.184208846585655));
        binding.tv5.setOnClickListener(v -> openMap(28.0988687, 30.7550744));

    }

    private void openMap(double lat, double lng) {
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:"+lat+","+lng));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private void setUpFragments() {
        fragment1 = (FragmentMapTouchListener) getSupportFragmentManager().findFragmentById(R.id.map1);
        fragment1.getMapAsync(this);

        fragment2 = (FragmentMapTouchListener) getSupportFragmentManager().findFragmentById(R.id.map2);
        fragment2.getMapAsync(this);

        fragment3 = (FragmentMapTouchListener) getSupportFragmentManager().findFragmentById(R.id.map3);
        fragment3.getMapAsync(this);

        fragment4 = (FragmentMapTouchListener) getSupportFragmentManager().findFragmentById(R.id.map4);
        fragment4.getMapAsync(this);

        fragment5 = (FragmentMapTouchListener) getSupportFragmentManager().findFragmentById(R.id.map5);
        fragment5.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap1 == null && googleMap != null) {
            googleMap1 = googleMap;
            fragment1.setListener(() -> binding.scrollView.requestDisallowInterceptTouchEvent(true));
            addMarker1(new LatLng(30.11873120098478, 31.359209169317133));

        } else if (googleMap2 == null && googleMap != null) {
            googleMap2 = googleMap;
            fragment2.setListener(() -> binding.scrollView.requestDisallowInterceptTouchEvent(true));
            addMarker2(new LatLng(30.59768647659405, 31.48549762956018));

        } else if (googleMap3 == null && googleMap != null) {
            googleMap3 = googleMap;
            fragment3.setListener(() -> binding.scrollView.requestDisallowInterceptTouchEvent(true));
            addMarker3(new LatLng(31.253041842560517, 29.972409015902773));

        } else if (googleMap4 == null && googleMap != null) {
            googleMap4 = googleMap;
            fragment4.setListener(() -> binding.scrollView.requestDisallowInterceptTouchEvent(true));
            addMarker4(new LatLng(30.47272442689041, 31.184208846585655));

        } else if (googleMap5 == null && googleMap != null) {
            googleMap5 = googleMap;
            fragment5.setListener(() -> binding.scrollView.requestDisallowInterceptTouchEvent(true));
            addMarker5(new LatLng(28.0988687, 30.7550744));

        }


    }

    private void addMarker1(LatLng latLng) {
        googleMap1.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.6f));
    }

    private void addMarker2(LatLng latLng) {
        googleMap2.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap2.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.6f));
    }

    private void addMarker3(LatLng latLng) {
        googleMap3.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap3.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.6f));
    }

    private void addMarker4(LatLng latLng) {
        googleMap4.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap4.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.6f));
    }
    private void addMarker5(LatLng latLng) {
        googleMap5.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap5.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.6f));
    }
}