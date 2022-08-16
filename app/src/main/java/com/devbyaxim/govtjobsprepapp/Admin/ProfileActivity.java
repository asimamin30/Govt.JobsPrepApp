package com.devbyaxim.govtjobsprepapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devbyaxim.govtjobsprepapp.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private String username, email,des;
     EditText EmailET,desET;
     TextView usernameTV;
    private ImageView eyeIV;
    private Button editBtn;
    private SharedPreferences adminPreferences;
    private SharedPreferences.Editor adminPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameTV=findViewById(R.id.usernameTV);
        EmailET=findViewById(R.id.EmailET);
        desET=findViewById(R.id.DesET);




        editBtn = findViewById(R.id.editBtn);

        adminPreferences = getSharedPreferences("adminPrefs", MODE_PRIVATE);
        adminPrefsEditor = adminPreferences.edit();

        usernameTV.setText(adminPreferences.getString("username", ""));
        EmailET.setText(adminPreferences.getString("email", ""));
        desET.setText(adminPreferences.getString("des", ""));


        editBtn.setOnClickListener(view -> {

            username="admin";
            email=EmailET.getText().toString();
            des=desET.getText().toString();

            adminPrefsEditor.putString("username", username);
            adminPrefsEditor.putString("email", email);
            adminPrefsEditor.putString("des", des);



            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();

            adminPrefsEditor.apply();
        });

    }


    }

