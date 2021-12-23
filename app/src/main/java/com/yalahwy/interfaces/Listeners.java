package com.yalahwy.interfaces;


public interface Listeners {

    interface LoginListener {
        void checkDataLogin(String phone_code, String phone);
    }



    interface BackListener
    {
        void back();
    }


    interface ProfileActions
    {
        void onMyWallet();
        void onFavorite();
        void onAddress();
        void onChangeLanguage();
        void onTerms();
        void onContactUs();
        void onMenu();
        void onAddresses();
        void onFacebook();
        void onYoutube();
        void onTiktok();
        void onInstagram();
        void onLogout();

    }



}
