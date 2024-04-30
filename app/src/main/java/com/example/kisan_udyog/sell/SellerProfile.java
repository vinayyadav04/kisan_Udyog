package com.example.kisan_udyog.sell;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kisan_udyog.R;

public class SellerProfile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
    }
}
