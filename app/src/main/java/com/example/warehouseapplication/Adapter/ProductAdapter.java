package com.example.warehouseapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.warehouseapplication.Model.Product;
import com.example.warehouseapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private List<Product> productList;
    private Context mCtx;

    public ProductAdapter(List<Product> P,Context c) {

        super(c, R.layout.list_view_row,P);
        this.productList = P;
        this.mCtx = c;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_view_row, null, true);

        TextView name = (TextView) view.findViewById(R.id.txtShowFood);
        TextView id = (TextView) view.findViewById(R.id.txtShowPrice);
        ImageView img = view.findViewById(R.id.imvFood);

        Product product = productList.get(position);
        name.setText(product.getName());
        id.setText(product.getId());
        Picasso.with(mCtx).load(product.getPics()).into(img);





        return view;
    }


}
