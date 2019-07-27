package com.example.warehouseapplication.Callback;

import com.example.warehouseapplication.Model.Orderlist_item;
import com.squareup.okhttp.ResponseBody;

import retrofit.Retrofit;

/**
 * Created by Killy77 on 26/4/2561.
 */

public interface OrderList_ItemCallback {
    public void onResponse(Orderlist_item listItem, Retrofit retrofit);
    public void onFailure(Throwable t);
    public void onBodyError(ResponseBody responseBody);
    public void onBodyErrorIsNull();
}
