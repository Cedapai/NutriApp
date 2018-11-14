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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class IniciarSesionActivity extends AppCompatActivity {

    private EditText txtEmailLogin, txtPass;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        txtEmailLogin = (EditText) findViewById(R.id.txtEmailLogin);
        txtPass = (EditText) findViewById(R.id.txtPasswordLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void btnUserLogin_Click(View v){
        final ProgressDialog progressDialog = ProgressDialog.show(IniciarSesionActivity.this,
                "Por favor espera...", "Procesando...", true);
        firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(),
                txtPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(IniciarSesionActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(IniciarSesionActivity.this, LandingUserActivity.class);
                    //i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                    startActivity(i);
                }else {
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(IniciarSesionActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
