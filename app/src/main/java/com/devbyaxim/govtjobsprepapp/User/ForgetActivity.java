package com.devbyaxim.govtjobsprepapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.devbyaxim.govtjobsprepapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetActivity extends AppCompatActivity {

    private String usernameStr, passwordStr;
    private TextInputLayout usernameTIL, passwordTIL;
    private TextInputEditText usernameET, passwordET;
    private Button check_button;
    private ImageView logo;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        usernameTIL = findViewById(R.id.usernametil);
        passwordTIL = findViewById(R.id.passwordTIL);
        usernameET = findViewById(R.id.usernamefg);
        passwordET = findViewById(R.id.passwordET);

        check_button = findViewById(R.id.check_cred);
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameStr = usernameTIL.getEditText().getText().toString();

                if (usernameStr.isEmpty()) {
                    usernameTIL.setError("Please enter username");
                    usernameTIL.requestFocus();
                } else {

                    ref.child(usernameStr)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {


                                        String username = snapshot.child("username").getValue(String.class);
                                        String email = snapshot.child("email").getValue(String.class);
                                        String phoneNo = snapshot.child("phoneNo").getValue(String.class);
                                        String password = snapshot.child("password").getValue(String.class);



                                        Intent intent = new Intent(getApplicationContext(), ForgetActivity02.class);
                                        intent.putExtra("username", username);
                                        intent.putExtra("email", email);
                                        intent.putExtra("phoneNo", phoneNo);
                                        intent.putExtra("password", password);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Record Not Find", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                }

            }
        });
    }
}