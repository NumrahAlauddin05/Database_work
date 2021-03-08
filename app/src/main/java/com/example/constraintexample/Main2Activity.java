package com.example.constraintexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {
    EditText emailEt;
    EditText passEt;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();
    }

    private void init() {
        emailEt = findViewById(R.id.loginEmail);
        passEt = findViewById(R.id.loginPass);
        auth=FirebaseAuth.getInstance();

    }

    public void signupBtn(View view) {

        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(intent);
    }

    public void loginBtn(View view) {

        String email = emailEt.getText().toString();
        String pass = passEt.getText().toString();

        if (email.isEmpty()) {
            emailEt.setError("Email required");
        } else if (pass.isEmpty()) {
            passEt.setError("Password required");
        } else {

            loginUser(email, pass);

        }

    }

    private void loginUser(String email, String pass) {

        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(Main2Activity.this, Home.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(Main2Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
