package com.example.winamp_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    //Variables
    Button loginForm,signup;
    EditText user, password, email, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                        DatabaseHelper db = new DatabaseHelper(Register.this);
                        db.insertUser(user.getText().toString().trim(),
                                password.getText().toString().trim(),
                                email.getText().toString().trim());
                        Toast.makeText(getApplicationContext(),"User registered successfully", Toast.LENGTH_SHORT);
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