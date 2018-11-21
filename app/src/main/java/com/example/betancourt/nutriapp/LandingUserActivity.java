package com.example.betancourt.nutriapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.betancourt.nutriapp.adapters.FoodListAdapter;
import com.example.betancourt.nutriapp.adapters.RegisterListAdapter;
import com.example.betancourt.nutriapp.pojo.Food;
import com.example.betancourt.nutriapp.pojo.Register;
import com.example.betancourt.nutriapp.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class LandingUserActivity extends AppCompatActivity {

    private TextView txtEmailUser;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Firelog";
    private RecyclerView mRegisterlList;
    private FirebaseFirestore mFirestore;
    private RegisterListAdapter registerListAdapter;
    private List<Register> registerList;

    public FirebaseStorage mStorage;
    public StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_user);
        // Get user uid
        firebaseAuth = FirebaseAuth.getInstance();
        final String userUid = firebaseAuth.getCurrentUser().getUid();
        Log.d("Firestore", userUid);
        // View items
        txtEmailUser = (TextView) findViewById(R.id.txtLandingUserName);
        // Data from firestore
        DocumentReference docRef = db.collection("users").document(userUid);
        final ProgressDialog progressDialog = ProgressDialog.show(LandingUserActivity.this,
                "Por favor espera...", "Procesando...", true);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Firestore", "DocumentSnapshot data: " + document.getData());
                        // Use user POJO
                        User user = document.toObject(User.class).withId(userUid);
                        txtEmailUser.setText(("Usuario: " + user.getName() + "\nNutriologo: " + user.getDoctor()));
                        progressDialog.dismiss();
                    } else {
                        Log.d("Firestore", "No such document");
                    }
                } else {
                    Log.d("Firestore", "get failed with ", task.getException());
                }
            }
        });

        // Adapter
        registerList = new ArrayList<>();
        registerListAdapter = new RegisterListAdapter(getApplicationContext(), registerList);

        mRegisterlList = (RecyclerView) findViewById(R.id.register_list);
        mRegisterlList.setHasFixedSize(true);
        mRegisterlList.setLayoutManager(new LinearLayoutManager(this));
        mRegisterlList.setAdapter(registerListAdapter);

        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("users").document(userUid).collection("registers").orderBy("hour").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e != null){
                    Log.d(TAG, "Error: " + e.getMessage());
                }

                for(DocumentChange doc: documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){

                        String register_id = doc.getDocument().getId();
                        Log.e(TAG, register_id);
                        Register register = doc.getDocument().toObject(Register.class).withId(register_id);
                        registerList.add(register);
                        Log.e(TAG, register.toString());

                        registerListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }
    public void btnAddFood(View v){
        Intent i = new Intent(LandingUserActivity.this, FoodTypeActivity.class);
        startActivity(i);
    }
}
