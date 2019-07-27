package com.example.warehouseapplication.Callback;

import com.example.warehouseapplication.Model.TakeDelivery;
import com.squareup.okhttp.ResponseBody;

import retrofit.Retrofit;

/**
 * Created by Killy77 on 29/4/2561.
 */

public interface TakeDeliveryCallback {
    public void onResponse(TakeDelivery response, Retrofit retrofit);
    public void onFailure(Throwable t);
    public void onBodyError(ResponseBody responseBody);
    public void onBodyErrorIsNull();
}
