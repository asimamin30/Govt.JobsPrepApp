package com.devbyaxim.govtjobsprepapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devbyaxim.govtjobsprepapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ForgetActivity02 extends AppCompatActivity {
    private String username,phoneNo,password,email;
    private TextInputLayout usernameTIL, passwordTIL;
    private TextInputEditText usernameET, passwordET;
    private Button check_button;
    private ImageView logo;
    TextView oldpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget02);

        oldpass=findViewById(R.id.oldpass);
        passwordTIL = findViewById(R.id.passwordTIL);
        passwordET = findViewById(R.id.passwordET);
        check_button=findViewById(R.id.check_cred);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        phoneNo = intent.getStringExtra("phoneNo");
        password = intent.getStringExtra("password");
        email = intent.getStringExtra("email");
        
        oldpass.setText(password);


        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (password.isEmpty()) {
                    passwordTIL.setError("Please enter password");
                    passwordTIL.requestFocus();
                } else {
                    ResetPass();
                }

            }
        });

    }

    private void ResetPass() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        HashMap<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("email", email);
        data.put("password", password);
        data.put("phoneNo", phoneNo);


        reference.child(username).setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // open user profile after successful registration
                            Intent intent = new Intent(ForgetActivity02.this, LoginActivity.class);
//                             To prevent user from returning back to Register Activity on pressing back button after registration
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Password Changed Successfully.", Toast.LENGTH_LONG).show();
                            finish(); // to close Register Activity
                        } else {
                            Toast.makeText(getApplicationContext(), "Password Changed  failed. Please try again.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    }
