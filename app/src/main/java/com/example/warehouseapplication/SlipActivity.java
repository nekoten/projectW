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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
//"http://beebikebnp.com/android/"

public class SlipActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String SERVER_ADDRESS = "http://beebikebnp.com/";
    private static final String imagePath = "http://beebikebnp.com/android/";
    ImageView imageToUpload, downloadedImage;
    Button bUploadImage, bUpdate ,bFin;
    EditText uploadImageName, downloadImageName;
    TextView id;
    String TAG = "Ammy";
    private Animation mShakeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slip);

        imageToUpload = findViewById(R.id.imageToUpload);
        id = findViewById(R.id.orderID);
        bUploadImage = findViewById(R.id.bUploadImage);
        bUpdate = findViewById(R.id.updateStatus);
        bFin = findViewById(R.id.finStatus);

        uploadImageName = findViewById(R.id.etUploadName);

        mShakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);

        id.setText(Utils.order_item.getDocNo());

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
                    new UploadImage(image, uploadImageName.getText().toString()).execute();
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
        String dis;

        public UploadImage(Bitmap image, String dis) {
            this.image = image;
            this.dis = dis;
        }

        @Override
        protected Void doInBackground(Void... params) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("image", encodedImage));
            dataToSend.add(new BasicNameValuePair("name", Utils.order_item.getDocNo()));

//            dataToSend.add(new BasicNameValuePair("dis",dis));


            HttpParams httpRequestParams = getHttpReauestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "android/SavePicture.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
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

}