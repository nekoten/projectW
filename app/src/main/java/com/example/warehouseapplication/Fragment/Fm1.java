package com.example.warehouseapplication.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseapplication.API.ConnectManager;
import com.example.warehouseapplication.Adapter.CustomDialog_detailOrderlist;
import com.example.warehouseapplication.Adapter.FollowAdapter;
import com.example.warehouseapplication.Callback.OrderListCallback;
import com.example.warehouseapplication.Callback.OrderList_ItemCallback;
import com.example.warehouseapplication.Callback.ResponseCallback;
import com.example.warehouseapplication.Model.Orderlist;
import com.example.warehouseapplication.Model.Orderlist_item;
import com.example.warehouseapplication.Model.ResponseModel;
import com.example.warehouseapplication.R;
import com.example.warehouseapplication.Tool.Utils;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Retrofit;

import static android.content.ContentValues.TAG;

/**
 * Created by Killy77 on 21/4/2561.
 */

public class Fm1 extends Fragment {
    RecyclerView recycleviewChick;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ConnectManager connect = new ConnectManager();
    ResponseCallback responseCallback = new ResponseCallback() {
        @Override
        public void onResponse(ResponseModel responseModel, Retrofit retrofit) {
            Utils.toast(getContext(),responseModel.getDescribtion());
            connect.orderList(orderListCallback,Utils.user.getChecklogin().getId_member());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onBodyError(ResponseBody responseBody) {

        }

        @Override
        public void onBodyErrorIsNull() {

        }
    };
    OrderList_ItemCallback orderList_itemCallback= new OrderList_ItemCallback() {
        @Override
        public void onResponse(Orderlist_item listItem, Retrofit retrofit) {

            CustomDialog_detailOrderlist cdd=new CustomDialog_detailOrderlist(getActivity(),listItem);
            cdd.show();
        }

        @Override
        public void onFailure(Throwable t) {
            Log.d(TAG,"onFailure: ");
        }

        @Override
        public void onBodyError(ResponseBody responseBody) {
            Log.d(TAG, "onBodyError: ");
        }

        @Override
        public void onBodyErrorIsNull() {
            Log.d(TAG, "onBodyErrorIsNull: ");
        }
    } ;

    OrderListCallback orderListCallback = new OrderListCallback() {
        @Override
        public void onResponse(Orderlist orderlist, Retrofit retrofit) {
            adapter = new FollowAdapter(getContext(), orderlist.getItems(), new FollowAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(List<Orderlist.ItemsBean> orderlist, int position) {
                    connect.orderList_item(orderList_itemCallback,orderlist.get(position).getId_order());
                }

                @Override
                public void onConfirm(List<Orderlist.ItemsBean> orderlist, int position) {
                    connect.acceptMember(responseCallback,orderlist.get(position).getId_order(),"4");
                }
            });
            recycleviewChick.setAdapter(adapter);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onBodyError(ResponseBody responseBody) {

        }

        @Override
        public void onBodyErrorIsNull() {

        }
    };


    public static Fm1 newInstance() {
        return new Fm1();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        connect.orderList(orderListCallback,Utils.user.getChecklogin().getId_member());
        return inflater.inflate(R.layout.fragment_fm, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleviewChick = (RecyclerView) view.findViewById(R.id.test);
        recycleviewChick.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleviewChick.setLayoutManager(layoutManager);

    }



}
