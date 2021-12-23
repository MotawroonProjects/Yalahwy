package com.yalahwy.mvp.activity_home_mvp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.yalahwy.R;
import com.yalahwy.models.CartDataModel;
import com.yalahwy.models.CategorySubCategoryDataModel;
import com.yalahwy.models.LogoutModel;
import com.yalahwy.models.UserModel;
import com.yalahwy.preferences.Preferences;
import com.yalahwy.remote.Api;
import com.yalahwy.share.Common;
import com.yalahwy.tags.Tags;
import com.yalahwy.ui.activity_home.fragments.Fragment_Categories;
import com.yalahwy.ui.activity_home.fragments.Fragment_Search;
import com.yalahwy.ui.activity_home.fragments.Fragment_Home;
import com.yalahwy.ui.activity_home.fragments.Fragment_Orders;
import com.yalahwy.ui.activity_home.fragments.Fragment_Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityHomePresenter {
    private Context context;
    private FragmentManager fragmentManager;
    private HomeActivityView view;
    private Fragment_Home fragment_home;
    private Fragment_Categories fragment_categories;
    private Fragment_Search fragment_search;
    private Fragment_Orders fragment_orders;
    private Fragment_Profile fragment_profile;
    private Preferences preference;
    private UserModel userModel;
    private double lat=0.0,lng=0.0;
    private int selectedPos =0;
    private String lang;

    public ActivityHomePresenter(Context context, HomeActivityView view, FragmentManager fragmentManager, double lat, double lng)
    {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        this.lat = lat;
        this.lng = lng;
        Paper.init(context);
        lang = Paper.book().read("lang","ar");
        getCategory();
        displayFragmentHome();
        updateTokenFireBase();
    }
    @SuppressLint("NonConstantResourceId")
    public void manageFragments(MenuItem item)
    {
        int id = item.getItemId();
        switch (id){
            case R.id.categories :
                displayFragmentCategories(selectedPos);
                break;
            case R.id.search :
                displayFragmentSearch();
                break;

            case R.id.orders :
                if (userModel==null){
                    Common.CreateDialogAlert(context,context.getString(R.string.pls_signin_signup));
                }else {
                    displayFragmentOrders();

                }
                break;
            case R.id.profile :
                displayFragmentProfile();
                break;
            default:
                displayFragmentHome();
                break;
        }
    }
    private void displayFragmentHome()
    {
        if (fragment_home==null){
            fragment_home = Fragment_Home.newInstance(lat,lng);
        }

        if (fragment_categories !=null&& fragment_categories.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_categories).commit();
        }

        if (fragment_orders !=null&& fragment_orders.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }

        if (fragment_search !=null&& fragment_search.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_search).commit();
        }
        if (fragment_profile!=null&&fragment_profile.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }

        if (fragment_home.isAdded()){
            fragmentManager.beginTransaction().show(fragment_home).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragment_home,"fragment_home").commit();
        }
    }
    public void displayFragmentCategories(int selectedPos)
    {
        this.selectedPos = selectedPos;
        if (fragment_categories ==null){
            fragment_categories = Fragment_Categories.newInstance(lat,lng,selectedPos);

        }

        if (fragment_home!=null&&fragment_home.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }

        if (fragment_orders !=null&& fragment_orders.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }

        if (fragment_search !=null&& fragment_search.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_search).commit();
        }
        if (fragment_profile!=null&&fragment_profile.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }

        if (fragment_categories.isAdded()){
            fragmentManager.beginTransaction().show(fragment_categories).commit();
            fragment_categories.setSubCategory(selectedPos);
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_categories,"fragment_appointment").commit();
        }
        view.onCategoryFragmentSelected();
    }
    private void displayFragmentSearch(){
        if (fragment_search ==null){
            fragment_search = Fragment_Search.newInstance();
        }


        if (fragment_home!=null&&fragment_home.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }

        if (fragment_orders !=null&& fragment_orders.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }

        if (fragment_categories !=null&& fragment_categories.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_categories).commit();
        }
        if (fragment_profile!=null&&fragment_profile.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }


        if (fragment_search.isAdded()){
            fragmentManager.beginTransaction().show(fragment_search).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_search,"fragment_consulting").commit();
        }
    }

    private void displayFragmentOrders(){
        if (fragment_orders ==null){
            fragment_orders = Fragment_Orders.newInstance();
        }

        if (fragment_home!=null&&fragment_home.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }

        if (fragment_search !=null&& fragment_search.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_search).commit();
        }

        if (fragment_categories !=null&& fragment_categories.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_categories).commit();
        }
        if (fragment_profile!=null&&fragment_profile.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }

        if (fragment_orders.isAdded()){
            fragmentManager.beginTransaction().show(fragment_orders).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_orders,"fragment_medicine").commit();
        }
    }

    private void displayFragmentProfile(){
        if (fragment_profile==null){
            fragment_profile = Fragment_Profile.newInstance();
        }

        if (fragment_home!=null&&fragment_home.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }

        if (fragment_search !=null&& fragment_search.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_search).commit();
        }

        if (fragment_categories !=null&& fragment_categories.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_categories).commit();
        }
        if (fragment_orders !=null&& fragment_orders.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }
        if (fragment_profile.isAdded()){
            fragmentManager.beginTransaction().show(fragment_profile).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragment_profile,"fragment_profile").commit();
        }
    }

    public void refreshFragmentHomeData(){
        if (fragment_home!=null&&fragment_home.isAdded()){
            fragment_home.refreshData();
        }
    }

    public void backPress(){
        if (fragment_home!=null&&fragment_home.isAdded()&&fragment_home.isVisible()){
            if (userModel==null){
                view.onNavigateToLoginActivity();
            }else {
                view.onFinished();
            }
        }else {
            displayFragmentHome();
            view.onHomeFragmentSelected();
        }
    }

    public void cart(){
        view.onNavigateToCartActivity();
    }

    public void logout()
    {
        if (userModel==null){
            view.onNavigateToLoginActivity();
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .logout(userModel.getData().getToken(),userModel.getData().getId(),userModel.getData().getFirebase_token(),"android")
                .enqueue(new Callback<LogoutModel>() {
                    @Override
                    public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus()==200){
                                    preference.clear(context);
                                    view.onNavigateToLoginActivity();
                                }else {
                                    view.onFailed(context.getString(R.string.failed));
                                }
                            }


                        } else {

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            if (response.code() == 500) {
                                view.onFailed("Server Error");
                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LogoutModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });


    }

    private void updateTokenFireBase()
    {
        if (userModel!=null){
            try {
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            String token = task.getResult().getToken();
                            Api.getService(Tags.base_url)
                                    .updateFirebaseToken(userModel.getData().getToken(),userModel.getData().getId(),token,"android")
                                    .enqueue(new Callback<LogoutModel>() {
                                        @Override
                                        public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                                            if (response.isSuccessful() && response.body() != null&&response.body().getStatus()==200) {
                                                userModel.getData().setFireBaseToken(token);
                                                preference.create_update_userdata(context,userModel);

                                            } else {
                                                try {

                                                    Log.e("errorToken", response.code() + "_" + response.errorBody().string());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<LogoutModel> call, Throwable t) {
                                            try {

                                                if (t.getMessage() != null) {
                                                    Log.e("errorToken2", t.getMessage());
                                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                                        Toast.makeText(context, R.string.something, Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            } catch (Exception e) {
                                            }
                                        }
                                    });
                        }
                    }
                });


            } catch (Exception e) {


            }
        }
    }

    public void getCartItemCount(){
        if (preference.getCartData(context)!=null&&preference.getCartData(context).getCartModelList()!=null){
            view.onCartCountUpdate(preference.getCartData(context).getCartModelList().size());

        }else {
            view.onCartCountUpdate(0);

        }
    }

    public void getCategory() {
        view.onProgressCategoryShow();
        String type;
       /* if (lang.equals("ar")){
            type="2";
        }else {
            type="1";
        }*/
        Api.getService(Tags.base_url)
                .getCategorySubCategory()
                .enqueue(new Callback<CategorySubCategoryDataModel>() {
                    @Override
                    public void onResponse(Call<CategorySubCategoryDataModel> call, Response<CategorySubCategoryDataModel> response) {
                        view.onProgressCategoryHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressCategoryHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CategorySubCategoryDataModel> call, Throwable t) {
                        try {
                            view.onProgressCategoryHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }


    public void updateCartModel(){
        CartDataModel cartDataModel = preference.getCartData(context);
        if (cartDataModel!=null){
            cartDataModel.setCoupon_discount(0.0);
            cartDataModel.setCoupon_code("");
            cartDataModel.setDelivery_cost(0.0);
            cartDataModel.setPackaging_cost(0.0);
            preference.createUpdateCartData(context,cartDataModel);
        }
    }

}
