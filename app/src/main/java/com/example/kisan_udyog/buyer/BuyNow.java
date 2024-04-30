package com.example.kisan_udyog.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kisan_udyog.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class BuyNow extends AppCompatActivity {
    String title, description,image,price, quantity, username,profilepic, city,time,phoneNumber,pid;
    ImageView mImage,mProfilePic,mclose;
    TextView mtitle, mdescription,mprice, mquantity, musername, mcity,mtime,mphoneNumber,mMail;
    Button buyNow;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buynow);
        getSupportActionBar().hide();

        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.55));

        WindowManager.LayoutParams params =getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.5f;
        getWindow().setAttributes(params);



        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        image = intent.getStringExtra("image");
        price = intent.getStringExtra("price");
        quantity = intent.getStringExtra("quantity");
        username = intent.getStringExtra("username");
        profilepic = intent.getStringExtra("profilePhoto");
        city = intent.getStringExtra("city");
        time = intent.getStringExtra("time");
        phoneNumber = intent.getStringExtra("phoneNumber");
        pid = intent.getStringExtra("pid");

        mImage=findViewById(R.id.cropImg);
        mProfilePic=findViewById(R.id.sellerProfilePic);
        mtitle=findViewById(R.id.cropName);
        musername=findViewById(R.id.sellerName);
        mphoneNumber=findViewById(R.id.sellerPhoneNo);
        mMail=findViewById(R.id.sellerEmail);
        mdescription=findViewById(R.id.cropDescription);
        mcity=findViewById(R.id.sellerLocation);
        mquantity=findViewById(R.id.cropQuantity);
        mprice=findViewById(R.id.cropTotalPrice);
        mtime=findViewById(R.id.sellerTime);
        buyNow=findViewById(R.id.buynow);
        mclose=findViewById(R.id.closes);
        mtime.setText("Date Posted"+":-"+time);
        mtitle.setText(title);
        musername.setText(username);
        mphoneNumber.setText(phoneNumber);
        mdescription.setText(description);
        mcity.setText("Location"+":-"+city);
        mquantity.setText(quantity+" "+"Kgs.");
        mprice.setText(price+" "+"Rs.");
        mMail.setText("abhishek@gmail.com");

        Picasso.with(getApplicationContext()).load(image).into(mImage);
        Picasso.with(getApplicationContext()).load(profilepic).into(mProfilePic);


        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //      FirebaseDatabase.getInstance().getReference().child("review").child(pid)
               //             .child(firebaseUser.getUid()).setValue(true);

                FirebaseDatabase.getInstance().getReference().child("Posts").child(pid)
                        .child("status").setValue("For Review");

                FirebaseDatabase.getInstance().getReference().child("Posts").child(pid)
                        .child("bId").setValue(firebaseUser.getUid());

                finish();
            }
        });

        mclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
