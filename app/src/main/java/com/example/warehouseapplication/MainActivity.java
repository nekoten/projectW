package com.example.warehouseapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouseapplication.Model.User;
import com.example.warehouseapplication.Tool.Utils;
import com.example.warehouseapplication.utils.MessageHandler;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private UserTABLE objUserTABLE;
    private OrderTABLE objOrderTABLE;
    private EditText edtUser, edtPassword;
    private String strUserChoose, strPasswordchoose, strPasswordTrue, strName;
    private FoodTABLE objFoodTABLE;
    String TAG ="Login";
    TextView signup;

    EditText username, userpassword;
    Button btnLogin;
    Spinner _spinner;

    public static String getMainUrl() {
        return mainUrl;
    }

    //    private static final String LoginUrl = "http://beebikebnp.com/android/login.php";
    private static final String mainUrl = "http://beebikebnp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.txtUsername);
        userpassword = findViewById(R.id.txtPassword);
        signup = findViewById(R.id.usersignup);
        btnLogin = findViewById(R.id.btnLogin);
        _spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.usertype, R.layout.support_simple_spinner_dropdown_item);
        _spinner.setAdapter(adapter);

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String item = _spinner.getSelectedItem().toString();
//                if (username.getText().toString().equals("admin") && userpassword.getText().toString().equals("admin") && item.equals("admin")) {
//                    Intent intent = new Intent(MainActivity.this, admin.class);
//                    startActivity(intent);
//                } else if (username.getText().toString().equals("admin") && userpassword.getText().toString().equals("admin") && item.equals("user")) {
//                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(), "ชื่อผู้ใช้ หรือ รหัสผ่าน ไม่ถูกต้อง!!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Click!!!: ");
               userLogin();
           }
        });

        //Bind Widget
        // bindWidget();
        
        //objUserTABLE = new UserTABLE(this);
        //objOrderTABLE = new OrderTABLE(this);
        //objFoodTABLE = new FoodTABLE(this);

        //Tester
        //testAddValue();

        //delete All Data
        //deleteAllData();

        //SynJSonToSQLite
        //synJSonToSQLite();
        
    }   //onCreate

    private void userLogin() {
        Log.d(TAG, "shot: "+mainUrl + "android/login.php");
        StringRequest request = new StringRequest(StringRequest.Method.POST, mainUrl + "android/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    User user = MessageHandler.deSerializeMessage(response,User.class);
                    Utils.user = user;
                    Log.d(TAG, "onResponse: "+ response);

                    Log.d(TAG, "onResponse_type: " + Utils.user.getUsername());
                    Log.d(TAG, "onResponse_model: "+ user);

                    Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("firstname", user.getFirstname());
                    intent.putExtra("idUser", user.getId_member());
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "เข้าสู่ระบบเสำเร็จ", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "ชื่อผู้ใช้ หรือ รหัสผ่าน ไม่ถูกต้อง!!", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d(TAG, "getParams: ");
                Map<String, String> params = new HashMap<>();
                params.put("loginuser", "true");
//                params.put("username", username.getText().toString().trim());
//                params.put("userpassword", userpassword.getText().toString().trim());
                params.put("username", "poon");
                params.put("userpassword", "094082");
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);

    }

    public void clickLogin(View view){

        strUserChoose = edtUser.getText().toString().trim();
        strPasswordchoose = edtPassword.getText().toString().trim();

        if(strUserChoose.equals("") || strPasswordchoose.equals("") ){

            //Alert Error
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.errorDialog(MainActivity.this, "มีช่องว่าง", "กรุณากรอกให้เรียบร้อย");

        }else {

            checkUser();

        }

    }   // clickLogin

    private void checkUser() {

        try {

            String strData[] = objUserTABLE.searchUser(strUserChoose);
            strPasswordTrue = strData[2];
            strName = strData[3];

            Log.d("WH", "Welcome " + strName);

            checkPassword();

        }   catch (Exception e){
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.errorDialog(MainActivity.this, "No This User", "No " + strUserChoose + " in my Database");
        }

    }   // ChechUser

    private void checkPassword() {

        if(strPasswordchoose.equals(strPasswordTrue)){

            //Intent to Order Activity
            welcomeUser();

        }else{

            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.errorDialog(MainActivity.this, "Password False", "Please Try again Password False");

        }

    }   //checkPassword

    private void welcomeUser() {

        AlertDialog.Builder objAlert = new AlertDialog.Builder(this);
        objAlert.setIcon(R.drawable.icon_user);
        objAlert.setTitle("Welcome to Warehouse");
        objAlert.setMessage("Welcome " + strName + "\n" + "To Warehouse");
        objAlert.setCancelable(false);
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent objIntent = new Intent(MainActivity.this, OrderActivity.class);
                objIntent.putExtra("Name", strName);
                startActivity(objIntent);
                finish();

            }
        });
        objAlert.show();

    }   // welcomeUser

    private void bindWidget() {
        edtUser = (EditText) findViewById(R.id.txtUsername);
        edtPassword = (EditText) findViewById(R.id.txtPassword);
    }   //bindWidget

    private void deleteAllData() {
        SQLiteDatabase objSQLite = openOrCreateDatabase("Warehouse.db", MODE_PRIVATE, null);
        objSQLite.delete("userTABLE", null, null);
        objSQLite.delete("foodTABLE", null, null);

    }   // deleteAllData

    private void synJSonToSQLite() {

        //setUp Policy
        if(Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);
        }   //if

        InputStream objInputStream = null;
        String strJSON = mainUrl, GET_URL = "android/php_get_user_data.php";

        //Create objInputStream
        try {


//           HttpClient objHttpClient = new DefaultHttpClient();
//           HttpPost objHttpPost = new HttpPost("http://10.0.2.2/", GET_URL = "android/php_get_user_data.php");
//           HttpResponse  objHttpResponse = objHttpClient.execute(objHttpPost);
//           HttpEntity objHttpEntity = objHttpResponse.getEntity();
//           objInputStream = objHttpEntity.getContent();


        } catch (Exception e){
            Log.d("WH","Error from InputStream ==> " + e.toString());
        }


        //Change InputStream to String
        try{

            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder objStringBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = objBufferedReader.readLine()) != null ){
                    objStringBuilder.append(strLine);
            }   //while

            objInputStream.close();
            strJSON = objStringBuilder.toString();

        } catch (Exception e){
            Log.d("WH","Error Create String ==> " + e.toString());
        }


        //Up Value to SQLite
        try{

            final JSONArray objJSONArray = new JSONArray(strJSON);
            for(int i = 0; i < objJSONArray.length(); i++){

                JSONObject objJSONObject = objJSONArray.getJSONObject(i);
                String strUser = objJSONObject.getString("User");
                String strPassword = objJSONObject.getString("Password");
                String strOfficer = objJSONObject.getString("Officer");

                long insertValue = objUserTABLE.addValueToUser(strUser, strPassword, strOfficer);
            }

        }catch (Exception e){
            Log.d("WH","Error Up Value ==> " + e.toString());
        }


    }   //synJSonToSQLite

    private void testAddValue() {

        //objUserTABLE.addValueToUser("admin1", "admin1", "admin1");
        //objOrderTABLE.addValueToOrder("Officer", "Orders", 4);
        objFoodTABLE.addValueToFood("หมึกไข่ (XL)", "240");

    }   //testAddValue
}
