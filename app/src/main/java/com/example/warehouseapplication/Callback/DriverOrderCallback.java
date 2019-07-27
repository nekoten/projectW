package com.example.warehouseapplication.Callback;

import com.example.warehouseapplication.Model.DeliveryOrderModel;
import com.squareup.okhttp.ResponseBody;

import retrofit.Retrofit;

/**
 * Created by Killy77 on 26/4/2561.
 */

public interface DriverOrderCallback {
    public void onResponse(DeliveryOrderModel delivery, Retrofit retrofit);
    public void onFailure(Throwable t);
    public void onBodyError(ResponseBody responseBody);
    public void onBodyErrorIsNull();
}
