package com.devbyaxim.govtjobsprepapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.devbyaxim.govtjobsprepapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AdminLogin extends AppCompatActivity {

    private String usernameStr, passwordStr;
    private TextInputLayout usernameTIL, passwordTIL;
    private TextInputEditText usernameET, passwordET;
    private Button login_button;
    private SharedPreferences adminPreferences;
    private SharedPreferences.Editor adminPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        usernameTIL = findViewById(R.id.usernameTIL);
        passwordTIL = findViewById(R.id.passwordTIL);
        usernameET = findViewById(R.id.adminEt);
        passwordET = findViewById(R.id.passwordET);
        login_button = findViewById(R.id.admin_login_button);


        adminPreferences = getSharedPreferences("adminPrefs", MODE_PRIVATE);
        adminPrefsEditor = adminPreferences.edit();


        login_button.setOnClickListener(view -> {
            usernameStr = usernameTIL.getEditText().getText().toString();
            passwordStr = passwordTIL.getEditText().getText().toString();
            if (usernameStr.isEmpty()) {
                usernameTIL.setError("Please enter username");
                usernameTIL.requestFocus();
            } else if (passwordStr.isEmpty()) {
                passwordTIL.setError("Please enter password");
                passwordTIL.requestFocus();
            } else {
                if (usernameStr.equals("admin")) {
                    if (passwordStr.equals("admin")) {
                        adminPrefsEditor.putString("username", usernameStr);
                        startActivity(new Intent(AdminLogin.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Record not found", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}