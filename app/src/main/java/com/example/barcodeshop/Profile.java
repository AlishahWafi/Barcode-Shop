package com.example.barcodeshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    Button btnEdit;
    TextView name, username, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnEdit = (Button)findViewById(R.id.btnProfileEdit);
        name = (TextView)findViewById(R.id.profileName);
        username = (TextView)findViewById(R.id.profileUserName);
        phone = (TextView)findViewById(R.id.profilePhoneNumber);

        Session session = new Session(this);

        name.setText(session.getUserName());
        username.setText(session.getUserUsername());
        phone.setText(session.getUserPhone());


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);
            }
        });
    }
}
