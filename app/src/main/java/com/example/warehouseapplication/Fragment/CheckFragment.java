package com.example.warehouseapplication.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.warehouseapplication.Adapter.CheckAdapter;
import com.example.warehouseapplication.Handler;
import com.example.warehouseapplication.MainActivity;
import com.example.warehouseapplication.Model.Check;
import com.example.warehouseapplication.Model.OrderDetail;
import com.example.warehouseapplication.R;
import com.example.warehouseapplication.RegisterActivity;
import com.example.warehouseapplication.Tool.Utils;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckFragment extends Fragment{

    ListView listView;
    static List<Check> productList;
    OrderDetail orderDetail = new OrderDetail();
    static List<OrderDetail> orderDetails;
    protected static FragmentActivity mActivity;
    String  idUser;
    static int mPosition;
    String TAG = "CheckFrag";
    TextView tv_name,tv_price;
    public List<Check> getProductList() {
        return productList;
    }
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }


    public static CheckFragment newInstance() {
        CheckFragment checkFragment = new CheckFragment();
        Bundle args = new Bundle();
        checkFragment.setArguments(args);
        return checkFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productList = new ArrayList<>();
        orderDetails = new ArrayList<>();
        Log.d(TAG, "onCreate: ");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, container, false);
        listView = view.findViewById(R.id.mainCheck);
        Intent intent = getActivity().getIntent();
        idUser = intent.getStringExtra("idUser");
//        ShowList();



        return view;
    }

//    private void dialogShow(int position){
//        mPosition = position;
//
//        DialogFragment dialogOrderFragment = new DialogOrderFragment();
//        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
//        ((DialogOrderFragment) dialogOrderFragment).setValue(productList.get(position));
//        dialogOrderFragment.show(fragmentManager, "ORDER DIALOG");
//
//
//   }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            mActivity = (FragmentActivity) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ShowList();
    }

    private void ShowList() {
        if(productList.size() > 0){
            productList.clear();
        }


        Ion.with(CheckFragment.this)

               .load("http://beebikebnp.com/android/CheckChange.php")
                //.load("http://192.168.88.225/android/CheckChange.php")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        try {

                            JSONArray jsonArray = new JSONArray(result);
                            for (int i =0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String b = jsonObject.getString("docno");
                                String a = jsonObject.getString("status");
                                String c = jsonObject.getString("chk");
                                System.out.println(a);

                                if (c.equals("1")) {

                                    if (a.equals("WC")) {
                                        Ion.with(CheckFragment.this)

                                                .load("http://beebikebnp.com/android/Change.php")
                                                //.load("http://192.168.88.225/android/Change.php")
                                                .setBodyParameter("change", b)
                                                .asString()
                                                .setCallback(new FutureCallback<String>() {
                                                    @Override
                                                    public void onCompleted(Exception e, String result) {
                                                    }
                                                });

                                    }

                                }

                            }

                        }catch (JSONException ex){
                            ex.printStackTrace();
                        }

                    }
                });



        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.getMainUrl() + "android/CheckOrder.php?idUser=" + idUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("Invoice");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject prodObj = array.getJSONObject(i);
                                //Log.d(TAG, "onResponse: "+prodObj.toString());
                                Check p = new Check(prodObj.getString("docno")
                                        , prodObj.getString("status")
                                        ,prodObj.getString("slip")
                                        ,prodObj.getString("dis")
                                        ,prodObj.getString("amountall")
                                        ,prodObj.getString("mark"));
                                productList.add(p);


                            }

                            CheckAdapter adapter = new CheckAdapter(productList, mActivity.getApplicationContext(), Utils.filter);
                            listView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

        };
        Handler.getInstance(mActivity.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    //    @Override
//    public void onFinishEditDialog(String amount) {
//        orderDetail = new OrderDetail();
//
//        if(amount == null || amount.equals("") || amount.equals("0")){
//            return;
//        }
//
//        int mAmount = Integer.parseInt(amount);
//
//        if(orderDetails.size() != 0){
//            for (int i = 0; i < orderDetails.size(); i++) {
//                if(orderDetails.get(i).getCode().equals(productList.get(mPosition).getCode())){
//                    mAmount = orderDetails.get(i).getAmount() + Integer.parseInt(amount);
//                    orderDetail.setAmount(mAmount);
//                    break;
//                }
//
//            }
//            orderDetail.setAmount(mAmount);
//            orderDetail.setPrice(Integer.parseInt(productList.get(mPosition).getName()));
//            orderDetail.setCode(productList.get(mPosition).getCode());
//            orderDetail.setName(productList.get(mPosition).getId());
//            orderDetails.add(orderDetail);
//
//        }else{
//
//            orderDetail.setAmount(mAmount);
//            orderDetail.setPrice(Integer.parseInt(productList.get(mPosition).getName()));
//            orderDetail.setCode(productList.get(mPosition).getCode());
//            orderDetail.setName(productList.get(mPosition).getId());
//            orderDetails.add(orderDetail);
//        }
//
//
//    }
}
