package com.example.barcodeshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.barcodeshop.Url.Url;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private TextView register;
    private EditText username, password;
    private Button btnlogin;
    private Session session;
    private String url;
    RelativeLayout relativeLayout;


    ProgressBar progressBar;
    LinearLayout linear, linearloginProgress1;

    public Login()
    {
        url = Url.getAbsUrl("login");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");
        session = new Session(this);
        register = (TextView) findViewById(R.id.txtRegister);
        username = (EditText)findViewById(R.id.txtLoginUsername);
        password = (EditText) findViewById(R.id.txtLoginPassword);
        btnlogin = (Button) findViewById(R.id.btnLogin);

        relativeLayout = (RelativeLayout) findViewById(R.id.login);
        linear = (LinearLayout) findViewById(R.id.linearLayoutLogin);
        linearloginProgress1 = (LinearLayout) findViewById(R.id.linearloginProgress);
        progressBar = (ProgressBar) findViewById(R.id.loginprogressBar);

        if(session.loggedin())
        {
            startActivity(new Intent(Login.this,Home.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    linear.setVisibility(View.INVISIBLE);
                    linearloginProgress1.setVisibility(View.VISIBLE);
                    login();
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String txtusername = username.getText().toString();
        String txtpassword = password.getText().toString();

        if (txtusername.isEmpty()) {
            username.setError("Username is require!");
            valid = false;
        } else {
            username.setError(null);
        }

        if (txtpassword.isEmpty()) {
            password.setError("Password is require!");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }

    public void login() {

        /*final ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Signing in...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();*/

        final RequestQueue queue = Volley.newRequestQueue(this);

        final String txtusername = username.getText().toString();
        final String txtpassword = password.getText().toString();

        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("Username", txtusername);
        jsonParams.put("Password", txtpassword);

        final JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Get response here and then logic after sucess login

                        System.out.println("Data : "+response);
                        if (response != null) {

                            try {
                                int CustomerID = response.getInt("Customer_Id");
                                String Name = response.getString("Name");
                                String Username = response.getString("Username");
                                String Phone = response.getString("PhoneNumber");
                                String Image = response.getString("Image");
                                session.setLoggedin(true, String.valueOf(CustomerID), Name, Username, Phone, Image);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(Login.this, Home.class);
                            //progressDialog.dismiss();
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(intent);
                            finish();

                        } else {

                            //progressDialog.dismiss();
                            linear.setVisibility(View.VISIBLE);
                            linearloginProgress1.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getBaseContext(), "Incorrect Username or Password!", Toast.LENGTH_LONG).show();
                            Snackbar.make(relativeLayout, "Incorrect Username or Password", Snackbar.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //progressDialog.dismiss();
                        linearloginProgress1.setVisibility(View.INVISIBLE);
                        linear.setVisibility(View.VISIBLE);
                        Snackbar.make(relativeLayout, "Server Error", Snackbar.LENGTH_LONG).show();
                    }
                })   {
            //defining header here...
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        queue.add(postRequest);

        //retry connection
        postRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

}
