package com.example.kisan_udyog.sell;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kisan_udyog.R;
import com.example.kisan_udyog.models.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyHolder> {
    Context context;
    List<PostModel> postModelList;
    private FirebaseAuth mAuth;
    private static final String TAG = "ProfileAdapter";
    private LinearLayout layout;
    LinearLayout.LayoutParams params;

    public ProfileAdapter(Context context, List<PostModel> postModelList) {
        this.context = context;
        this.postModelList = postModelList;
    }

    @NonNull
    @Override
    public ProfileAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_profile_recyclerview , viewGroup , false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.MyHolder holder, int position) {
        String title = postModelList.get(position).getpTitle();
        String uid=postModelList.get(position).getUid();
        String status=postModelList.get(position).getStatus();
        String prices=postModelList.get(position).getpPrice();
        String quants=postModelList.get(position).getpQuantity();
        String dates=postModelList.get(position).getpTime();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String s=user.getUid();

        if(uid.equals(s)){
            holder.texts.setText(title+".");
            holder.date.setText(dates);
            holder.price.setText(prices+" "+"Rs.");
            holder.quant.setText(quants+" "+"Kgs.");


            if(status.equals("Not Sold")){
                holder.stat.setText("Not Sold");
                holder.stat.setTextColor(Color.RED);
                int color1 = Color.argb(100, 255, 230, 230);
                holder.itemView.setBackgroundColor(color1);
            }
            else if(status.equals("Sold")){
                holder.stat.setText("Sold");
                int color2 = Color.argb(100, 230, 255, 230);
                holder.itemView.setBackgroundColor(color2);
            }
            else if(status.equals("For Review")){
                holder.stat.setText("For Review");
                holder.stat.setTextColor(Color.BLUE);
                int color3 = Color.argb(100, 230, 249, 255);
                holder.itemView.setBackgroundColor(color3);
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
