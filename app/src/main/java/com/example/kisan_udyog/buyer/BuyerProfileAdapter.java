package com.example.kisan_udyog.buyer;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kisan_udyog.R;
import com.example.kisan_udyog.models.PostModel;
import com.example.kisan_udyog.sell.ProfileAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class BuyerProfileAdapter extends RecyclerView.Adapter<BuyerProfileAdapter.MyHolder> {
    Context context;
    List<PostModel> postModelList;
    private FirebaseAuth mAuth;
    private static final String TAG = "BuyerProfileAdapter";
    private LinearLayout layout;
    LinearLayout.LayoutParams params;


    public BuyerProfileAdapter(Context context, List<PostModel> postModelList) {
        this.context = context;
        this.postModelList = postModelList;
    }

    @NonNull
    @Override
    public BuyerProfileAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_buyer_recycler , viewGroup , false);
        return new BuyerProfileAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyerProfileAdapter.MyHolder holder, int position) {
        String title = postModelList.get(position).getpTitle();
        final String bid=postModelList.get(position).getbId();
        String status=postModelList.get(position).getStatus();
        String prices=postModelList.get(position).getpPrice();
        String quants=postModelList.get(position).getpQuantity();
        String dates=postModelList.get(position).getpTime();
        final String pId=postModelList.get(position).getpId();

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String s=user.getUid();

        if(bid.equals(s)){
            holder.texts.setText(title+".");
            holder.date.setText(dates);
            holder.price.setText(prices+" "+"Rs.");
            holder.quant.setText(quants+" "+"Kgs.");


            if(status.equals("For Review")){
                holder.stat.setText("PAY and BUY");
                holder.stat.setTextColor(Color.RED);
                int color1 = Color.argb(100, 255, 230, 230);
                holder.itemView.setBackgroundColor(color1);
                holder.stat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      /*  DatabaseReference reference;
                        reference = FirebaseDatabase.getInstance().getReference("Posts");
                        reference.orderByChild("bId").equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                    String keys = childSnapshot.getKey();
                                    Log.d(TAG,"PARENTE 3"+ keys);
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/

                        FirebaseDatabase.getInstance().getReference().child("Posts").child(pId)
                                .child("status").setValue("Sold");
                    }
                });
            }
            else if(status.equals("Sold")){
                holder.stat.setText("Payment Done");
                int color2 = Color.argb(100, 230, 255, 230);
                holder.itemView.setBackgroundColor(color2);
            }

        }
        else{
            params.height = 0;
            holder.itemView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{

        //ImageView postImage,profilePic;
        TextView texts,stat,quant,price,date;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            texts = itemView.findViewById(R.id.ksd);
            stat=itemView.findViewById(R.id.ddr);
            quant = itemView.findViewById(R.id.quant1);
            price=itemView.findViewById(R.id.price1);
            date=itemView.findViewById(R.id.dateTime);
            layout =(LinearLayout)itemView.findViewById(R.id.ll1);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

        }
    }
    private void Layout_hide() {
        params.height = 0;
        //itemView.setLayoutParams(params); //This One.
        layout.setLayoutParams(params);   //Or This one.

    }
}
