package com.example.kisan_udyog.gridview;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisan_udyog.R;
import com.example.kisan_udyog.models.DataModel;
import com.example.kisan_udyog.sell.SellActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<DataModel> {

    // constructor for our list view adapter.
    public GridViewAdapter(@NonNull Context context, ArrayList<DataModel> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_gridview_layout, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        final DataModel dataModel = getItem(position);

        // initializing our UI components of list view item.
      //  TextView nameTV = listitemView.findViewById(R.id.idTVtext);
        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
       // nameTV.setText(dataModel.getName());

        // in below line we are using Picasso to load image
        // from URL in our Image VIew.
        Picasso.with(getContext()).load(dataModel.getImgUrl()).into(courseIV);


        // below line is use to add item
        // click listener for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Toast.makeText(getContext(), "Item clicked is : " + dataModel.getName(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), SellActivity.class);
                //finish();
                intent.putExtra("ghj", dataModel.getName());
                intent.putStringArrayListExtra("type", dataModel.getType());
                intent.putExtra("description", dataModel.getDescription());
                Log.d("GridViewAdapter","TYPES"+dataModel.getType());
                getContext().startActivity(intent);
            }
        });
        return listitemView;
    }
}