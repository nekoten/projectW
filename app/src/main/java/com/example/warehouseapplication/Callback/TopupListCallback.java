package com.example.warehouseapplication.Callback;

import com.example.warehouseapplication.Model.TopupModel;
import com.squareup.okhttp.ResponseBody;

import retrofit.Retrofit;

/**
 * Created by Killy77 on 29/4/2561.
 */

public interface TopupListCallback {
    public void onResponse(TopupModel topupModel, Retrofit retrofit);
    public void onFailure(Throwable t);
    public void onBodyError(ResponseBody responseBody);
    public void onBodyErrorIsNull();
}
