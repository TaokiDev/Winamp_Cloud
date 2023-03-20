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

import org.checkerframework.checker.nullness.qual.NonNull;

public class ResetPassword extends AppCompatActivity {

    private Button goBackButton, revButton;
    private EditText currentEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        goBackButton = findViewById(R.id.comeBackButton);
        revButton = findViewById(R.id.recoverButton);
        currentEmail = findViewById(R.id.recEmail);

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
                String email = currentEmail.getText().toString();

                //Evades to left the Email field in blank
                if (email.isEmpty()) {
                    Toast.makeText(ResetPassword.this, "Email field cannot left in blank", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),
                                                "Password reset email sent. Check yor inbox.",
                                        Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),
                                                "Failed to send password reset email, Try Again.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }

}