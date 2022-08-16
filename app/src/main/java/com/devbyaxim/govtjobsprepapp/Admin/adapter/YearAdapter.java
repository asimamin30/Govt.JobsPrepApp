package com.devbyaxim.govtjobsprepapp.Admin.adapter;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devbyaxim.govtjobsprepapp.Admin.model.PostModel;
import com.devbyaxim.govtjobsprepapp.Admin.webActivity;
import com.devbyaxim.govtjobsprepapp.R;
import com.devbyaxim.govtjobsprepapp.Admin.ViewPostsActivity;
import com.devbyaxim.govtjobsprepapp.Admin.ViewSelectionPastPaperActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {
    Context viewSelectionPastPaperActivityContext;
    ArrayList<PostModel> yearArrayList;

    private SharedPreferences adminPreferences;
    private SharedPreferences.Editor adminPrefsEditor;

    public YearAdapter(ViewSelectionPastPaperActivity viewSelectionPastPaperActivityContext, ArrayList<PostModel> yearArrayList) {
        this.viewSelectionPastPaperActivityContext = viewSelectionPastPaperActivityContext;
        this.yearArrayList = yearArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(viewSelectionPastPaperActivityContext).inflate(R.layout.resource_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        String title = yearArrayList.get(position).getTitle();
        String year = yearArrayList.get(position).getYear();
        String des = yearArrayList.get(position).getShortDesc();
        String url = yearArrayList.get(position).getFilePath();
        String name = yearArrayList.get(position).getName();
        String type = yearArrayList.get(position).getType();
        String longDesc = yearArrayList.get(position).getLongDesc();
        String category = yearArrayList.get(position).getCategory();
        String getQr=yearArrayList.get(position).getQR();


        holder.title.setText(title);
        holder.year.setText(year);
        holder.des.setText(des);



        adminPreferences = viewSelectionPastPaperActivityContext.getSharedPreferences("adminPrefs", MODE_PRIVATE);
        adminPrefsEditor = adminPreferences.edit();

        adminPreferences.getString("username", "");



//        holder.getqr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getQr));
//                viewSelectionPastPaperActivityContext.startActivity(intent);
//            }
//        });

        holder.generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        Intent intent=new Intent(viewSelectionPastPaperActivityContext,webActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("year",year);
        intent.putExtra("des",des);
        intent.putExtra("url",url);
        intent.putExtra("name",name);
        intent.putExtra("type",type);
        intent.putExtra("longDesc",longDesc);
        intent.putExtra("category",category);
        viewSelectionPastPaperActivityContext.startActivity(intent);
            }
        });

        holder.showpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                viewSelectionPastPaperActivityContext.startActivity(intent);
            }
        });
    }

    public void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);
    }


    @Override
    public int getItemCount() {
        return yearArrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, year, des, showpaper, generateQR,getqr;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titlee);
            year = itemView.findViewById(R.id.year);
            des = itemView.findViewById(R.id.shortdes);
            showpaper = itemView.findViewById(R.id.showpaper);
            generateQR = itemView.findViewById(R.id.generateQR);
//            getqr = itemView.findViewById(R.id.getQR);
        }
    }

}
