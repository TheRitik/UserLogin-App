package com.example.userlogin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button login , signup ;
    EditText inputemail,inputpassword;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    String emailPattern =  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputemail = findViewById(R.id.email);
        inputpassword = findViewById(R.id.password);
        login = findViewById(R.id.button);
        signup = findViewById(R.id.button2);
        String email = inputemail.getText().toString();
        String password = inputpassword.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performMatch();
            }
        });
    }
    private void performMatch() {
        String id = inputemail.getText().toString();
        String pass = inputpassword.getText().toString();
        if(!id.matches(emailPattern)){
            inputemail.setError("Worng Email");
        }
        else{
            progressDialog.setMessage("Waiting to Sign In");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("LogIn");
            mAuth.signInWithEmailAndPassword(id,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isComplete()){
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Wait for sign in", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(MainActivity.this,HomePage.class);
                        startActivity(myIntent);
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}