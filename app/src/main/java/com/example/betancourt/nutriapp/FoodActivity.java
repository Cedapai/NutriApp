package com.example.betancourt.nutriapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betancourt.nutriapp.pojo.Food;
import com.example.betancourt.nutriapp.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FoodActivity extends AppCompatActivity {

    private static final String TAG = "Firelog";
    private TextView txtComidaDetail, txtQuantityDetail;
    // Form
    private TextView txtIngrediente, txtMarca, txtLugar, txtQuien, txtActividad, txtHora;
    private ImageView imgComidaDetail;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseStorage storage;
    public StorageReference storageRef;
    private String img_name, food_id, food_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        storage = FirebaseStorage.getInstance();

        txtComidaDetail = (TextView) findViewById(R.id.nombre_comida_detail);
        txtQuantityDetail = (TextView) findViewById(R.id.nombre_quantity_comida_detail);
        imgComidaDetail = (ImageView) findViewById(R.id.foodImageDetail);

        // Form
        txtIngrediente = (TextView) findViewById(R.id.txtIngredientes);
        txtMarca = (TextView) findViewById(R.id.txtMarca);
        txtLugar = (TextView) findViewById(R.id.txtLugar);
        txtQuien = (TextView) findViewById(R.id.txtQuien);
        txtActividad = (TextView) findViewById(R.id.txtActividad);
        txtHora = (TextView) findViewById(R.id.txtHour);

        // Fill with food
        Intent intent = getIntent();
        food_id = intent.getStringExtra("id");
        food_type = intent.getStringExtra("type");
        Log.e(TAG, food_id);
        DocumentReference docRef = db.collection("food").document(food_id);
        final ProgressDialog progressDialog = ProgressDialog.show(FoodActivity.this,
                "Por favor espera...", "Procesando...", true);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Firestore", "DocumentSnapshot data: " + document.getData());
                        // Use user POJO
                        Food food = document.toObject(Food.class).withId(food_id);
                        txtComidaDetail.setText(food.getName());
                        txtQuantityDetail.setText(food.getQuantity());
                        img_name = food.getImg_name();
                        storageRef = storage.getReferenceFromUrl("gs://nutriapp-c8be7.appspot.com/Food").child(img_name);
                        final long ONE_MEGABYTE = 1024 * 1024;
                        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                imgComidaDetail.setImageBitmap(bitmap);
                            }
                        });
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

    public void btnAddFoodRegister(View v){
        final ProgressDialog progressDialog = ProgressDialog.show(FoodActivity.this,
                "Por favor espera...", "Procesando...", true);
        Map<String, Object> foodRegister = new HashMap<>();
        // Get user auth id
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Data of the user
        foodRegister.put("ingredient", txtIngrediente.getText().toString());
        foodRegister.put("brand", txtMarca.getText().toString());
        foodRegister.put("quantity", txtQuantityDetail.getText().toString());
        foodRegister.put("food_name", txtComidaDetail.getText().toString());
        foodRegister.put("img_name", img_name);
        foodRegister.put("place", txtLugar.getText().toString());
        foodRegister.put("who", txtQuien.getText().toString());
        foodRegister.put("activity", txtActividad.getText().toString());
        foodRegister.put("food_type", food_type);
        foodRegister.put("food_id", food_id);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        foodRegister.put("date", formatter.format(date));
        foodRegister.put("hour", txtHora.getText().toString());
        Log.e(TAG, foodRegister.toString());
        db.collection("users").document(userUid).collection("registers")
                .add(foodRegister)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Firestore", "DocumentSnapshot successfully written!");
                        Toast.makeText(FoodActivity.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(FoodActivity.this, LandingUserActivity.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error adding document", e);
                    }
                });
    }
}
