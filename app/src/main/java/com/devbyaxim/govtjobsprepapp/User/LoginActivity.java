package com.devbyaxim.govtjobsprepapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.devbyaxim.govtjobsprepapp.Admin.AdminLogin;
import com.devbyaxim.govtjobsprepapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private String usernameStr, passwordStr;
    private TextInputLayout usernameTIL, passwordTIL;
    private TextInputEditText usernameET, passwordET;
    private Button login_button;
    private ImageView logo;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameTIL = findViewById(R.id.usernameTIL);
        passwordTIL = findViewById(R.id.passwordTIL);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        login_button = findViewById(R.id.login_button);
        logo = findViewById(R.id.logo);

        logo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent=new Intent(LoginActivity.this, AdminLogin.class);
                startActivity(intent);
                return false;

            }
        });

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
                OpenDBAndCheckCredentials();
            }
        });
    }

    private void OpenDBAndCheckCredentials() {

        ref.child(usernameStr).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String pwd = snapshot.child("password").getValue(String.class);
                    if (pwd.equals(passwordStr)) {
                      Intent intent =new Intent(LoginActivity.this, UserDashBoard.class);
                      intent.putExtra("username",usernameStr);
                      startActivity(intent);
                      finish();
                        }
                     else {
                        Toast.makeText(getApplicationContext(),"Invalid credentials. Kindly, check and re-enter.",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"User does not exists or is no longer valid. Please register again.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void forgetpass(View view) {

        startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
        finish();
    }

    public void regnow(View view) {

        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        finish();
    }
}