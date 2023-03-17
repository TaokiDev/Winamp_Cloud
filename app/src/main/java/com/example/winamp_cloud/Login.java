package com.example.winamp_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {

    //Variables
    private Button signupForm, loginButton, recoveryButton;
    private EditText email, password;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Reference variables
        signupForm = findViewById(R.id.signupScreen);
        loginButton = findViewById(R.id.loginButton);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        recoveryButton = findViewById(R.id.revButton);

        firebaseAuth = FirebaseAuth.getInstance();

        signupForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        recoveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ResetPassword.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        //Sign in success, UI update with the user data
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        Toast.makeText(getApplicationContext(),"Auhentication successful", Toast.LENGTH_SHORT).show();
                                        emptyFields();
                                        Intent intent = new Intent(Login.this, Homepage.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Something went wrong in authentication, try again", Toast.LENGTH_SHORT).show();
                                        emptyFields();
                                    }
                                }
                            });
                }
            }
        });

    }

    private boolean checkEmpty() {
        if(email.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Email field left empty",Toast.LENGTH_SHORT).show();
            return true;
        }else if(password.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Password field left empty", Toast.LENGTH_SHORT).show();
            return true;
        }else if(email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"No info provided", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }
    }

    private void emptyFields(){
        email.setText("");
        password.setText("");
    }
}
