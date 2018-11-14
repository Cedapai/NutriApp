package com.example.betancourt.nutriapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText txtEmailAddress, txtPassword, txtName;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txtEmailAddress = (EditText) findViewById(R.id.txtEmailRegistration);
        txtPassword = (EditText) findViewById(R.id.txtPasswordRegistration);
        txtName = (EditText) findViewById(R.id.txtNameRegistration);
        firebaseAuth = FirebaseAuth.getInstance();

    }
    public void btnRegistrationUser_Click(View v){
        final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this,
                "Por favor espera...", "Procesando...", true);
        firebaseAuth.createUserWithEmailAndPassword(txtEmailAddress.getText().toString(),
                txtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Map<String, Object> user = new HashMap<>();
                    // Get user auth id
                    String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    // Data of the user
                    user.put("name", txtName.getText().toString());
                    // Create user in firestore
                    db.collection("users").document(userUid)
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Firestore", "DocumentSnapshot successfully written!");
                                    Toast.makeText(RegistrationActivity.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(RegistrationActivity.this, IniciarSesionActivity.class);
                                    startActivity(i);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Firestore", "Error adding document", e);
                                }
                            });
                }else{
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
