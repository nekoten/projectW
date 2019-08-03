package com.example.warehouseapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouseapplication.Model.Check;
import com.example.warehouseapplication.OrderActivity;
import com.example.warehouseapplication.R;
import com.example.warehouseapplication.SlipActivity;
import com.example.warehouseapplication.Tool.Utils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CheckAdapter extends ArrayAdapter<Check> {

    private List<Check> productList;
    private Context mCtx;

    public CheckAdapter(List<Check> P, Context c) {

        super(c, R.layout.follow_detail, P);
        this.productList = P;
        this.mCtx = c;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.follow_detail, null, true);


        TextView docno = (TextView) view.findViewById(R.id.textOrder);
        final Check product = productList.get(position);
        docno.setText(product.getDocNo());

        Button status = (Button) view.findViewById(R.id.textstatus);
        Check product1 = productList.get(position);


        if (product1.getStatus().equals("WC")) {
            status.setBackgroundResource(R.color.colorS1);
            status.setText("ยังไม่ได้ชำระ");
        } else {
            status.setBackgroundResource(R.color.colorS3);
            status.setText("ชำระเงินแล้ว");
        }

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.user.getUsername() == "admin"){
                    if (product.getStatus().equals("WC")) {

                        Toast.makeText(mCtx, product.getDocNo(), Toast.LENGTH_SHORT).show();

                        Ion.with(mCtx)

                                .load("http://beebikebnp.com/android/Update.php")
                                .setBodyParameter("id", product.getDocNo())
                                .asString()
                                .setCallback(new FutureCallback<String>() {
                                    @Override
                                    public void onCompleted(Exception e, String result) {
//                                    notifyDataSetChanged();
                                    }
                                });

                        Ion.with(mCtx)

                                .load("http://beebikebnp.com/android/SelectBeer.php")
                                .setBodyParameter("id", product.getDocNo())
                                .asString()
                                .setCallback(new FutureCallback<String>() {
                                    @Override
                                    public void onCompleted(Exception e, String result) {

                                        try {
                                            JSONObject obj = new JSONObject(result);
                                            JSONArray array = obj.getJSONArray("Invoice");
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject prodObj = array.getJSONObject(i);
                                                String a = prodObj.getString("prod_id");
                                                String b = prodObj.getString("qty");

                                                Ion.with(mCtx)

                                                        .load("http://beebikebnp.com/android/InsertBeer.php")
                                                        .setBodyParameter("pro", a)
                                                        .setBodyParameter("qty", b)
                                                        .asString()
                                                        .setCallback(new FutureCallback<String>() {
                                                            @Override
                                                            public void onCompleted(Exception e, String result) {

                                                            }
                                                        });

                                            }

                                        } catch (JSONException ex) {

                                            ex.printStackTrace();
                                        }

                                    }
                                });

                    } else if (product.getStatus().equals("CO")) {
                    }
                }
                else{
                    Utils.order_id = product.getDocNo();
                    Intent intent = new Intent(mCtx, SlipActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mCtx.startActivity(intent);
                }



            }
        });

        return view;
    }


}