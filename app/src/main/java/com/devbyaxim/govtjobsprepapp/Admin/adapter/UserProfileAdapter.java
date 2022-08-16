package com.devbyaxim.govtjobsprepapp.Admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devbyaxim.govtjobsprepapp.Admin.model.usermodel;
import com.devbyaxim.govtjobsprepapp.R;

import java.util.ArrayList;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.HolderShop> {

    private Context context;
    public ArrayList<usermodel> userlist;

    public UserProfileAdapter(Context context, ArrayList<usermodel> userlist) {
        this.context = context;
        this.userlist = userlist;
    }

    @NonNull
    @Override
    public UserProfileAdapter.HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_shop.xml
        View view = LayoutInflater.from(context).inflate(R.layout.custom_profile, parent, false);


        return new UserProfileAdapter.HolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileAdapter.HolderShop holder, int position) {
        //get data
        usermodel modelShop = userlist.get(position);
        final String name = modelShop.getUsername();
        final String email = modelShop.getEmail();
        final String phone = modelShop.getPhoneNo();
        Log.d("name", name);


        //set data
        holder.username.setText(name);
        holder.email.setText(email);
        holder.phone.setText(phone);


        //handle click listener, show shop details
    }

    @Override
    public int getItemCount() {
        return userlist.size(); // return number of records
    }

    //view holder
    class HolderShop extends RecyclerView.ViewHolder {

        private TextView username, email, phone;

        public HolderShop(@NonNull View itemView) {
            super(itemView);

            //init ui views
            username = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
        }
    }

}

