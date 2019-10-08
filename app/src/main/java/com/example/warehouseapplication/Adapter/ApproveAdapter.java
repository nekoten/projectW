package com.example.warehouseapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.warehouseapplication.Model.Check;
import com.example.warehouseapplication.Model.TopupModel;
import com.example.warehouseapplication.Model.UserApprove;
import com.example.warehouseapplication.OrderActivity;
import com.example.warehouseapplication.R;
import com.example.warehouseapplication.SlipActivity;
import com.example.warehouseapplication.Tool.Utils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApproveAdapter extends ArrayAdapter<UserApprove> {

    private List<UserApprove> productList = new ArrayList<>();
    private Context mCtx;
    String TAG = "Adapter";
    ApproveAdapter.OnItemClickListener listener;

    public ApproveAdapter(List<UserApprove> P, Context c, ApproveAdapter.OnItemClickListener listener) {
        super(c, R.layout.row_approve, P);
        for (UserApprove approve : P) {
            this.productList.add(approve);
        }
        this.mCtx = c;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemApprove(List<UserApprove> item, int position);
        void onItemReject(List<UserApprove> item, int position);

    }

    @Override
    public int getCount() {
        return this.productList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.row_approve, null, true);
        TextView username = (TextView) view.findViewById(R.id.textOrder);
        final UserApprove userapprove = productList.get(position);
        username.setText(userapprove.getUsername());
        Button btn_approve = (Button) view.findViewById(R.id.btn_approve);
        Button btn_reject = (Button) view.findViewById(R.id.btn_reject);

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemApprove(productList,position);
            }
        });
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemReject(productList,position);
            }
        });


        return view;
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_approve:
//                Log.d(TAG, "onClick: "+product1.getROWAUTO());
//                Ion.with(getContext())
//                        .load("http://beebikebnp.com/android/Approve.php")
//                        //.load("http://192.168.88.225/android/Finish.php")
//                        .setBodyParameter("ROWAUTO", product1.getROWAUTO())
//                        .asString()
//                        .setCallback(new FutureCallback<String>() {
//                            @Override
//                            public void onCompleted(Exception e, String result) {
//                                Log.d(TAG, "onCompleted: "+result);
//                            }
//                        });
//                break;
//            case R.id.btn_reject:
//                Log.d(TAG, "onClick: "+product1.getROWAUTO());
//                Ion.with(getContext())
//                        .load("http://beebikebnp.com/android/Reject.php")
//                        //.load("http://192.168.88.225/android/Finish.php")
//                        .setBodyParameter("ROWAUTO", product1.getROWAUTO())
//                        .asString()
//                        .setCallback(new FutureCallback<String>() {
//                            @Override
//                            public void onCompleted(Exception e, String result) {
//                                Log.d(TAG, "onCompleted: "+result);
//                            }
//                        });
//                break;
//
//
//        }
//    }
}