package com.example.kisan_udyog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.kisan_udyog.buyer.HomeBuyer;
import com.example.kisan_udyog.login.LoginActivity;
import com.example.kisan_udyog.models.User;
import com.example.kisan_udyog.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    DatabaseReference reference, rootRef;
    private String userType;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        getSupportActionBar().hide();
        userType=setups();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             try {
                 if (userType.equals("FARMER")) {
                     Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     finish();
                     startActivity(intent);
                 } else if (userType.equals("BUYER")) {
                     Intent intent = new Intent(SplashScreen.this, HomeBuyer.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     finish();
                     startActivity(intent);
                 }
             }
             catch(Exception error1){
                 Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 finish();
                 startActivity(intent);
             }

            }
        }, 5000);
    }



    private String setups(){
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
            rootRef = FirebaseDatabase.getInstance().getReference();
            reference = rootRef.child("users").child(userID);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userType = dataSnapshot.getValue(User.class).getRole();
                    Toast.makeText(SplashScreen.this, "LOGGED AS "+ userType, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        return userType;
    }
}