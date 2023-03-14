package com.example.winamp_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class Login extends AppCompatActivity {

    //Variables
    private Button signupForm, loginButton, recoveryButton;
    private EditText username, password;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Reference variables
        signupForm = findViewById(R.id.signupScreen);
        loginButton = findViewById(R.id.loginButton);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        recoveryButton = findViewById(R.id.revButton);
        databaseHelper = new DatabaseHelper(this);

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
                Intent intent = new Intent(Login.this, Recoverypage.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String usr = username.getText().toString();
                    String pwd = password.getText().toString();

                    if(databaseHelper.checkLogin(usr,pwd)){
                        Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Homepage.class);
                        emptyFields();
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login.this, "Invalid Parameters", Toast.LENGTH_SHORT).show();
                    }


            }
        });

    }

    private void emptyFields(){
        username.setText("");
        password.setText("");
    }
}
