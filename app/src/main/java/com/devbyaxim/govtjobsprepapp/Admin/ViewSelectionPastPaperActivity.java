package com.devbyaxim.govtjobsprepapp.Admin;

import static com.devbyaxim.govtjobsprepapp.custom.constants._PostCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.devbyaxim.govtjobsprepapp.Admin.adapter.YearAdapter;
import com.devbyaxim.govtjobsprepapp.Admin.model.PostModel;
import com.devbyaxim.govtjobsprepapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewSelectionPastPaperActivity extends AppCompatActivity {

    private TextView ppscMaterialListTv,cssMaterialListTv,asfMaterialListTv;
    FirebaseDatabase database;
//    public static String _PostCategory = "PPSC";

    RecyclerView _PostsRecyclerView;
    ArrayList<PostModel> _PostsArrayList;
    YearAdapter adapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_past_paper);

        database = FirebaseDatabase.getInstance();

        _PostsArrayList = new ArrayList<>();

        _PostCategory = "PPSC";

        initView();
        SetListeners();
        Functionality();


    }

    private void initView() {
        ppscMaterialListTv = findViewById(R.id.ppscMaterialListTv);
        cssMaterialListTv = findViewById(R.id.cssMaterialListTv);
        asfMaterialListTv = findViewById(R.id.asfMaterialListTv);
        _PostsRecyclerView = findViewById(R.id.PostsRecyclerView);
    }

    private void SetListeners() {
        ppscMaterialListTv.setOnClickListener(view -> {
            _PostCategory = "PPSC";
            ppscMaterialListTv.setBackgroundColor(getResources().getColor(R.color.white));
            cssMaterialListTv.setBackgroundColor(getResources().getColor(R.color.disable_card));
            asfMaterialListTv.setBackgroundColor(getResources().getColor(R.color.disable_card));

            ppscMaterialListTv.setTextColor(getResources().getColor(R.color.black));
            cssMaterialListTv.setTextColor(getResources().getColor(R.color.grayColor));
            asfMaterialListTv.setTextColor(getResources().getColor(R.color.grayColor));
            Functionality();
        });

        cssMaterialListTv.setOnClickListener(view -> {
            _PostCategory = "CSS";
            cssMaterialListTv.setBackgroundColor(getResources().getColor(R.color.white));
            asfMaterialListTv.setBackgroundColor(getResources().getColor(R.color.disable_card));
            ppscMaterialListTv.setBackgroundColor(getResources().getColor(R.color.disable_card));

            cssMaterialListTv.setTextColor(getResources().getColor(R.color.black));
            asfMaterialListTv.setTextColor(getResources().getColor(R.color.grayColor));
            ppscMaterialListTv.setTextColor(getResources().getColor(R.color.grayColor));
            Functionality();
        });

        asfMaterialListTv.setOnClickListener(view -> {
            _PostCategory = "ASF";
            asfMaterialListTv.setBackgroundColor(getResources().getColor(R.color.white));
            ppscMaterialListTv.setBackgroundColor(getResources().getColor(R.color.disable_card));
            cssMaterialListTv.setBackgroundColor(getResources().getColor(R.color.disable_card));

            asfMaterialListTv.setTextColor(getResources().getColor(R.color.black));
            ppscMaterialListTv.setTextColor(getResources().getColor(R.color.grayColor));
            cssMaterialListTv.setTextColor(getResources().getColor(R.color.grayColor));
            Functionality();
        });
    }

    private void Functionality() {
        reference = database.getReference("Posts").child(_PostCategory);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _PostsArrayList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    PostModel modelShop = postSnapshot.getValue(PostModel.class);
                    _PostsArrayList.add(modelShop);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        adapter = new YearAdapter(ViewSelectionPastPaperActivity.this,_PostsArrayList);
        _PostsRecyclerView.setAdapter(adapter);

    }

}