package com.example.kisan_udyog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisan_udyog.buyer.HomeBuyer;
import com.example.kisan_udyog.buyer.PostAdapter;
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


public class ProfileFragment extends Fragment {

    RecyclerView recyclerView;
    ProfileAdapter profileAdapter;
    List<PostModel> postModelList;
    String username,email,profilepic,phoneNumber;
    TextView mUsername,mPhonenumber,mEmail,mSignout;
    ImageView  mProfilepic;

    FirebaseAuth auth ;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        recyclerView = view.findViewById(R.id.recycler34);
        mEmail= view.findViewById(R.id.email1);
        mUsername= view.findViewById(R.id.username1);
        mPhonenumber= view.findViewById(R.id.phonenumber1);
        mProfilepic= view.findViewById(R.id.image_profile1);
        mSignout=view.findViewById(R.id.signoutfarm);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

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
                Picasso.with(getContext())
                      .load(profilepic).into(mProfilepic);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth = FirebaseAuth.getInstance();
                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();
                auth.signOut();
                startActivity(intent);
            }
        });
        return view;
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
                    profileAdapter = new ProfileAdapter(getContext() , postModelList);
                    recyclerView.setAdapter(profileAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}