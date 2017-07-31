package com.example.barcodeshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.barcodeshop.Url.Url;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    EditText name, password, retypepassword;
    Button btnSubmit;

    String url, username;

    public EditProfile(){
        url = Url.getAbsUrl("update");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Session session = new Session(this);
        username = session.getUserUsername();

        name = (EditText) findViewById(R.id.editProfileName);
        password = (EditText) findViewById(R.id.editProfilePassword);
        retypepassword = (EditText) findViewById(R.id.editProfileRetypePassword);

        btnSubmit = (Button) findViewById(R.id.btnSaveChanges);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate()){
                    updateProfile(username);
                }
            }
        });
    }

    public boolean Validate(){
        boolean valid = true;

        String Name = name.getText().toString();
        String Password = password.getText().toString();
        String RetypePassword = retypepassword.getText().toString();

        if(!RetypePassword.equals(Password)){
            retypepassword.setError("Password does not match");
            valid = false;
        }else {
            retypepassword.setError(null);
        }

        if(Name.isEmpty()){
            name.setError("Name is required!");
            valid = false;
        }else{
            name.setError(null);
        }
        if (Password.isEmpty()){
            password.setError("Password is required!");
            valid = false;
        }else{
            password.setError(null);
        }

        return valid;
    }

    public void updateProfile(String userName){

        final ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Updating Profile...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String Name = name.getText().toString();
        String Password = password.getText().toString();

        final RequestQueue queue = Volley.newRequestQueue(EditProfile.this);
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("Username", userName);
        jsonParams.put("Name", Name);
        jsonParams.put("Password", Password);

        final JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.optBoolean("success")){
                    Toast.makeText(EditProfile.this, "Profile Updated!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfile.this, Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EditProfile.this, "Profile Can Not Updated!!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditProfile.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(postRequest);
    }


}
