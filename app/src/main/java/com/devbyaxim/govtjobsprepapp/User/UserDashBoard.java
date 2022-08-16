package com.devbyaxim.govtjobsprepapp.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.devbyaxim.govtjobsprepapp.Admin.MainActivity;
import com.devbyaxim.govtjobsprepapp.Admin.ProfileActivity;
import com.devbyaxim.govtjobsprepapp.Admin.UploadDocsActivity;
import com.devbyaxim.govtjobsprepapp.Admin.ViewSelectionPastPaperActivity;
import com.devbyaxim.govtjobsprepapp.R;
import com.google.android.material.card.MaterialCardView;

public class UserDashBoard extends AppCompatActivity {

    private MaterialCardView goToProfile,goToUploadDocs,post;
    private LinearLayout logOut;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
        goToProfile = findViewById(R.id.addGalleryImage);
//        goToUploadDocs = findViewById(R.id.goToUploadDocs);
//        logOut = findViewById(R.id.logOut);
        post = findViewById(R.id.post);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");


        goToProfile.setOnClickListener(view -> {
//            startActivity(new Intent(UserDashBoard.this, UserProfile.class));

            Intent intent1=new Intent(UserDashBoard.this, UserProfile.class);
            intent1.putExtra("username",username);
            startActivity(intent1);

        });



        post.setOnClickListener(view -> {
            startActivity(new Intent(UserDashBoard.this, ViewPastPaper.class));
        });

    }

    public void logout(View view) {

        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }
}