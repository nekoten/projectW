package com.example.warehouseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.warehouseapplication.Fragment.CartFragment;
import com.example.warehouseapplication.Fragment.CheckFragment;
import com.example.warehouseapplication.Fragment.OrderListFragment;
import com.example.warehouseapplication.Model.OrderDetail;
import com.example.warehouseapplication.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity  extends AppCompatActivity{


    public String getStrMyOfficer() {
        return strMyOfficer;
    }
    public String strMyOfficer;
    public String  strMyFood, strItem;
    public String user,idUser,firstname;

    TextView test;


    static List<Product> productList;

    public static List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public static void setOrderDetails(List<OrderDetail> orderDetails) {
        OrderActivity.orderDetails = orderDetails;
    }

    static List<OrderDetail> orderDetails;
    TextView welcomeuser;
    Toolbar toolbar;
    FragmentTransaction fragmentTransaction;
    OrderListFragment orderListFragment;

    private TextView txtShowOfficer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_order);

        productList = new ArrayList<>();
        orderDetails = new ArrayList<>();
        Intent intent = getIntent();
        user = intent.getStringExtra("username");
        idUser = intent.getStringExtra("idUser");
        firstname = intent.getStringExtra("firstname");


        toolbar = findViewById(R.id.toolbar);
        super.onCreate(savedInstanceState);

        orderListFragment = OrderListFragment.newInstance();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrag, orderListFragment);
        fragmentTransaction.commit();

        setSupportActionBar(toolbar);
        welcomeuser = findViewById(R.id.welcomeUser);
        welcomeuser.setText("สวัสดี " + firstname);

        //Bind Widget
        bindWidget();
        //setup Text Show Officer
        setUpTxtShowOfficer();

        test = findViewById(R.id.test);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, SlipActivity.class);
                startActivity(intent);
            }
        });




    }   //onCreate

    private void setUpTxtShowOfficer() {

        strMyOfficer = getIntent().getExtras().getString("Name");
        txtShowOfficer.setText(strMyOfficer);

    }   //setup text show Officer

    private void bindWidget() {
        txtShowOfficer = (TextView) findViewById(R.id.txtShowOfficer);
    }   //binddWidget

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_items, menu);
        return true;    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cart:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                CartFragment cartFragment = CartFragment.newInstance();
                fragmentTransaction.replace(R.id.mainFrag, cartFragment, "cart_fragment");
                cartFragment.setRetainInstance(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

            case R.id.item_favorite:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                CheckFragment checkFragment = CheckFragment.newInstance();
                fragmentTransaction.replace(R.id.mainFrag, checkFragment, "check_fragment");
                checkFragment.setRetainInstance(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

//           case R.id.slip:
//               fragmentTransaction = getSupportFragmentManager().beginTransaction();
//               SlipActivity slipActivity = SlipActivity.newInstance();
//               fragmentTransaction.replace(R.id.mainFrag, slipActivity, "check_fragment");
//               slipActivity.setRetainInstance(true);
//               fragmentTransaction.addToBackStack(null);
//               fragmentTransaction.commit();
//               break;



        }






        return super.onOptionsItemSelected(item);
    }



}   //Main Class
