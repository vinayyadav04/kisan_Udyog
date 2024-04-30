package com.example.kisan_udyog.sell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kisan_udyog.R;
import com.example.kisan_udyog.models.FarmerPost;
import com.example.kisan_udyog.models.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class PostAdapterSeller extends RecyclerView.Adapter<PostAdapterSeller.MyHolder> {

    private FirebaseUser firebaseUser;

    Context context;
    List<FarmerPost> FarmerPostList;

    public PostAdapterSeller(Context context, List<FarmerPost> FarmerPostList) {
        this.context = context;
        this.FarmerPostList = FarmerPostList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_home_post , parent , false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final FarmerPost farmerPost=FarmerPostList.get(position);

        String description = FarmerPostList.get(position).getfDescription();
        String image = FarmerPostList.get(position).getfImage();
        String username=FarmerPostList.get(position).getfUsername();
        String profilePhoto=FarmerPostList.get(position).getFprofile_pic();
        String like=FarmerPostList.get(position).getfLikes();

        holder.postDescription.setText(description);
        holder.likes.setText(like+" likes.");
        holder.postUsername.setText(username);

        Glide.with(context).load(image).into(holder.postImage);
        Glide.with(context).load(profilePhoto).into(holder.profilePic);

        isLiked(farmerPost.getfTime(), holder.likesimg);
        nrLikes(holder.likes, farmerPost.getfTime());
        holder.likesimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.likesimg.getTag().equals("like")) {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(farmerPost.getfTime())
                            .child(firebaseUser.getUid()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(farmerPost.getfTime())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return FarmerPostList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView postImage,profilePic,likesimg;
        TextView postDescription,postUsername,likes;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.postImage4);
            postDescription = itemView.findViewById(R.id.description2);
            postUsername=itemView.findViewById(R.id.username4);
            profilePic=itemView.findViewById(R.id.profilePic4);
            likes=itemView.findViewById(R.id.likes);
            likesimg=itemView.findViewById(R.id.likesimg);

        }
    }

    private void nrLikes(final TextView likes, String postId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                likes.setText(dataSnapshot.getChildrenCount()+" likes");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void isLiked(final String postid, final ImageView imageView){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_liked_foreground);
                    imageView.setTag("liked");
                } else{
                    imageView.setImageResource(R.drawable.ic_like_foreground);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
