package com.example.constraintexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText emailEt;
    EditText passEt;
    EditText nameEt;
    EditText contactEt;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent =new Intent(MainActivity.this,Main3Activity.class);
//                startActivity(intent);
//            }
//        },5000);
    }

    private void init() {
        emailEt = findViewById(R.id.sigupName);
        passEt = findViewById(R.id.signupPass);
        contactEt = findViewById(R.id.contactEt);
        nameEt = findViewById(R.id.nameEt);
        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("User");
    }

    public void signup(View view) {

        String email = emailEt.getText().toString();
        String pass = passEt.getText().toString();
        String name = nameEt.getText().toString();
        String contact = contactEt.getText().toString();

        if (email.isEmpty()) {
            emailEt.setError("Email required");
        } else if (pass.isEmpty()) {
            passEt.setError("Password required");
        } else {

            signupUser(email, pass, name, contact);

        }


    }

    private void signupUser(final String email, final String pass, final String name, final String contact) {

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    //String key=reference.push().getKey();
                    String key=auth.getCurrentUser().getUid();
                    User user=new User(name,email,pass,contact);
                    reference.child(key).setValue(user);


                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
