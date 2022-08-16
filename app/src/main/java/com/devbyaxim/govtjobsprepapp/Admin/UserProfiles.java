package com.devbyaxim.govtjobsprepapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.devbyaxim.govtjobsprepapp.Admin.adapter.UserProfileAdapter;
import com.devbyaxim.govtjobsprepapp.Admin.adapter.YearAdapter;
import com.devbyaxim.govtjobsprepapp.R;
import com.devbyaxim.govtjobsprepapp.Admin.model.usermodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfiles extends AppCompatActivity {


     ArrayList<usermodel> usermodels;
    RecyclerView rcvRv;
    UserProfileAdapter userProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profiles);

        rcvRv=findViewById(R.id.userrcv);

        loadAllprofiles();
        Toast.makeText(this, "click me ", Toast.LENGTH_SHORT).show();

    }

    private void loadAllprofiles() {

        usermodels = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        reference.orderByChild("type").equalTo("user").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clear list before adding
                        usermodels.clear();
                        for(DataSnapshot ds: snapshot.getChildren())
                        {
                            usermodel modelShop = ds.getValue(usermodel.class);
                            usermodels.add(modelShop);

                        }
                        //setup adapter
                        userProfileAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        userProfileAdapter = new UserProfileAdapter(UserProfiles.this,usermodels);
        //set adapter to recyclerview
        rcvRv.setAdapter(userProfileAdapter);

    }


    }
