package com.example.barcodeshop.Url;

import java.util.HashMap;

public class Url {

    final static String API_URL = "http://192.168.43.118:8081/api/";
    final static String IMAGE_URL = "http://192.168.43.118:8081/";

    final static HashMap<String, String> API_ENDPOINTS = new HashMap<String, String>() {{
        put("login", "login/login");
        put("register", "register/register");
        put("list", "Product/GetProduct");
        put("search", "SearchProduct/search");
        put("offers", "getOffers/getOffers");
        put("profile", "UserProfile/userProfile");
        put("imageUpload", "addImage/addImage");
        put("update", "UpdateProfile/UpdateProfile");
        put("addList", "register/GetOrderLine");
        put("ProductRating", "Prodr uct/getProductRating");
        put("OrderList","AddList/getOrder");
        put("Wallet","wallet/getWallet");
        put("TopRated","TopRating/TopRatedPreviousOrder");
        put("ProductTopRated","TopRating/TopRatedProducts");
        put("ratingDialogBox","TopRating/userRatingDialog");
        put("rate","TopRating/userRating");
        put("PreviousOrder","PreviousOrder/getPreviousOrder");
    }};

    final static HashMap<String, String> API_IMAGE = new HashMap<String, String>() {{
        put("getImage", "images/");
    }};

    public static String getAbsUrl(String key){
        return API_URL + API_ENDPOINTS.get(key);
    }

    public static String getImageUrl(String key){
        return IMAGE_URL + API_IMAGE.get(key);
    }

}
