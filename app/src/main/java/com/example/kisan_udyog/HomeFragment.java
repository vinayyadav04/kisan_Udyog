package com.example.kisan_udyog;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisan_udyog.login.LoginActivity;
import com.example.kisan_udyog.models.FarmerPost;
import com.example.kisan_udyog.models.User;
import com.example.kisan_udyog.sell.AddProfilePost;
import com.example.kisan_udyog.sell.ChatBot;
import com.example.kisan_udyog.sell.PostAdapterSeller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment{
    private static final String TAG = "HomeFragment";
    private ImageView chatBots,addPost;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseAuth auth ;
    FirebaseUser user;
    private String profilepic,username;
    private TextView profileName;

    RecyclerView recyclerView;
    PostAdapterSeller postAdapterseller;
    List<FarmerPost> farmPostList;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupFirebaseAuth();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.recycler_view2);
        chatBots=view.findViewById(R.id.chatBot);
        profileName=view.findViewById(R.id.profName);
        addPost=view.findViewById(R.id.addPost1);
        //image=view.findViewById(R.id.nav_account);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User post = dataSnapshot.child(user.getUid()).getValue(User.class);
                profilepic= String.valueOf(post.getProfile_photo());
                username=String.valueOf(post.getUsername());
                profileName.setText(username);

               // Picasso.with(getContext())
                  //      .load(profilepic).into(image);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });





        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        farmPostList = new ArrayList<>();
        loadPosts();

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), AddProfilePost.class);
                startActivity(intent);
            }
        });


        chatBots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), ChatBot.class);
                startActivity(intent);
            }
        });
        return view;


    }

    private void loadPosts() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Farmer_Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                farmPostList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    FarmerPost farmPostModel = ds.getValue(FarmerPost.class);
                    farmPostList.add(farmPostModel);
                    postAdapterseller = new PostAdapterSeller(getContext() , farmPostList);
                    recyclerView.setAdapter(postAdapterseller);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListner= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                checkCurrentUser(user);
                if(user!=null){
                    Log.d(TAG,"USER logged in"+ user.getUid());
                }
                else{
                    Log.d(TAG,"USER not logged in");
                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        checkCurrentUser(mAuth.getCurrentUser());
    }
    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListner);
    }

    private void checkCurrentUser(FirebaseUser user){
        if (user == null){
            Intent intent= new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }


}
