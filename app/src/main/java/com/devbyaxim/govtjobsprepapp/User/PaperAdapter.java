package com.devbyaxim.govtjobsprepapp.User;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devbyaxim.govtjobsprepapp.Admin.ViewSelectionPastPaperActivity;
import com.devbyaxim.govtjobsprepapp.Admin.adapter.YearAdapter;
import com.devbyaxim.govtjobsprepapp.Admin.model.PostModel;
import com.devbyaxim.govtjobsprepapp.Admin.webActivity;
import com.devbyaxim.govtjobsprepapp.R;

import java.util.ArrayList;

public class PaperAdapter  extends RecyclerView.Adapter<PaperAdapter.ViewHolder> {
    Context Context;
    ArrayList<PostModel> yearArrayList;

    private SharedPreferences adminPreferences;
    private SharedPreferences.Editor adminPrefsEditor;

    public PaperAdapter(ViewPastPaper Context, ArrayList<PostModel> yearArrayList) {
        this.Context = Context;
        this.yearArrayList = yearArrayList;
    }

    @NonNull
    @Override
    public PaperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Context).inflate(R.layout.userside_row, parent, false);
        return new PaperAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaperAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


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



        adminPreferences = Context.getSharedPreferences("adminPrefs", MODE_PRIVATE);
        adminPrefsEditor = adminPreferences.edit();

        adminPreferences.getString("username", "");


        holder.getqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getQr));
                Context.startActivity(intent);
            }
        });

//        holder.generateQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(Context, webActivity.class);
//                intent.putExtra("title",title);
//                intent.putExtra("year",year);
//                intent.putExtra("des",des);
//                intent.putExtra("url",url);
//                intent.putExtra("name",name);
//                intent.putExtra("type",type);
//                intent.putExtra("longDesc",longDesc);
//                intent.putExtra("category",category);
//                Context.startActivity(intent);
//            }
//        });

        holder.showpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                Context.startActivity(intent);
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
        TextView title, year, des, showpaper,getqr;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titlee);
            year = itemView.findViewById(R.id.year);
            des = itemView.findViewById(R.id.shortdes);
            showpaper = itemView.findViewById(R.id.showpaper);
            getqr = itemView.findViewById(R.id.getQR);
        }
    }

}
