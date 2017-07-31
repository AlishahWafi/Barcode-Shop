package com.example.barcodeshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText txtName, txtUsername, txtPassword, txtPhone;
    Button btnReg;

    private String url;

    public Register() {
        // Required empty public constructor
        url = Url.getAbsUrl("register");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtName = (EditText)findViewById(R.id.txtRegName);
        txtUsername = (EditText)findViewById(R.id.txtRegUserName);
        txtPassword = (EditText)findViewById(R.id.txtRegPassword);
        txtPhone = (EditText)findViewById(R.id.txtRegPhone);

        btnReg = (Button) findViewById(R.id.btnRegister);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    Signup();
                }
            }
        });

    }

    public void Signup(){

        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Signing in...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        final RequestQueue queue = Volley.newRequestQueue(this);

        String name = txtName.getText().toString();
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String phone = txtPhone.getText().toString();

        //used strings to send parameters
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("Name", name);
        jsonParams.put("Username", username);
        jsonParams.put("Password", password);
        jsonParams.put("PhoneNumber", phone);
        jsonParams.put("Points", "0");

        //sending JsonObjectRequest to server address, with parameters
        final JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Get response here and then logic after sucess login
                        if (response.optBoolean("success")) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getBaseContext(), "Username or Phone is already exist.", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getBaseContext(), "Server Error", Toast.LENGTH_LONG).show();
                    }
                }) {
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

    public boolean validate() {
        boolean valid = true;

        String name = txtName.getText().toString();
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String phone = txtPhone.getText().toString();

        if (name.isEmpty()) {
            txtName.setError("Name is require!");
            valid = false;
        } else {
            txtName.setError(null);
        }

        if (username.isEmpty()) {
            txtUsername.setError("Username is require!");
            valid = false;
        } else {
            txtUsername.setError(null);
        }

        if (password.isEmpty()) {
            txtPassword.setError("Password is require!");
            valid = false;
        } else {
            txtPassword.setError(null);
        }

        if (phone.isEmpty()) {
            txtPhone.setError("Phone Number is require!");
            valid = false;
        } else {
            txtPhone.setError(null);
        }
        return valid;
    }
}
