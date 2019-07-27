package com.example.warehouseapplication.Callback;

import com.example.warehouseapplication.Model.EditResponse;
import com.squareup.okhttp.ResponseBody;

import retrofit.Retrofit;

/**
 * Created by Killy77 on 14/4/2561.
 */
public interface EditCallback {
    public void onResponse(EditResponse response, Retrofit retrofit);
    public void onFailure(Throwable t);
    public void onBodyError(ResponseBody responseBody);
    public void onBodyErrorIsNull();
}
