package com.devbyaxim.govtjobsprepapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.devbyaxim.govtjobsprepapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private String usernameStr, passwordStr, emailStr, phoneStr;
    private TextInputLayout usernameTIL, passwordTIL, emailTIL, phoneTIL;
    private TextInputEditText usernameET, passwordET, emailET, phoneET;
    private Button signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        usernameTIL = findViewById(R.id.usernameTIL);
        passwordTIL = findViewById(R.id.passwordTIL);
        emailTIL = findViewById(R.id.EmailTIL);
        phoneTIL = findViewById(R.id.PhoneTIL);

        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        emailET = findViewById(R.id.EmailET);
        phoneET = findViewById(R.id.PhoneET);


        signup_button = findViewById(R.id.signup_button);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usernameStr = usernameTIL.getEditText().getText().toString();
                passwordStr = passwordTIL.getEditText().getText().toString();
                emailStr = emailTIL.getEditText().getText().toString();
                phoneStr = phoneTIL.getEditText().getText().toString();


                if (usernameStr.isEmpty()) {
                    usernameTIL.setError("Please enter username");
                    usernameTIL.requestFocus();
                } else if (passwordStr.isEmpty()) {
                    passwordTIL.setError("Please enter password");
                    passwordTIL.requestFocus();
                } else if (emailStr.isEmpty()) {
                    emailTIL.setError("Please enter email");
                    emailTIL.requestFocus();
                } else if (phoneStr.isEmpty()) {
                    phoneTIL.setError("Please enter phone number");
                    phoneTIL.requestFocus();
                } else {
                    OpenDatabaseToSaveData();


                }
            }
        });
    }

    private void OpenDatabaseToSaveData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        HashMap<String, Object> data = new HashMap<>();
        data.put("username", usernameStr);
        data.put("email", emailStr);
        data.put("password", passwordStr);
        data.put("phoneNo", phoneStr);
        data.put("type", "user");


        reference.child(usernameStr).setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // open user profile after successful registration
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
//                             To prevent user from returning back to Register Activity on pressing back button after registration
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "User Registered successfully.", Toast.LENGTH_LONG).show();
                            finish(); // to close Register Activity
                        } else {
                            Toast.makeText(getApplicationContext(), "User Registered failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}