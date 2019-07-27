package com.example.warehouseapplication.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.warehouseapplication.Fragment.OrderListFragment;
import com.example.warehouseapplication.Model.Product;
import com.example.warehouseapplication.R;

public class DialogOrderFragment extends DialogFragment {
    Product mProduct;
    private AmountDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public interface AmountDialogListener {
        void onFinishEditDialog(String inputText);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_order, null);
        builder.setView(dialogView);

       TextView tv_name = dialogView.findViewById(R.id.tv_item_name);
        TextView tv_price = dialogView.findViewById(R.id.tv_item_price);

        tv_name.setText(mProduct.getId());
        tv_price.setText(mProduct.getName());
        builder.setMessage("กรุณากรอกจำนวนสินค้า")
                .setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText edt_amount = dialogView.findViewById(R.id.edt_amount);
                        String amount = edt_amount.getText().toString();
                        listener = new OrderListFragment();
                        listener.onFinishEditDialog(amount);


                    }
                })
                .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it

        return builder.create();
    }

    public void setValue(Product product){
        this.mProduct = product;
    }





}
