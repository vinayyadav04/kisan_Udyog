package com.example.kisan_udyog.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import com.example.kisan_udyog.R;
import com.example.kisan_udyog.models.User;
import com.example.kisan_udyog.models.UserSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class FirebaseMethods {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;
    private Context mContext;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();

        mContext = context;
        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }







    public void addNewUser(String email, String username, String city, double latitude,double longitude,long phone_number,String profile_photo,String usertype) {
        User user = new User(email, username, city, latitude, longitude, phone_number, userID,profile_photo,usertype);
        myRef.child(mContext.getString(R.string.dbname_user)).child(userID).setValue(user);

    }


    public void registerNewEmail(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(mContext, "authentication failed", Toast.LENGTH_SHORT).show();
                        } else if (task.isSuccessful()) {
                            sendVerificationEmail();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:success", task.getException());
                            userID = mAuth.getCurrentUser().getUid();
                        }
                    }

                }); }





    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                    }
                    else{
                        Toast.makeText(mContext, "could'nt send email verification", Toast.LENGTH_SHORT).show();
                    }
                }
            } );
        }
    }






    public UserSettings getUserSettings(DataSnapshot dataSnapshot){
        Log.d(TAG, "getUserAccountSettings: retrieving user account settings from firebase.");

        User user = new User();

        for(DataSnapshot ds: dataSnapshot.getChildren()){

            // users node
            if(ds.getKey().equals(mContext.getString(R.string.dbname_user))) {
                Log.d(TAG, "getUserAccountSettings: datasnapshot: " + ds);

                user.setUsername(
                        ds.child(userID)
                                .getValue(User.class)
                                .getUsername()
                );
                user.setEmail(
                        ds.child(userID)
                                .getValue(User.class)
                                .getEmail()
                );
                user.setPhone_number(
                        ds.child(userID)
                                .getValue(User.class)
                                .getPhone_number()
                );
                user.setU_id(
                        ds.child(userID)
                                .getValue(User.class)
                                .getU_id()
                );
                user.setLatitude(
                        ds.child(userID)
                                .getValue(User.class).getLatitude()
                );
                user.setLongitude(
                        ds.child(userID)
                                .getValue(User.class)
                                .getLongitude()
                );
                user.setCity(
                        ds.child(userID)
                                .getValue(User.class)
                                .getCity()
                );
                user.setRole(
                        ds.child(userID)
                                .getValue(User.class)
                                .getRole()
                );


                Log.d(TAG, "getUserAccountSettings: retrieved users information: " + user.toString());
            }

        }
        return new UserSettings(user);

    }

}
