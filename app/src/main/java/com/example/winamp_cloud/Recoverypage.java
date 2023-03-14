package com.example.winamp_cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Recoverypage extends AppCompatActivity {

    private Button goBackButton, recoveryButton;
    private EditText email, newPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoverypage);

        goBackButton = findViewById(R.id.goBackButton);
        recoveryButton = findViewById(R.id.revButton);
        email = findViewById(R.id.emailRec);
        newPassword = findViewById(R.id.newPassword);
        databaseHelper = new DatabaseHelper(this);

        recoveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                String newPwd = newPassword.getText().toString();

                //Opens database connection
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                //Update data
                ContentValues cv = new ContentValues();
                cv.put("password", newPwd);

                int rowsAffected = db.update("users", cv,"email = ?", new String[]{em});

                // Checks the process
                if(rowsAffected >0){
                    // In case of the data was successfuly updated
                    Toast.makeText(Recoverypage.this,"Password successfuly updated", Toast.LENGTH_SHORT).show();
                }else{
                    // In case of a failed update
                    Toast.makeText(Recoverypage.this,"Password Update Failed", Toast.LENGTH_SHORT).show();
                }

                db.close();
            }
        });
    }
}