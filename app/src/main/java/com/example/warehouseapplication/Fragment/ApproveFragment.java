package com.example.warehouseapplication.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.warehouseapplication.Adapter.ApproveAdapter;
import com.example.warehouseapplication.Adapter.CheckAdapter;
import com.example.warehouseapplication.Handler;
import com.example.warehouseapplication.MainActivity;
import com.example.warehouseapplication.Model.Check;
import com.example.warehouseapplication.Model.OrderDetail;
import com.example.warehouseapplication.Model.UserApprove;
import com.example.warehouseapplication.R;
import com.example.warehouseapplication.Tool.Utils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApproveFragment extends Fragment{

    ListView listView;
    static List<UserApprove> approveList;
    OrderDetail orderDetail = new OrderDetail();
    static List<OrderDetail> orderDetails;
    protected static FragmentActivity mActivity;
    String  idUser;
    static int mPosition;
    String TAG = "Approve";
    TextView tv_name,tv_price;
    public List<UserApprove> getapproveList() {
        return approveList;
    }
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }


    public static ApproveFragment newInstance() {
        ApproveFragment approveFragment = new ApproveFragment();
        Bundle args = new Bundle();
        approveFragment.setArguments(args);
        return approveFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        approveList = new ArrayList<>();
        orderDetails = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, container, false);
        listView = view.findViewById(R.id.mainCheck);
        Intent intent = getActivity().getIntent();
//        ShowList();



        return view;
    }

//    private void dialogShow(int position){
//        mPosition = position;
//
//        DialogFragment dialogOrderFragment = new DialogOrderFragment();
//        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
//        ((DialogOrderFragment) dialogOrderFragment).setValue(approveList.get(position));
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
        if(approveList.size() > 0){
            approveList.clear();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.getMainUrl() + "android/getUserApprove.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("user_approve");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject prodObj = array.getJSONObject(i);
                                //Log.d(TAG, "onResponse: "+prodObj.toString());

//                                ,String username
//            ,String userpasword
//            ,String user_type
//            ,String name
//            ,String lastname
//            ,String telephone
//            ,String address
//            ,String active)
                                UserApprove p = new UserApprove(
                                        prodObj.getString("ROWAUTO"),
                                        prodObj.getString("username"),
                                        prodObj.getString("userpassword"),
                                        prodObj.getString("user_type"),
                                        prodObj.getString("name"),
                                        prodObj.getString("lastname"),
                                        prodObj.getString("telephone"),
                                        prodObj.getString("address"),
                                        prodObj.getString("active")
                                        );
                                approveList.add(p);


                            }

                            ApproveAdapter adapter = new ApproveAdapter(approveList, mActivity.getApplicationContext(), new ApproveAdapter.OnItemClickListener() {

                                @Override
                                public void onItemApprove(List<UserApprove> item, int position) {
                                    Ion.with(getContext())
                                            .load("http://beebikebnp.com/android/Approve.php")
                                            //.load("http://192.168.88.225/android/Finish.php")
                                            .setBodyParameter("ROWAUTO", item.get(position).getROWAUTO())
                                            .asString()
                                            .setCallback(new FutureCallback<String>() {
                                                @Override
                                                public void onCompleted(Exception e, String result) {
                                                    Utils.toast(getContext(),"ยืนยันสำเร็จ");
                                                    Log.d(TAG, "onCompleted: "+result);
                                                    ShowList();
                                                }
                                            });
                                }

                                @Override
                                public void onItemReject(List<UserApprove> item, int position) {
                                    Ion.with(getContext())
                                            .load("http://beebikebnp.com/android/Reject.php")
                                            //.load("http://192.168.88.225/android/Finish.php")
                                            .setBodyParameter("ROWAUTO", item.get(position).getROWAUTO())
                                            .asString()
                                            .setCallback(new FutureCallback<String>() {
                                                @Override
                                                public void onCompleted(Exception e, String result) {
                                                    Utils.toast(getContext(),"ทำรายการเรียบร้อย");
//                                Intent intent = new Intent(getContext(), OrderActivity.class);
//                                mCtx.startActivity(intent);

                                                    Log.d(TAG, "onCompleted: "+result);
                                                    ShowList();
                                                }
                                            });
                                }
                            });
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
//                if(orderDetails.get(i).getCode().equals(approveList.get(mPosition).getCode())){
//                    mAmount = orderDetails.get(i).getAmount() + Integer.parseInt(amount);
//                    orderDetail.setAmount(mAmount);
//                    break;
//                }
//
//            }
//            orderDetail.setAmount(mAmount);
//            orderDetail.setPrice(Integer.parseInt(approveList.get(mPosition).getName()));
//            orderDetail.setCode(approveList.get(mPosition).getCode());
//            orderDetail.setName(approveList.get(mPosition).getId());
//            orderDetails.add(orderDetail);
//
//        }else{
//
//            orderDetail.setAmount(mAmount);
//            orderDetail.setPrice(Integer.parseInt(approveList.get(mPosition).getName()));
//            orderDetail.setCode(approveList.get(mPosition).getCode());
//            orderDetail.setName(approveList.get(mPosition).getId());
//            orderDetails.add(orderDetail);
//        }
//
//
//    }
}
