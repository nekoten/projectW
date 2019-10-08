package com.example.warehouseapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouseapplication.Model.Check;

import com.example.warehouseapplication.R;
import com.example.warehouseapplication.SlipActivity;
import com.example.warehouseapplication.Tool.Utils;


import java.util.ArrayList;
import java.util.List;

public class CheckAdapter extends ArrayAdapter<Check> {

    private List<Check> productList = new ArrayList<>();
    private Context mCtx;
    String TAG = "Adapter";
    public CheckAdapter(List<Check> P, Context c,String filter) {
        super(c, R.layout.follow_detail, P);
        for (Check check : P) {
            switch (filter){
                case "1":
                    if(check.getStatus().equals("WC")&&!check.getSlip().equals("")){
                        this.productList.add(check);
                    }
                    break;
                case "2":
                    if(check.getStatus().equals("WC")&&check.getSlip().equals("")){
                        this.productList.add(check);
                    }
                    break;
                case "3":
                    if(check.getStatus().equals("CO")){
                        this.productList.add(check);
                    }
                    break;
                case "4":
                    if(check.getStatus().equals("FIN")){
                        this.productList.add(check);
                    }
                    break;
                 default:this.productList.add(check);
                    break;
            }
        }
        this.mCtx = c;

    }

    @Override
    public int getCount() {
        return this.productList.size();
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
            if(product1.getSlip().equals("")){
                status.setBackgroundResource(R.color.colorS1);
                status.setText("ยังไม่ได้ชำระเงิน");
            }else{
                status.setBackgroundResource(R.color.colorS4);
                status.setText("รอยืนยันชำระเงิน");
            }
        }
        else if (product1.getStatus().equals("CO")){
            status.setBackgroundResource(R.color.colorS3);
            status.setText("ชำระเงินแล้ว");
        }
        else if (product1.getStatus().equals("DL")){
            status.setBackgroundResource(R.color.colorS5);
            status.setText("สินค้าพร้อมจัดส่ง");
        }
        else{
            status.setBackgroundResource(R.color.colorPrimary);
            status.setText("เสร็จสิ้นการสั่งซื้อ");
        }

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Utils.order_item = product;
                    Intent intent = new Intent(mCtx, SlipActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mCtx.startActivity(intent);
            }
        });

        return view;
    }



}