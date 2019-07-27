package com.example.warehouseapplication.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseapplication.Model.Orderlist_item;
import com.example.warehouseapplication.R;

/**
 * Created by Killy77 on 27/4/2561.
 */

public class CustomDialog_detailOrderlist extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes;
    public RecyclerView review;
    public RecyclerView.LayoutManager layoutManager;
    public RecyclerView.Adapter adapter;
    public TextView total;

    Orderlist_item item;

    //pap na

    public CustomDialog_detailOrderlist(Activity a, Orderlist_item item) {
        super(a);
        // TODO Auto-generated constructor stub
        this.item = item;
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.form_orderlist_detail);
        yes = (Button) findViewById(R.id.btn_ok);
        review = (RecyclerView) findViewById(R.id.detail);
        total = (TextView) findViewById(R.id.txttotal);

        review.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        review.setLayoutManager(layoutManager);
        adapter = new Orderlist_itemAdapter(getContext(), item);

        int totals = 0;
        for (int i = 0;i<item.getItems().size();i++){
            totals = totals+(Integer.valueOf(item.getItems().get(i).getprice())* Integer.valueOf(item.getItems().get(i).getQty()));
        }


        total.setText("Total : "+item.getItems().get(0).getTotalPrice()+" ฿");
        review.setAdapter(adapter);
        yes.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                dismiss();
                break;
            default: dismiss();
                break;
        }
        dismiss();
    }

}