package com.example.warehouseapplication.Callback;

import com.example.warehouseapplication.Model.PromotionModel;
import com.squareup.okhttp.ResponseBody;

import retrofit.Retrofit;

/**
 * Created by Killy77 on 29/4/2561.
 */

public interface PromotionCallback {
    public void onResponse(PromotionModel promotion, Retrofit retrofit);
    public void onFailure(Throwable t);
    public void onBodyError(ResponseBody responseBody);
    public void onBodyErrorIsNull();
}
