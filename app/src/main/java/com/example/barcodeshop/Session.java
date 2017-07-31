package com.example.barcodeshop;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ' on 5/12/2017.
 */
public class Session {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public static final String KEY_NAME = "name";
    public static final String KEY_CUSTOMER_ID = "customerid";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_IMAGE = "image";

    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp",Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    //yaha login ko true set kare ga or jo username ho ga wo save kare ga

    public void setLoggedin(boolean loggedin, String customerid, String Name, String Username, String Phone, String Image){
        editor.putBoolean("loggedInmode",loggedin);
        editor.putString(KEY_NAME,Name);
        editor.putString(KEY_CUSTOMER_ID,customerid);
        editor.putString(KEY_USERNAME,Username);
        editor.putString(KEY_PHONE,Phone);
        editor.putString(KEY_IMAGE, Image);

        editor.commit();
    }

    public  boolean loggedin(){
        return prefs.getBoolean("loggedInmode",false);
    }

    //is function mai string ke under username save kya hai,
    // is ko home activity mai call kare gae to username waha dikh jae ga

    public void logout(){
        editor.clear();
        editor.commit();
    }

    public String getUserID(){
        String id;
        id = prefs.getString(KEY_CUSTOMER_ID,null);
        return id;
    }

    public String getUserName(){
        String name;
        name = prefs.getString(KEY_NAME,null);

        return name;
    }

    public String getUserUsername(){
        String username;
        username = prefs.getString(KEY_USERNAME,null);

        return username;
    }

    public String getUserPhone(){
        String phone;
        phone = prefs.getString(KEY_PHONE,null);

        return phone;
    }

    public String getUserImage(){
        String image;
        image = prefs.getString(KEY_IMAGE,null);

        return image;
    }

}
