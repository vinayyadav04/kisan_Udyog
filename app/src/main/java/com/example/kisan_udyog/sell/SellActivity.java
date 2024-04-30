package com.example.kisan_udyog.sell;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.kisan_udyog.R;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public class SellActivity extends AppCompatActivity {
    private static final String TAG = "SellActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_price);
        getSupportActionBar().hide();


        final ArrayList<String> mobileArray = (ArrayList<String>) getIntent().getSerializableExtra("type");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_sell_price_adapter, mobileArray);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);


       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String[] arr =splitString(mobileArray.get(i));
               Log.d("SellActivity", "INEGER"+arr[0]);
               Intent intent = new Intent(SellActivity.this,AddPostActivity.class);
               intent.putExtra("typeName", arr[0]);
               intent.putExtra("typePrice", arr[1]);
               startActivity(intent);
           }
       });
    }


    private String[] splitString(String s){
        String typeName=s;
        String typePrice=s;
        typeName = typeName.replaceAll("[0-9]","");
        typePrice=typePrice.replaceAll("[A-Za-z\\s]","");
        return new String[] {typeName, typePrice};
    }
}