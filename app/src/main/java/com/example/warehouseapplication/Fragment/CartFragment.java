package com.example.warehouseapplication.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseapplication.Adapter.CartAdapter;
import com.example.warehouseapplication.MainActivity;
import com.example.warehouseapplication.Model.OrderDetail;
import com.example.warehouseapplication.OrderActivity;
import com.example.warehouseapplication.R;
import com.example.warehouseapplication.utils.DialogEditCartItemFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CartFragment extends Fragment implements DialogEditCartItemFragment.AmountDialogCartListener {

    Button btnSend, btnRemove;

    RecyclerView mRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private static LinearLayoutManager mLayoutManager;
    protected static FragmentActivity mActivity;
    static List<OrderDetail> mOrderDetails;
    int totalAll;
    private static TextView tv_total_all;
    String client;
    String idUser;

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();

        mOrderDetails = OrderListFragment.orderDetails;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            mActivity = (FragmentActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cart, container, false);
        btnSend = view.findViewById(R.id.btn_send_order);
        btnRemove = view.findViewById(R.id.btn_delete);
        mRecyclerView = view.findViewById(R.id.recyclert_cart);

        tv_total_all = view.findViewById(R.id.tv_total_all);
        OrderActivity orderActivity =  (OrderActivity)getActivity();
        client = orderActivity.user;
        idUser = orderActivity.idUser;

        mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CartAdapter(mOrderDetails, mActivity);
        mAdapter.setHasStableIds(true);

        mRecyclerView.setItemAnimator(null);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sendOrder(mOrderDetails)) {
                    Toast.makeText(mActivity, "สั่งซื้อสินค้าเรียบร้อย", Toast.LENGTH_SHORT).show();
                    mOrderDetails.clear();
                    mAdapter.notifyDataSetChanged();

                    mActivity.getSupportFragmentManager().popBackStack();

                } else {
                    Toast.makeText(mActivity, "ไม่สามารถทำรายการได้", Toast.LENGTH_SHORT).show();

                }

            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOrderDetails.clear();
                mAdapter.notifyDataSetChanged();

            }
        });

        calTotal();
        return view;
    }

    private void calTotal() {

                for (int i = 0; i < mOrderDetails.size(); i++) {
                    totalAll = totalAll + (mOrderDetails.get(i).getPrice() * mOrderDetails.get(i).getAmount());
                }
                tv_total_all.setText(String.valueOf(totalAll));
    }

    public boolean sendOrder(List<OrderDetail> orderDetails) {

        final String date01,date02;

        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String date2 = df2.format(Calendar.getInstance().getTime());

        DateFormat df3 = new SimpleDateFormat("HH:mm");
        String date3 = df3.format(Calendar.getInstance().getTime());

        String date22[] = date2.split("/");
        String date33[] = date3.split(":");


        if (Integer.parseInt(date33[0]) < 6){


            date01 = date22[2]+"/"+date22[1]+"/"+date22[0]+" "+"06:00";
            System.out.println(date01);

            date02 = date22[2]+"/"+date22[1]+"/"+date22[0]+" "+"07:00";
            System.out.println(date02);

            System.out.println("1");

        }else {

            date01 = date22[2]+"/"+date22[1]+"/"+(Integer.parseInt(date22[0])+1)+" "+"06:00";
            System.out.println(date01);

            date02 = date22[2]+"/"+date22[1]+"/"+(Integer.parseInt(date22[0])+1)+" "+"07:00";
            System.out.println(date02);

            System.out.println("2");



        }

        //statement
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream os = null;
                InputStream is = null;
                HttpURLConnection conn = null;
                try {
                    //constants
                    String strUrl = MainActivity.getMainUrl() +  "android/order.php";
                    URL url = new URL(strUrl);
                    JSONArray jsonObject = new JSONArray();
                    JSONArray mJson = new JSONArray();
                    JSONObject jsonclient = new JSONObject();
                    jsonclient.put("client",client);
                    jsonclient.put("idUser",idUser);
                    jsonclient.put("time",date01);
                    jsonclient.put("time2",date02);
                    for (int i = 0; i < mOrderDetails.size(); i++) {
                        JSONObject mJsonObject = new JSONObject();
                        mJsonObject.put("code", mOrderDetails.get(i).getCode());
                        mJsonObject.put("amount", mOrderDetails.get(i).getAmount());
                        mJsonObject.put("price", mOrderDetails.get(i).getPrice());
                        jsonObject.put(mJsonObject);
                    }
                    jsonclient.put("order",jsonObject);
                    mJson.put(jsonclient);



                    String message = mJson.toString();

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /*milliseconds*/);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    try {
                        conn.setRequestMethod("POST");
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setFixedLengthStreamingMode(message.getBytes().length);

                    //make some HTTP header nicety
                    conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                    //open
                    conn.connect();

                    //setup send
                    os = new BufferedOutputStream(conn.getOutputStream());
                    os.write(message.getBytes());
                    //clean up
                    os.flush();

                    //do somehting with response
                    is = conn.getInputStream();
                    //String contentAsString = readIt(is,len);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //clean up
                    try {
                        os.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    conn.disconnect();
                }
            }
        }).start();

        return true;
    }


    @Override
    public void onFinishEditCartDialog(String inputText, String code) {
        int amount = Integer.parseInt(inputText);
        for (int i = 0; i < mOrderDetails.size(); i++) {
            if (mOrderDetails.get(i).getCode().equals(code)) {
                if (amount > 0) {
                    mOrderDetails.get(i).setAmount(amount);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mOrderDetails.remove(i);
                    mAdapter.notifyDataSetChanged();
                }

            }
        }
        calTotal();
    }
}
