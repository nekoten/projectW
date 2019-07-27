package com.example.warehouseapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.warehouseapplication.API.ConnectManager;
import com.example.warehouseapplication.Callback.StoreCallback;
import com.example.warehouseapplication.Model.StoreModel;
import com.example.warehouseapplication.Tool.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.okhttp.ResponseBody;

import retrofit.Retrofit;

//import com.example.warehouseapplication.Adapter.CustomDialog;
//import com.example.warehouseapplication.Adapter.CustomDialog_other;
//import com.example.warehouseapplication.Adapter.CustomDialog_water;
//import com.example.warehouseapplication.Fragment.Fm1;
//import com.example.warehouseapplication.Fragment.Fm_Location;
//import com.example.warehouseapplication.Fragment.OtherFM;
//import com.example.warehouseapplication.Fragment.fm_menu;

public class TabActivity extends AppCompatActivity {
    BottomNavigationView mBottomNav;
    Toolbar toolbar;
    String TAG = "Menu";
    FloatingActionButton fab;

    ConnectManager connect = new ConnectManager();
    StoreCallback callback = new StoreCallback() {
        @Override
        public void onResponse(StoreModel storeModel, Retrofit retrofit) {
            Utils.storeModel = storeModel;
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onBodyError(ResponseBody responseBody) {

        }

        @Override
        public void onBodyErrorIsNull() {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tab);
//        mBottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
//        getSupportActionBar().setTitle("Follow");
//        toolbar.setVisibility(View.GONE);
//        connect.getstore(callback);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.content_main, fm_menu.newInstance())
//                .commit();
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Utils.user!=null) {
//                    Utils.order.setId_member(Utils.user.getChecklogin().getId_member());
//                    Utils.order.setOrder(Utils.orderbanlist);
//                    Log.d(TAG, "onClick: "+Utils.order.getId_member()+" / "+ Utils.order.getOrder().size() );
//                    Log.d(TAG, "onClick: "+Utils.user.getChecklogin().getCash()+" Bath");
//
//                    Intent intent = new Intent(TabActivity.this,BasketActivity.class);
//                    startActivity(intent);
//                }else {
//                    Intent intent = new Intent(TabActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                }
//
//
//            }
//        });

//        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Intent intent;
//                switch(item.getItemId()){
//                    case R.id.item_location:
//                        toolbar.setVisibility(View.GONE);
//                        fab.setVisibility(View.GONE);
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.content_main, Fm_Location.newInstance())
//                                .commit();
//                        break;
//                    case R.id.item_recent:
//                        toolbar.setVisibility(View.GONE);
//                        fab.setVisibility(View.VISIBLE);
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.content_main, fm_menu.newInstance())
//                                .commit();
//                        break;
//                    case R.id.item_scan :
//                        if (Utils.user!=null) {
//                            toolbar.setVisibility(View.VISIBLE);
//                            getSupportActionBar().setTitle("My Topup");
//                            fab.setVisibility(View.GONE);
//                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, StatusTopup.newInstance()).commit();
//                        }else {
//                            intent = new Intent(TabActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                        }
//
//                        break;
//                    case R.id.item_other:
//                        fab.setVisibility(View.GONE);
//                        toolbar.setVisibility(View.GONE);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, OtherFM.newInstance()).commit();
//
//                        break;
//                    case R.id.item_favorite:
//                        if (Utils.user!=null) {
//                            toolbar.setVisibility(View.VISIBLE);
//                            getSupportActionBar().setTitle("Follow");
//                            fab.setVisibility(View.GONE);
//                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, Fm1.newInstance()).commit();
//                        }else {
//                            intent = new Intent(TabActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                        }
//                        break;
//
//                    default:
//
//
//                        break;
//                }
//                return true;
//            }
//        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        //ดิบๆไปก่อน
//        if (Utils.addup){
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fm_menu.newInstance()).commit();
//        }else {
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, OtherFM.newInstance()).commit();
//        }
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if(result != null) {
//            if(result.getContents() == null) {
//                Log.d("Ammy", "fail ");
//            } else {
//
//                Log.d("Ammy", "onActivityResult: "+result.getContents());
//
//                if (Integer.valueOf(result.getContents()) >= 10035 && Integer.valueOf(result.getContents()) <= 10039) {
//
//                    for(int i =0;i<Utils.product.getProduct().size();i++){
//                        for (int j =0;j< Utils.product.getProduct().get(i).getBurgur().size();j++){
//                            if(Utils.product.getProduct().get(i).getBurgur().get(j).getId_product().equals(result.getContents())){
//                                Log.d("Ammy", "QR_Other");
//                                CustomDialog_other other = new CustomDialog_other(TabActivity.this,Utils.product.getProduct().get(i).getBurgur().get(j));
//                                other.show();
//                            }
//                        }
//                    }
//
//
//                } else if (Integer.valueOf(result.getContents()) >= 10040 && Integer.valueOf(result.getContents()) <= 10045) {
//
//                    for(int i =0;i<Utils.product.getProduct().size();i++){
//                        for (int j =0;j< Utils.product.getProduct().get(i).getBurgur().size();j++){
//                            if(Utils.product.getProduct().get(i).getBurgur().get(j).getId_product().equals(result.getContents())){
//                                Log.d("Ammy", "QR_Water");
//                                CustomDialog_water water = new CustomDialog_water(TabActivity.this,Utils.product.getProduct().get(i).getBurgur().get(j));
//                                water.show();
//                            }
//                        }
//                    }
//
//
//                } else {
//
//                    for(int i =0;i<Utils.product.getProduct().size();i++){
//                        for (int j =0;j< Utils.product.getProduct().get(i).getBurgur().size();j++){
//                            if(Utils.product.getProduct().get(i).getBurgur().get(j).getId_product().equals(result.getContents())){
//                                Log.d("Ammy", "QR_Burger");
//                                CustomDialog cdd = new CustomDialog(TabActivity.this,Utils.product.getProduct().get(i).getBurgur().get(j));
//                                cdd.show();
//                            }
//                        }
//                    }
//
//                }
//
//            }
//        }
//    }
}
