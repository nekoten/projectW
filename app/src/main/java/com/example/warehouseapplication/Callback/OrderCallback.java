package com.example.warehouseapplication.Callback;

import com.example.warehouseapplication.Model.OrderResponse;
import com.squareup.okhttp.ResponseBody;

import retrofit.Retrofit;

/**
 * Created by Killy77 on 26/4/2561.
 */

public interface OrderCallback {
    public void onResponse(OrderResponse orderResponse, Retrofit retrofit);
    public void onFailure(Throwable t);
    public void onBodyError(ResponseBody responseBody);
    public void onBodyErrorIsNull();
}
