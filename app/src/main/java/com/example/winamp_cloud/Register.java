package com.example.winamp_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class Register extends AppCompatActivity {

    //Variables
    Button loginForm,signup;
    EditText user, password, email, confirmPassword;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialize Firebase Auth and Firebase instances
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //Reference variables
        loginForm = findViewById(R.id.gobackButton);
        signup = findViewById(R.id.signUpButton);
        user = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        confirmPassword = findViewById(R.id.confirmPassword);

        //Cancel sign up and go back to login screen
        loginForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        //Sign up functions
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkEmpty() == false) {

                    if (!checkEmail(email.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                    }

                    else if (!checkUser(user.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Error, username must start with Uppercase and contains 4 characters at least (max 12)", Toast.LENGTH_LONG).show();
                    }
                    else if (!checkPassword(password.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Error, password must contain 8 characters at least, one letter and one number", Toast.LENGTH_LONG).show();
                    }

                    else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_LONG).show();
                    }

                    else{
                        /*
                        Created a custom user class because it's needed to pass more than one parameter
                        */
                        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>(){
                                    @Override
                                    public void onComplete(@NotNull Task<AuthResult> task){

                                        if(task.isSuccessful()){
                                        //Sign up success, the UI are update to the user data
                                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                            User userObj = new User(user.getText().toString().trim(), email.getText().toString().trim());
                                            firestore.collection("users").document(firebaseUser.getUid()).set(userObj);
                                            Toast.makeText(getApplicationContext(),"User Registered Successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else{
                                            Toast.makeText(getApplicationContext(),"Registration Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                }

            }
        });


    }

    //Filters

    private boolean checkUser(String s){
        return s.matches("^[A-Z]\\w{3,9}$");
    }

    private boolean checkPassword(String s){ return s.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$"); }

    private boolean checkEmail(String s){ return s.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"); }

    private boolean checkEmpty(){
        if(email.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Error, email has not been set",Toast.LENGTH_SHORT ).show();
            return true;
        }

        else if(user.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Error, user has not been set",Toast.LENGTH_SHORT ).show();
            return true;
        }

        else if(password.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Error, password has not been set",Toast.LENGTH_SHORT ).show();
            return true;
        }

        else if(confirmPassword.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Error, confirm the password please",Toast.LENGTH_SHORT ).show();
            return true;
        }else{
            return false;
        }

    }
}