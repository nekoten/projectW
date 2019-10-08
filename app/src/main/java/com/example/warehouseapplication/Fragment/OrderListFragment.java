package com.example.warehouseapplication.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.warehouseapplication.Adapter.ProductAdapter;
import com.example.warehouseapplication.Handler;
import com.example.warehouseapplication.Model.OrderDetail;
import com.example.warehouseapplication.Model.Product;
import com.example.warehouseapplication.R;
import com.example.warehouseapplication.utils.DialogOrderFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderListFragment extends Fragment  implements DialogOrderFragment.AmountDialogListener  {
    private String strMyOfficer, strMyFood, strItem;
    ListView listView;
    static List<Product> productList;
    OrderDetail orderDetail = new OrderDetail();
    static List<OrderDetail> orderDetails;
    protected static FragmentActivity mActivity;

    static int mPosition;

    TextView tv_name,tv_price;

    public List<Product> getProductList() {
        return productList;
    }
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public static OrderListFragment newInstance() {
        OrderListFragment orderListFragment = new OrderListFragment();
        Bundle args = new Bundle();
        orderListFragment.setArguments(args);
        return orderListFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productList = new ArrayList<>();
        orderDetails = new ArrayList<>();




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        listView = view.findViewById(R.id.mainCheck);
        ShowList();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                dialogShow(position);

            }
        });
        return view;
    }

    private void dialogShow(int position){
        mPosition = position;

        DialogFragment dialogOrderFragment = new DialogOrderFragment();
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        ((DialogOrderFragment) dialogOrderFragment).setValue(productList.get(position));
        dialogOrderFragment.show(fragmentManager, "ORDER DIALOG");


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            mActivity = (FragmentActivity) context;
        }
    }
//10.10.9.87 ชั้น 8
//10.13.2.97 ชั้น 4
    private void ShowList() {
        if(productList.size() > 0){
            productList.clear();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://beebikebnp.com/android/product_view.php",
        //StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.88.225/android/product_view.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("Product");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject prodObj = array.getJSONObject(i);
                                Product p = new Product(prodObj.getString("name"), prodObj.getString("price_out"),prodObj.getString("code"),prodObj.getString("pic"));
                                productList.add(p);
                            }
                            ProductAdapter adapter = new ProductAdapter(productList, mActivity.getApplicationContext());
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

    @Override
    public void onFinishEditDialog(String amount) {
        orderDetail = new OrderDetail();

        if(amount == null || amount.equals("") || amount.equals("0")){
            return;
        }

        int mAmount = Integer.parseInt(amount);

        if(orderDetails.size() != 0){
            for (int i = 0; i < orderDetails.size(); i++) {
                if(orderDetails.get(i).getCode().equals(productList.get(mPosition).getCode())){
                    mAmount = orderDetails.get(i).getAmount() + Integer.parseInt(amount);
                    orderDetail.setAmount(mAmount);
                    break;
                }

            }
            orderDetail.setAmount(mAmount);
            orderDetail.setPrice(Integer.parseInt(productList.get(mPosition).getName()));
            orderDetail.setCode(productList.get(mPosition).getCode());
            orderDetail.setName(productList.get(mPosition).getId());
            orderDetails.add(orderDetail);

        }else{

            orderDetail.setAmount(mAmount);
            orderDetail.setPrice(Integer.parseInt(productList.get(mPosition).getName()));
            orderDetail.setCode(productList.get(mPosition).getCode());
            orderDetail.setName(productList.get(mPosition).getId());
            orderDetails.add(orderDetail);
        }


    }
}
