package com.example.kisan_udyog.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisan_udyog.MainActivity;
import com.example.kisan_udyog.R;

import com.example.kisan_udyog.buyer.HomeBuyer;
import com.example.kisan_udyog.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Context mContext;
    private EditText mEmail, mPassword;
    private TextView mRegisterHere;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private DatabaseReference rootRef;
    private String userType;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acttivity_login);
        getSupportActionBar().hide();

        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mContext = LoginActivity.this;

        mAuth = FirebaseAuth.getInstance();
        //setupFirebaseAuth();
        init();

    }
    private boolean isStringNull(String string){
        if(string.equals("")){
            return true;
        }
        else {
            return false;
        }
    }

//----------------------------------------getting type of user--------------------------------------


    private void init(){
        Button btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String email= mEmail.getText().toString();
                String password= mPassword.getText().toString();
                if(isStringNull(email) && isStringNull(password)){
                    Toast.makeText(mContext, "you must fill all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            userType=setups();
                            if(!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "UNSUCCESSFUL",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                try {
                                    if(user.isEmailVerified() ){
                                        if(userType.equals("BUYER")){
                                            Intent intent = new Intent(LoginActivity.this, HomeBuyer.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            finish();
                                            startActivity(intent);
                                        }
                                        else if (userType.equals("FARMER")){
                                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            finish();
                                            startActivity(intent);
                                        }
                                    }

                                    else{
                                        Toast.makeText(mContext, "Email is not verified ", Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();
                                    }
                                }
                                catch (NullPointerException e){
                                    Log.e(TAG, "oncompete: NulPointer exception"+e.getMessage());
                                }
                            }
                        }
                    });
                }
            }
        });



        mRegisterHere = findViewById(R.id.register);
        mRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    private String  setups(){
        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
            rootRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userType = dataSnapshot.getValue(User.class).getRole();
                    Toast.makeText(LoginActivity.this, "LOGGED AS "+ userType, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        return userType;
    }



  /*  private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase= FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                }
                else{
                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }*/
}