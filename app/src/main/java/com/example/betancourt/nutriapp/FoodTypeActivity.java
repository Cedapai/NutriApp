package com.example.betancourt.nutriapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FoodTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type);
    }

    public void btnDesayuno_Click(View v){
        Intent i = new Intent(FoodTypeActivity.this, SearchFoodActivity.class);
        i.putExtra("type", "desayuno");
        startActivity(i);
    }
    public void btnComida_Click(View v){
        Intent i = new Intent(FoodTypeActivity.this, SearchFoodActivity.class);
        i.putExtra("type", "comida");
        startActivity(i);
    }
    public void btnCena_Click(View v){
        Intent i = new Intent(FoodTypeActivity.this, SearchFoodActivity.class);
        i.putExtra("type", "cena");
        startActivity(i);
    }
    public void btnColacion_Click(View v){
        Intent i = new Intent(FoodTypeActivity.this, SearchFoodActivity.class);
        i.putExtra("type", "colacion");
        startActivity(i);
    }
}