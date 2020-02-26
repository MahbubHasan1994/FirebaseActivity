package com.example.firebaseactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText signUpEmail , signUpPassword;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpEmail = findViewById(R.id.loginEmail);
        signUpPassword = findViewById(R.id.loginPassword);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbarId);

        findViewById(R.id.goto_signup).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
    }

    public void userLogin(){

        String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();

        if(email.isEmpty()){
            signUpEmail.setError("Email is Required");
            signUpEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            signUpEmail.setError("Please Enter a valid Email Address");
            signUpEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            signUpPassword.setError("Password is Required");
            signUpPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            signUpPassword.setError("Minimum length of password should be of 6 Character");
            signUpPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(MainActivity.this , ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.goto_signup:

                startActivity(new Intent(this,SignUpActivity.class));
                break;

            case R.id.login:
                userLogin();
                break;
        }

    }
}
