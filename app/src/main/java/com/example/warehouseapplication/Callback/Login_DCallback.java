package com.example.warehouseapplication.Callback;

import com.example.warehouseapplication.Model.DriverModel;
import com.squareup.okhttp.ResponseBody;

import retrofit.Retrofit;

/**
 * Created by Killy77 on 14/4/2561.
 */
public interface Login_DCallback {
    public void onResponse(DriverModel driver, Retrofit retrofit);
    public void onFailure(Throwable t);
    public void onBodyError(ResponseBody responseBody);
    public void onBodyErrorIsNull();
}
