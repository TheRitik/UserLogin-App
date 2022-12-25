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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    EditText first , last , password , email ;
    Button signup , signin ;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        first = findViewById(R.id.editTextTextPassword);
        last = findViewById(R.id.editTextTextPassword2);
        password = findViewById(R.id.editTextTextPassword3);
        email = findViewById(R.id.editTextTextPassword4);
        signup = findViewById(R.id.button3);
        signin = findViewById(R.id.button4);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SignUp.this,MainActivity.class);
                startActivity(myIntent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });
    }
    private void performAuth() {
//        String firstn = first.getText().toString();
//        String lastn = last.getText().toString();
        String id = email.getText().toString();
        String pass = password.getText().toString();
        progressDialog.setMessage("Wait For Registration");
        progressDialog.setTitle("Registered");
        progressDialog.setCanceledOnTouchOutside(false);

        mAuth.createUserWithEmailAndPassword(id,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this, "Registered", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent myintent = new Intent(SignUp.this,HomePage.class);
                    startActivity(myintent);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}