package com.example.betancourt.nutriapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.betancourt.nutriapp.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LandingUserActivity extends AppCompatActivity {

    private TextView txtEmailUser;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                        txtEmailUser.setText(user.getName());
                        progressDialog.dismiss();
                    } else {
                        Log.d("Firestore", "No such document");
                    }
                } else {
                    Log.d("Firestore", "get failed with ", task.getException());
                }
            }
        });


    }
    public void btnAddFood(View v){
        Intent i = new Intent(LandingUserActivity.this, FoodTypeActivity.class);
        startActivity(i);
    }
}
