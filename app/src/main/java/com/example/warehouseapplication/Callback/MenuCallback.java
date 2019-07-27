package com.example.warehouseapplication.Callback;

import com.example.warehouseapplication.Model.Product;
import com.squareup.okhttp.ResponseBody;

import retrofit.Retrofit;

/**
 * Created by Killy77 on 15/4/2561.
 */

public interface MenuCallback {
    public void onResponse(Product product, Retrofit retrofit);
    public void onFailure(Throwable t);
    public void onBodyError(ResponseBody responseBody);
    public void onBodyErrorIsNull();
}
