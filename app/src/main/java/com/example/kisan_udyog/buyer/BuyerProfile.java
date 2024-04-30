package com.example.kisan_udyog.buyer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisan_udyog.MainActivity;
import com.example.kisan_udyog.R;
import com.example.kisan_udyog.login.LoginActivity;
import com.example.kisan_udyog.models.PostModel;
import com.example.kisan_udyog.models.User;
import com.example.kisan_udyog.sell.ProfileAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BuyerProfile extends AppCompatActivity {
    RecyclerView recyclerView;
    BuyerProfileAdapter buyerProfileAdapter;
    List<PostModel> postModelList;
    private Context mContext;

    String username,email,profilepic,phoneNumber;
    TextView mUsername,mPhonenumber,mEmail,mSignout;
    ImageView mProfilepic;

    FirebaseAuth auth ;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_profile);
        getSupportActionBar().hide();
        mContext = BuyerProfile.this;
        recyclerView = findViewById(R.id.recycler344);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        mEmail= findViewById(R.id.email1);
        mUsername= findViewById(R.id.username1);
        mPhonenumber= findViewById(R.id.phonenumber1);
        mProfilepic= findViewById(R.id.image_profile1);

        recyclerView.setLayoutManager(layoutManager);

        postModelList = new ArrayList<>();

        loadPosts();


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User post = dataSnapshot.child(user.getUid()).getValue(User.class);
                profilepic= String.valueOf(post.getProfile_photo());
                phoneNumber=String.valueOf(post.getPhone_number());
                username=String.valueOf(post.getUsername());
                email=String.valueOf(post.getEmail());
                mUsername.setText(username);
                mPhonenumber.setText(phoneNumber);
                mEmail.setText(email);
                Picasso.with(mContext)
                        .load(profilepic).into(mProfilepic);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    private void loadPosts() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postModelList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    PostModel postModel = ds.getValue(PostModel.class);
                    postModelList.add(postModel);
                    buyerProfileAdapter = new BuyerProfileAdapter(mContext , postModelList);
                    recyclerView.setAdapter(buyerProfileAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(TAG, "" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
