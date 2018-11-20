package com.example.betancourt.nutriapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.betancourt.nutriapp.adapters.FoodListAdapter;
import com.example.betancourt.nutriapp.pojo.Food;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SearchFoodActivity extends AppCompatActivity {

    private static final String TAG = "Firelog";
    private RecyclerView mFoodlList;
    private FirebaseFirestore mFirestore;
    private FoodListAdapter foodListAdapter;
    private List<Food> foodList;

    public FirebaseStorage mStorage;
    public StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);
        Intent intent = getIntent();
        String food_type = intent.getStringExtra("type");
        foodList = new ArrayList<>();
        foodListAdapter = new FoodListAdapter(getApplicationContext(), foodList, food_type);

        mFoodlList = (RecyclerView) findViewById(R.id.food_list);
        mFoodlList.setHasFixedSize(true);
        mFoodlList.setLayoutManager(new LinearLayoutManager(this));
        mFoodlList.setAdapter(foodListAdapter);

        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("food").orderBy("name").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e != null){
                    Log.d(TAG, "Error: " + e.getMessage());
                }

                for(DocumentChange doc: documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){

                        String food_id = doc.getDocument().getId();
                        Log.e(TAG, food_id);
                        Food food = doc.getDocument().toObject(Food.class).withId(food_id);
                        foodList.add(food);
                        Log.e(TAG, food.toString());

                        foodListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
