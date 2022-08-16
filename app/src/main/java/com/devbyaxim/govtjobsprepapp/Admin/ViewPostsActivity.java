package com.devbyaxim.govtjobsprepapp.Admin;

import static com.devbyaxim.govtjobsprepapp.custom.constants._PostCategory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.devbyaxim.govtjobsprepapp.R;

public class ViewPostsActivity extends AppCompatActivity {

    String YEAR;
    TextView year,catTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posts);

//        YEAR = getIntent().getStringExtra("year");
//
//        year = findViewById(R.id.year);
//        catTv = findViewById(R.id.catTv);
//        year.setText(YEAR);
//        catTv.setText(_PostCategory);
    }
}