package com.yalahwy.models;

import android.content.Context;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.yalahwy.BR;
import com.yalahwy.R;

import java.io.Serializable;

public class SignUpModel extends BaseObservable implements Serializable {
    private String name;
    private String phone_code;
    private String phone;
    private String email;
    private String password;
    public ObservableField<String> error_phone = new ObservableField<>();
    public ObservableField<String> error_password = new ObservableField<>();
    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();


    public SignUpModel() {
        this.phone_code = "+20";
        this.phone = "";
        this.password = "";
        this.name = "";
        this.email = "";
    }

    public boolean isDataValid(Context context) {
        if (!name.isEmpty() &&
                !phone.isEmpty() &&
                !email.isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                !password.isEmpty() &&
                password.length() >= 6
        ) {
            error_name.set(null);
            error_email.set(null);
            error_phone.set(null);
            error_password.set(null);

            return true;
        } else {
            if (name.isEmpty()) {
                error_name.set(context.getString(
                        R.string.field_req));
            } else {
                error_name.set(null);
            }

            if (phone.isEmpty()) {
                error_phone.set(context.getString(R.string.field_req));

            } else {
                error_phone.set(null);

            }

            if (email.isEmpty()) {
                error_email.set(context.getString(R.string.field_req));
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                error_email.set(context.getString(R.string.inv_email));

            }else {
                error_email.set(null);
            }

            if (password.isEmpty()) {
                error_password.set(context.getString(R.string.field_req));
            } else if (password.length() < 6) {
                error_password.set(context.getString(R.string.password_short));
            } else {
                error_password.set(null);
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
        notifyPropertyChanged(BR.name);
    }


    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }
}
