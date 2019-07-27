package com.example.warehouseapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseapplication.API.ConnectManager;
import com.example.warehouseapplication.Adapter.TopupStatusAdapter;
import com.example.warehouseapplication.Callback.TopupListCallback;
import com.example.warehouseapplication.Model.TopupModel;
import com.example.warehouseapplication.R;
import com.example.warehouseapplication.Tool.Utils;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Retrofit;

/**
 * Created by Killy77 on 17/6/2561.
 */

public class StatusTopup extends Fragment {
    RecyclerView recycleviewChick;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ConnectManager connect = new ConnectManager();
    TopupListCallback topupListCallback = new TopupListCallback() {
        @Override
        public void onResponse(TopupModel topupModel, Retrofit retrofit) {
                adapter = new TopupStatusAdapter(getContext(), topupModel.getItems(), new TopupStatusAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(List<TopupModel.ItemsBean> orderlist, int position) {

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




    public static StatusTopup newInstance() {
        return new StatusTopup();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        connect.getTopup(topupListCallback, Utils.user.getChecklogin().getId_member());
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