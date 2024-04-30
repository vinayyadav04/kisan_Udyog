package com.example.kisan_udyog.sell;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisan_udyog.R;
import com.example.kisan_udyog.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class  AddPostActivity extends Activity {
    private static final String TAG = "AddPostActivity";
    EditText title_blog , description_blog ;
    Button upload ;
    ImageView blog_image,profImages,close ;
    TextView mquant,mpricetot;
    String latitude,longitude,type,total,quantity,username,profileImages,phoneNumber,city;
    Uri image_uri = null ;
    private static final  int GALLERY_IMAGE_CODE = 100 ;
    private static final  int CAMERA_IMAGE_CODE = 200 ;
    ProgressDialog pd ;
    FirebaseAuth auth ;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.78));

        WindowManager.LayoutParams params =getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.5f;
        getWindow().setAttributes(params);

        close=findViewById(R.id.close1);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title_blog = findViewById(R.id.title_blog);
        description_blog = findViewById(R.id.description_blog);
        upload = findViewById(R.id.upload);
        blog_image = findViewById(R.id.post_image_blog);
        profImages=findViewById(R.id.profilePic);

        mquant=findViewById(R.id.quant);
        mpricetot=findViewById(R.id.total);
        Intent intent = getIntent();
        type = intent.getStringExtra("typeName");
        quantity = intent.getStringExtra("typePrice");
        title_blog.setText(type);
        mquant.setText(quantity);



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User post = dataSnapshot.child(user.getUid()).getValue(User.class);
                latitude= String.valueOf(post.getLatitude());
                longitude= String.valueOf(post.getLongitude());
                username=String.valueOf(post.getUsername());
                phoneNumber=String.valueOf(post.getPhone_number());
                city=String.valueOf(post.getCity());

                profileImages=String.valueOf(post.getProfile_photo());
                Log.d(TAG,"USERNAME ISS "+ username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });

        pd = new ProgressDialog(this);

        blog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_blog.getText().toString();
                String description = description_blog.getText().toString();
                total=String.valueOf(Integer.parseInt(quantity)*Integer.parseInt(mpricetot.getText().toString()));
                if (TextUtils.isEmpty(title)){
                    title_blog.setError("Title is required");
                }
                else if (TextUtils.isEmpty(description)){
                    description_blog.setError("Description is required");
                }
                else {
                    uploadData(title , description);
                }
            }
        });

    }

    private void uploadData(final String title, final String description) {

        pd.setMessage("Publising post");
        pd.show();
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        final String timeStamps = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        final String filepath = "Posts/"+"post_"+timeStamp;

        if (blog_image.getDrawable() != null){
            Bitmap bitmap = ((BitmapDrawable)blog_image.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG , 100 , baos);
            byte[] data = baos.toByteArray();

            final StorageReference reference = FirebaseStorage.getInstance().getReference().child(filepath);
            UploadTask uploadTask = reference.putBytes(data);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                throw task.getException();
                }
                    Log.d(TAG,"URL IS  "+ reference.getDownloadUrl());

                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUril = task.getResult();
                        //Log.d(TAG,"URLS IS  "+ downloadUril);
                        String downloadUri=downloadUril.toString();
                        HashMap<String , Object> hashMap = new HashMap<>();
                        hashMap.put("profile_pic",profileImages);
                        hashMap.put("uid" , user.getUid());
                        hashMap.put("pQuantity" , quantity);
                        hashMap.put("pPrice" , total);
                        hashMap.put("pLatitude" , latitude);
                        hashMap.put("pUsername" , username);
                        hashMap.put("pLongitude" , longitude);
                        hashMap.put("uEmail" , user.getEmail());
                        hashMap.put("pId" , timeStamp);
                        hashMap.put("city" , city);
                        hashMap.put("pTitle" , title);
                        hashMap.put("phoneNumber" , phoneNumber);
                        hashMap.put("pImage" , downloadUri);
                        hashMap.put("pDescription" , description);
                        hashMap.put("pTime" ,  timeStamps);
                        hashMap.put("status", "Not Sold");
                        hashMap.put("bId", "");

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                        ref.child(timeStamp).setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        pd.dismiss();
                                        finish();
                                        Toast.makeText(AddPostActivity.this, "Post Published", Toast.LENGTH_SHORT).show();
                                        title_blog.setText("");
                                        description_blog.setText("");
                                        blog_image.setImageURI(null);
                                        image_uri = null ;
                                    }
                                });
                    } else {
                    }
                }
            });
        }
    }

    private void imagePickDialog() {
        //here 0 is for camera and 1 is for gallery so please do it like me
        String[] options = {"Camera" , "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose image from");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    cameraPick();
                }
                if (which == 1){
                    galleryPick();

                }
            }
        });

        builder.create().show();
    }

    private void cameraPick() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE , "Temp Pick");
        contentValues.put(MediaStore.Images.Media.TITLE , "Temp desc");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT , image_uri);
        startActivityForResult(intent , CAMERA_IMAGE_CODE);
    }

    private void galleryPick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent , GALLERY_IMAGE_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == GALLERY_IMAGE_CODE){
                image_uri = data.getData();
                blog_image.setImageURI(image_uri);
            }
            if (requestCode == CAMERA_IMAGE_CODE){
                blog_image.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}