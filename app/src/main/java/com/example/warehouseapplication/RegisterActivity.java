package com.example.warehouseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class RegisterActivity extends AppCompatActivity {

    EditText username, userpassword, name, lastname, usertelephone, useraddress;
    Button btnRegister;

    private static final String registerUrl = "http://beebikebnp.com/android/Regis.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.regUser);
        userpassword = findViewById(R.id.regPassword);
        name = findViewById(R.id.regName);
        lastname = findViewById(R.id.regLastname);
        usertelephone = findViewById(R.id.regTelephone);
        useraddress = findViewById(R.id.regAddress);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registernewuser();
            }
        });
    }

    private void registernewuser() {

        Ion.with(RegisterActivity.this)

                .load(registerUrl)
                .setBodyParameter("usname", username.getText().toString().trim())
                .setBodyParameter("pass", userpassword.getText().toString().trim())
                .setBodyParameter("name", name.getText().toString().trim())
                .setBodyParameter("lname", lastname.getText().toString().trim())
                .setBodyParameter("tel", usertelephone.getText().toString().trim())
                .setBodyParameter("address", useraddress.getText().toString().trim())
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        Toast.makeText(getApplicationContext(),"สมัครสมาชิกเรียบร้อย", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        /*StringRequest request = new StringRequest(Request.Method.POST, registerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response);
                if (response.contains("success")) {
                    Toast.makeText(getApplicationContext(),"Successfully Register User", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Error Registering New User", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("registeruser", "true");
                params.put("username", username.getText().toString().trim());
                params.put("userpassword", userpassword.getText().toString().trim());
                params.put("name", name.getText().toString());
                params.put("telephone", usertelephone.getText().toString());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);*/

    }

}
