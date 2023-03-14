package com.example.winamp_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShowUserInfo extends AppCompatActivity {

    private Button goBackButton, revButton;
    private EditText currentEmail,hiddenPassword, hiddenUsername;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        goBackButton = findViewById(R.id.comeBackButton);
        revButton = findViewById(R.id.recoverButton);
        currentEmail = findViewById(R.id.recEmail);
        hiddenPassword = findViewById(R.id.passHidden);
        hiddenUsername = findViewById(R.id.username);

        // Initialize database
        database = openOrCreateDatabase("winampusers.db", MODE_PRIVATE, null);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowUserInfo.this, Login.class);
                startActivity(intent);
            }
        });

        revButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = currentEmail.getText().toString().trim();

                //Evades to left the Email field in blank
                if (email.isEmpty()) {
                    Toast.makeText(ShowUserInfo.this, "Email field cannot left in blank!", Toast.LENGTH_SHORT).show();
                } else {

                    Cursor cursor = database.rawQuery("SELECT * FROM users WHERE email=?", new String[]{email});

                    if (cursor.moveToFirst()) {
                        //User was found so it displays their password
                        @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                        if(hiddenPassword.getVisibility() == View.GONE){
                            hiddenPassword.setText(password);
                            hiddenPassword.setVisibility(View.VISIBLE);
                        }else{
                            hiddenPassword.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(ShowUserInfo.this, "No user found with that email", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}