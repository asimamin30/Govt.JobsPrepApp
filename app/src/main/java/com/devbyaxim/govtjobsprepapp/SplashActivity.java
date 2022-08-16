package com.devbyaxim.govtjobsprepapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.devbyaxim.govtjobsprepapp.User.LoginActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class SplashActivity extends AppCompatActivity {

    private static final long Splash_Time = 2500;
    private ShapeableImageView logo;
    private MaterialTextView logo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.logo);
        logo2 = findViewById(R.id.logo2);

        setAnimationOnView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, Splash_Time);
    }

    private void setAnimationOnView() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(2500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        logo.setAnimation(anim);
        logo2.setAnimation(anim);
    }
}