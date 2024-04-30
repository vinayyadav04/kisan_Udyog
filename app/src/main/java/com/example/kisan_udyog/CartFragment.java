package com.example.kisan_udyog;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.example.kisan_udyog.gridview.GridViewAdapter;
import com.example.kisan_udyog.models.DataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment{
    GridView coursesGV;
    ArrayList<DataModel> dataModelArrayList;
    FirebaseFirestore db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sell_gridview, container, false);
            // below line is use to initialize our variables.
            coursesGV = view.findViewById(R.id.idGVCourses);
            dataModelArrayList = new ArrayList<>();

            // initializing our variable for firebase
            // firestore and getting its instance.
            db = FirebaseFirestore.getInstance();

            // here we are calling a method
            // to load data in our list view.
            loadDatainGridView();

            return view;
        }

        private void loadDatainGridView() {
            // below line is use to get data from Firebase
            // firestore using collection in android.
            db.collection("Data").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            // after getting the data we are calling on success method
                            // and inside this method we are checking if the received
                            // query snapshot is empty or not.
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // if the snapshot is not empty we are hiding our
                                // progress bar and adding our data in a list.
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {

                                    // after getting this list we are passing
                                    // that list to our object class.
                                    DataModel dataModel = d.toObject(DataModel.class);

                                    // after getting data from Firebase
                                    // we are storing that data in our array list
                                    dataModelArrayList.add(dataModel);
                                }
                                // after that we are passing our array list to our adapter class.
                                GridViewAdapter adapter = new GridViewAdapter(getActivity(), dataModelArrayList);

                                // after passing this array list
                                // to our adapter class we are setting
                                // our adapter to our list view.
                                coursesGV.setAdapter(adapter);
                            } else {
                                // if the snapshot is empty we are displaying a toast message.
                                Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // we are displaying a toast message
                    // when we get any error from Firebase.
                    Toast.makeText(getActivity(), "Fail to load data..", Toast.LENGTH_SHORT).show();
                }
            });

    }

}
