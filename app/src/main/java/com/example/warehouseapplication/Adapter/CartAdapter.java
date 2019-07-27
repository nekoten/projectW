package com.example.warehouseapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseapplication.Model.OrderDetail;
import com.example.warehouseapplication.R;
import com.example.warehouseapplication.utils.DialogEditCartItemFragment;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>  {
    List<OrderDetail> mOrderDetails;
    Context mContext;

    public CartAdapter(List<OrderDetail> orderDetail,Context context){
        this.mOrderDetails = orderDetail;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.cart_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_price.setText(String.valueOf(mOrderDetails.get(position).getPrice()));
        holder.tv_amount.setText(String.valueOf(mOrderDetails.get(position).getAmount()));
        holder.tv_name.setText(String.valueOf(mOrderDetails.get(position).getName()));
        String total = String.valueOf(mOrderDetails.get(position).getPrice() * mOrderDetails.get(position).getAmount());
        holder.tv_total.setText(total);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DialogEditCartItemFragment dialogOrderFragment = new DialogEditCartItemFragment();
                FragmentManager fragmentManager = ((FragmentActivity)mContext).getSupportFragmentManager();
                ((DialogEditCartItemFragment) dialogOrderFragment).setValue(mOrderDetails.get(position));
                dialogOrderFragment.show(fragmentManager, "ORDER DIALOG");
                Toast.makeText(mContext,String.valueOf(mOrderDetails.get(position).getName()),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mOrderDetails.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
       public TextView tv_name,tv_amount,tv_price,tv_total,tv_total_all;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_total = itemView.findViewById(R.id.tv_total);
        }
    }
}
