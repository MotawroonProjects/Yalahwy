package com.yalahwy.models;

import android.content.Context;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.yalahwy.BR;
import com.yalahwy.R;


public class EditProfileModel extends BaseObservable {

    private String name;

    private String email;
    private String password;
    private String image;

    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();


    public EditProfileModel() {

        this.password = "";
        this.name = "";
        this.email = "";
    }

    public boolean isDataValid(Context context) {
        if (!name.isEmpty() &&
                !email.isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
        ) {
            error_name.set(null);
            error_email.set(null);

            return true;
        } else {
            if (name.isEmpty()) {
                error_name.set(context.getString(
                        R.string.field_req));
            } else {
                error_name.set(null);
            }



            if (email.isEmpty()) {
                error_email.set(context.getString(R.string.field_req));
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                error_email.set(context.getString(R.string.inv_email));

            }else {
                error_email.set(null);
            }



            return false;
        }
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.yalahwy.BR.name);
    }


    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(com.yalahwy.BR.password);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

