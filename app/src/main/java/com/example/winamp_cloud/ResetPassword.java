package com.example.winamp_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPassword extends AppCompatActivity {

    private Button goBackButton, revButton;
    private EditText currentEmail;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        goBackButton = findViewById(R.id.comeBackButton);
        revButton = findViewById(R.id.recoverButton);
        currentEmail = findViewById(R.id.recEmail);

        // Initialize database
        database = openOrCreateDatabase("winampusers.db", MODE_PRIVATE, null);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassword.this, Login.class);
                startActivity(intent);
            }
        });

        revButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = currentEmail.getText().toString().trim();

                //Evades to left the Email field in blank
                if(email.isEmpty()){
                    Toast.makeText(ResetPassword.this,"Email field cannot left in blank!", Toast.LENGTH_SHORT);
                }else{
                    Cursor cursor = database.rawQuery("SELECT * FROM users WHERE email=?", new String[]{email});

                }
            }
        });
    }
}