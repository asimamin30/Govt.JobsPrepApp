package com.devbyaxim.govtjobsprepapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.devbyaxim.govtjobsprepapp.User.LoginActivity;
import com.devbyaxim.govtjobsprepapp.R;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    private MaterialCardView goToProfile,goToUploadDocs,post,users;
    private LinearLayout logOut;

    private SharedPreferences adminPreferences;
    private SharedPreferences.Editor adminPrefsEditor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToProfile = findViewById(R.id.goToProfile);
        goToUploadDocs = findViewById(R.id.goToUploadDocs);
        users = findViewById(R.id.users);
        logOut = findViewById(R.id.logOut);
        post = findViewById(R.id.post);


        adminPreferences = getSharedPreferences("adminPrefs", MODE_PRIVATE);
        adminPrefsEditor = adminPreferences.edit();




        goToProfile.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });

        goToUploadDocs.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, UploadDocsActivity.class));
        });

        logOut.setOnClickListener(view -> {
            adminPrefsEditor.clear();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        post.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ViewSelectionPastPaperActivity.class));
        });
        users.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, UserProfiles.class));
        });

    }
}