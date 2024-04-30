package com.example.kisan_udyog.buyer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kisan_udyog.R;
import com.example.kisan_udyog.models.FarmerPost;
import com.example.kisan_udyog.models.PostModel;
import com.example.kisan_udyog.sell.AddProfilePost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.ContentValues.TAG;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHolder> {

    Context context;
    List<PostModel> postModelList;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context context, List<PostModel> postModelList) {
        this.context = context;
        this.postModelList = postModelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_post , parent , false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final PostModel postModel=postModelList.get(position);

        final String title = postModelList.get(position).getpTitle();
        final String description = postModelList.get(position).getpDescription();
        final String image = postModelList.get(position).getpImage();
        final String price=postModelList.get(position).getpPrice();
        final String quantity =postModelList.get(position).getpQuantity();
        final String username=postModelList.get(position).getpUsername();
        final String profilePhoto=postModelList.get(position).getProfile_pic();
        final String city=postModelList.get(position).getCity();
        final String time=postModelList.get(position).getpTime();
        final String phoneNumber=postModelList.get(position).getPhoneNumber();
        final String postId=postModel.getpId();

        holder.postTitle.setText(title);
        holder.postDescription.setText(description);
        holder.postQuantity.setText(quantity+" kgs.");
        holder.postPrice.setText(price+ " Rs.");
        holder.postUsername.setText(username);

        Glide.with(context).load(image).into(holder.postImage);
        Glide.with(context).load(profilePhoto).into(holder.profilePic);

        isForReview(postModel.getpId(), holder.buynow);
        holder.buynow.setText("BUY NOW");
        holder.buynow.setTextColor(Color.RED);
        holder.buynow.setTag("buynow");
        holder.buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, BuyNow.class);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("image", image);
                intent.putExtra("price", price);
                intent.putExtra("quantity", quantity);
                intent.putExtra("username", username);
                intent.putExtra("profilePhoto", profilePhoto);
                intent.putExtra("city", city);
                intent.putExtra("time", time);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("pid", postId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView postImage,profilePic;
        TextView postTitle , postDescription,postPrice,postQuantity,postUsername,buynow;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.postImage);
            postTitle = itemView.findViewById(R.id.postTitle);
            postDescription = itemView.findViewById(R.id.postDescription);
            postPrice=itemView.findViewById(R.id.postPrice);
            postQuantity=itemView.findViewById(R.id.postQuantity);
            postUsername=itemView.findViewById(R.id.username);
            profilePic=itemView.findViewById(R.id.profilePic);
            buynow=itemView.findViewById(R.id.buyNow);
        }
    }


    private void isForReview(final String postid, final TextView textView){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Posts").child(postid).child("status");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status=dataSnapshot.getValue(String.class);
                Log.d(TAG,"gggg "+status);
                if(status.equals("For Review")){
                    textView.setText("FOR REVIEW");
                    textView.setTextColor(Color.BLUE);
                    textView.setTag("review");
                    textView.setEnabled(false);
                }
                else if(status.equals("Sold")){
                    textView.setText("SOLD");
                    textView.setTextColor(Color.GREEN);
                    textView.setTag("sold");
                    textView.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
