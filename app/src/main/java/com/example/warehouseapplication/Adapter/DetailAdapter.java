package com.example.warehouseapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.warehouseapplication.Model.Check;
import com.example.warehouseapplication.Model.DetailModel;
import com.example.warehouseapplication.R;
import com.example.warehouseapplication.SlipActivity;
import com.example.warehouseapplication.Tool.Utils;

import java.util.List;

public class DetailAdapter extends ArrayAdapter<DetailModel> {

    private List<DetailModel> detailList;
    private Context mCtx;
    TextView name,qty,amount;
    String TAG = "detailAdapter";
    public DetailAdapter(List<DetailModel> P, Context c) {
        super(c, R.layout.detail_item, P);
        this.detailList = P;
        this.mCtx = c;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.detail_item, null, true);

        name = (TextView) view.findViewById(R.id.txtPro_p);
        qty = (TextView) view.findViewById(R.id.txtQty_p);
        amount = (TextView) view.findViewById(R.id.txtAmout_p);
        final DetailModel item  = detailList.get(position);
        name.setText(item.getProd_name());
        qty.setText(item.getQty()+" กิโลกรัม");
        amount.setText(item.getAmount()+" ฿");

        return view;
    }


}