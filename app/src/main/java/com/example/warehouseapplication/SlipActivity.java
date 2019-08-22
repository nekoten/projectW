package com.example.warehouseapplication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.warehouseapplication.Adapter.CheckAdapter;
import com.example.warehouseapplication.Adapter.DetailAdapter;
import com.example.warehouseapplication.Model.Check;
import com.example.warehouseapplication.Model.DetailModel;
import com.example.warehouseapplication.Tool.Utils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
//"http://beebikebnp.com/android/"

public class SlipActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String SERVER_ADDRESS = "http://beebikebnp.com/";
    private static final String imagePath = "http://beebikebnp.com/android/";
    ImageView imageToUpload, downloadedImage;
    Button bUploadImage, bUpdate ,bFin;
    EditText uploadImageName, downloadImageName;
    TextView id,dis;
    String TAG = "Ammy";
    ListView listView;
    static List<DetailModel> detailList = new ArrayList<>();
    private Animation mShakeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slip);

        imageToUpload = findViewById(R.id.imageToUpload);
        id = findViewById(R.id.orderID);
        dis = findViewById(R.id.txtdis);
        bUploadImage = findViewById(R.id.bUploadImage);
        bUpdate = findViewById(R.id.updateStatus);
        bFin = findViewById(R.id.finStatus);
        listView = findViewById(R.id.detail_list);
        Log.d(TAG, "onCreate: "+Utils.order_item.getAmountAll());


        dis.setText(Utils.order_item.getDis()+" ฿");
        mShakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);

        id.setText(Utils.order_item.getDocNo());
        ShowList();
        bUpdate.setText("ยืนยันการชำระ");
        bFin.setText("ปิดจ๊อบ");

        bUploadImage.setOnClickListener(this);
        bUpdate.setOnClickListener(this);
        bFin.setOnClickListener(this);

        if (Utils.user.getUsername().equals("admin")) {
            bUploadImage.setVisibility(View.GONE);
            if (!Utils.order_item.getSlip().isEmpty()) {
                Picasso.with(getApplicationContext()).load(imagePath + Utils.order_item.getSlip()).into(imageToUpload);
                if (Utils.order_item.getStatus().equals("CO")) {
                    bUpdate.setVisibility(View.GONE);
                    bFin.setVisibility(View.VISIBLE);
                }
            }else {
                    bUpdate.setEnabled(false);
                    bUpdate.setAlpha((float) .3);
                if (Utils.order_item.getStatus().equals("CO")) {
                    bUpdate.setVisibility(View.GONE);
                }
            }
        } else {
            bUpdate.setVisibility(View.GONE);
            if (!Utils.order_item.getSlip().isEmpty()) {
                Picasso.with(getApplicationContext()).load(imagePath + Utils.order_item.getSlip()).into(imageToUpload);
                bUploadImage.setVisibility(View.GONE);
            } else {
                imageToUpload.setOnClickListener(this);
            }
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;

            case R.id.bUploadImage:
                if(imageToUpload.getDrawable() == null){
                    Utils.toast(getApplicationContext(),"กรุณาเพิ่มรูปภาพ");
                    imageToUpload.startAnimation(mShakeAnimation);
                }else {
                    Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
                    Log.d(TAG, "onClick: "+image.toString());
                    new UploadImage(image).execute();
                }

                break;

            case R.id.updateStatus:
                if (Utils.order_item.getStatus().equals("WC")) {
                    Ion.with(getApplicationContext())
                            .load("http://beebikebnp.com/android/Update.php")
                            .setBodyParameter("id", Utils.order_item.getDocNo())
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {

                                }
                            });

                    Ion.with(getApplicationContext())

                            .load("http://beebikebnp.com/android/SelectBeer.php")
                            .setBodyParameter("id", Utils.order_item.getDocNo())
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

                                            Ion.with(getApplicationContext())

                                                    .load("http://beebikebnp.com/android/InsertBeer.php")
                                                    .setBodyParameter("pro", a)
                                                    .setBodyParameter("qty", b)
                                                    .asString()
                                                    .setCallback(new FutureCallback<String>() {
                                                        @Override
                                                        public void onCompleted(Exception e, String result) {
                                                            finish();
                                                        }
                                                    });
                                        }

                                    } catch (JSONException ex) {

                                        ex.printStackTrace();
                                    }

                                }
                            });

                } else if (Utils.order_item.getStatus().equals("CO")) {
                }
                break;

            case R.id.finStatus:
                Ion.with(getApplicationContext())
                        .load("http://beebikebnp.com/android/Finish.php")
                        .setBodyParameter("id", Utils.order_item.getDocNo())
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                finish();
                            }
                        });
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            Log.d(TAG, "onActivityResult: " + data.getDataString());
            imageToUpload.setImageURI(selectedImage);
            Log.d(TAG, "onActivityResult: " + selectedImage);
        }
    }

    private class UploadImage extends AsyncTask<Void, Void, Void> {

        Bitmap image;

        public UploadImage(Bitmap image) {

            this.image = image;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "doInBackground: ");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("image", encodedImage));
            dataToSend.add(new BasicNameValuePair("name", Utils.order_item.getDocNo()));
            HttpParams httpRequestParams = getHttpReauestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "android/SavePicture.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d(TAG, "doInBackground: ");
                client.execute(post);
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: ");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private HttpParams getHttpReauestParams() {
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
        return httpRequestParams;
    }

    private void ShowList() {
        if(detailList.size() > 0){
            detailList.clear();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.getMainUrl() + "android/getDetail.php?docNo=" + Utils.order_item.getDocNo(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("InvoiceSub");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject prodObj = array.getJSONObject(i);
                                Log.d(TAG, "onResponse: "+prodObj.toString());
//                                Check p = new Check(prodObj.getString("docno")
//                                        , prodObj.getString("status")
//                                        ,prodObj.getString("slip")
//                                        ,prodObj.getString("dis")
//                                        ,prodObj.getString("amountall"));
                                DetailModel d = new DetailModel(prodObj.getString("ROWAUTO"),prodObj.getString("docno"),prodObj.getString("prod_name"),prodObj.getString("unit_name"),prodObj.getString("qty"),prodObj.getString("agv"),prodObj.getString("amount"));
                                detailList.add(d);
                            }

                            DetailAdapter adapter = new DetailAdapter(detailList,getApplicationContext());
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
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}