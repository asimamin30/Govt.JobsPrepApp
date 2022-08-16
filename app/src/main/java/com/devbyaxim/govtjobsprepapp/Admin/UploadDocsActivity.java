package com.devbyaxim.govtjobsprepapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.devbyaxim.govtjobsprepapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UploadDocsActivity extends AppCompatActivity {
    private String selectedPostCategory = "";

    private AutoCompleteTextView postCategory;
    private MaterialCardView addPastPaper;
    private MaterialButton uploadPostBtn;
    private Button viewPost;
    private TextInputEditText shortDescriptionET, longDescriptionET, yearEt, titleET;
    private String shortDescStr, longDescStr, yearStr, titleStr;
    private TextInputLayout shortDescTIL, longDescriptionTIL, postTIL, yearTIL, titleTIL;

    //for image get
/*    private Uri uri;
    private final int REQ = 2;
    private Bitmap bitmap;
    String timestamp;
    String imageRename;
    String resultUriStr;
    private String uriStr = "";*/

    // get file
    private String checker = "", myUri="",QR="";
    private StorageTask uploadTask;
    private Uri fileUri;
    String timestamp;
    String imageRename;
    NotificationManager notificationManager;


    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private static final String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_docs);


         notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        postCategory = findViewById(R.id.postCategory);
        addPastPaper = findViewById(R.id.addPastPaper);
        uploadPostBtn = findViewById(R.id.uploadPostBtn);
        viewPost = findViewById(R.id.viewPost);
        shortDescriptionET = findViewById(R.id.shortDescriptionET);
        longDescriptionET = findViewById(R.id.longDescriptionET);
        yearEt = findViewById(R.id.yearEt);
        titleTIL = findViewById(R.id.titleTIL);
        titleET = findViewById(R.id.titleET);
        shortDescTIL = findViewById(R.id.shortDescTIL);
        longDescriptionTIL = findViewById(R.id.longDescriptionTIL);
        postTIL = findViewById(R.id.postTIL);
        yearTIL = findViewById(R.id.yearTIL);

        setupListeners();

        // https://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener.html
        postCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPostCategory = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(UploadDocsActivity.this, "" + selectedPostCategory, Toast.LENGTH_SHORT).show();
            }
        });

        /*
        String[] postCategoryStr = getResources().getStringArray(R.array.papers);
        ArrayAdapter<String> postCategoryAdapter = new ArrayAdapter<String>(this,R.layout.dropdown_item,postCategoryStr);
        uploadDocsXML.postCategory.setAdapter(postCategoryAdapter);*/

        addPastPaper.setOnClickListener(view -> {
            //openGallery();
            CharSequence options[] = new CharSequence[]
                    {
                            "Images",
                            "PDF Files",
                            "MS Word Files"
                    };
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocsActivity.this);
            builder.setTitle("Select the File");

            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        checker = "image";
//                        checker = "pdf";
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        intent.setType("application/pdf");
//                        startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 438);

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select Image"), 438);
                    }
                    if (i == 1) {
                        checker = "pdf";
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("application/pdf");
                        startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 438);
                    }
                    if (i == 2) {
                        checker = "docx";
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("application/msword");
                        startActivityForResult(Intent.createChooser(intent, "Select MS Word File"), 438);
                    }
                }
            });
            builder.show();

        });

        uploadPostBtn.setOnClickListener(view -> {
            shortDescStr = String.valueOf(shortDescriptionET.getText());
            longDescStr = String.valueOf(longDescriptionET.getText());
            yearStr = String.valueOf(yearEt.getText());
            titleStr = String.valueOf(titleET.getText());

            if (isValidate()) {
                if (selectedPostCategory.isEmpty()) {
                    postTIL.setError("Please Select Category");
                    postCategory.requestFocus();
                    return;
                } else if (String.valueOf(titleET.getText()).isEmpty()) {
                    postTIL.setErrorEnabled(false);
                    titleTIL.setError("Please Select Category");
                    titleET.requestFocus();
                    return;
                } else {
                    postTIL.setErrorEnabled(false);
                    titleTIL.setErrorEnabled(false);
                    uploadToFirebase();
                }

            }
        });

        viewPost.setOnClickListener(view -> {
            startActivity(new Intent(UploadDocsActivity.this,ViewSelectionPastPaperActivity.class));
        });


    }

    private void uploadToFirebase() {


        Map fileImageBody = new HashMap();

        fileImageBody.put("filePath", myUri);
        fileImageBody.put("name", fileUri.getLastPathSegment());
        fileImageBody.put("type", checker);
        fileImageBody.put("shortDesc", shortDescStr);
        fileImageBody.put("longDesc", longDescStr);
        fileImageBody.put("title", titleStr);
        fileImageBody.put("year", yearStr);
        fileImageBody.put("category", selectedPostCategory);
        fileImageBody.put("QR", QR);


        Toast.makeText(getApplicationContext(), "Please Wait, We are uploading a file....", Toast.LENGTH_SHORT).show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.child(selectedPostCategory).child(yearStr).setValue(fileImageBody)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "QR code Post Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            pushnotifi();
                        } else {
                            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void pushnotifi() {

        Intent intent = new Intent(UploadDocsActivity.this, UploadDocsActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(UploadDocsActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(UploadDocsActivity.this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setTicker("Hearty365")
                .setContentTitle("Default notification")
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) UploadDocsActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());

//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.logo)
//                        .setContentTitle("Notifications Example")
//                        .setContentText("This is a test notification");
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//
//        // Add as notification
//        notificationManager.notify(0, builder.build());

    }

    private void openGallery() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        /*Intent pickImage = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        );
                        startActivityForResult(pickImage, REQ);*/


                        /*Intent gallery = new Intent();
                        gallery.setType("image/*");
                        gallery.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(gallery,2);*/
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private boolean isValidate() {
        return (validateLongDescription() && validateYear() && validateShortDescription());
    }

    private void setupListeners() {
        shortDescriptionET.addTextChangedListener(new TextFieldValidation(shortDescriptionET));
        longDescriptionET.addTextChangedListener(new TextFieldValidation(longDescriptionET));
        yearEt.addTextChangedListener(new TextFieldValidation(yearEt));
    }

    /**
     * 1) field must not be empty
     * 2) password length must not be greater than 80
     */
    private boolean validateShortDescription() {
        if (String.valueOf(shortDescriptionET.getText()).isEmpty()) {
            shortDescTIL.setError("Required Field!");
            shortDescriptionET.requestFocus();
            return false;
        } else if (String.valueOf(shortDescriptionET.getText()).length() > 80) {
            shortDescTIL.setError("Short Description can\'t more than 80 character long");
            shortDescriptionET.requestFocus();
            return false;
        } else {
            shortDescTIL.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * 1) field must not be empty
     * 2) password length must not be greater than 4000
     */
    private boolean validateLongDescription() {
        if (String.valueOf(longDescriptionET.getText()).isEmpty()) {
            longDescriptionTIL.setError("Required Field!");
            longDescriptionET.requestFocus();
            return false;
        } else if (String.valueOf(shortDescriptionET.getText()).length() > 4000) {
            longDescriptionTIL.setError("Short Description can\'t more than 80 character long");
            longDescriptionET.requestFocus();
            return false;
        } else {
            longDescriptionTIL.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateYear() {
        String yearStr = String.valueOf(yearEt.getText());
        if (yearStr.isEmpty()) {
            yearTIL.setError("Required Field!");
            yearEt.requestFocus();
            return false;
        } else {
            yearTIL.setErrorEnabled(false);
            int vYear = Integer.parseInt(yearStr);
            if (vYear < 2015) {
                yearTIL.setError("More than 2015 Required");
                yearEt.requestFocus();
                return false;
            } else {
                yearTIL.setErrorEnabled(false);
            }
        }

        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        String[] postCategoryStr = getResources().getStringArray(R.array.papers);
        ArrayAdapter<String> postCategoryAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, postCategoryStr);
        postCategory.setAdapter(postCategoryAdapter);
    }

    /**
     * applying text watcher on each text field
     */
    class TextFieldValidation implements TextWatcher {

        /*       TextInputEditText v;

               public TextFieldValidation(TextInputEditText view) {
                   this.v = view;
               }*/
        View v;

        public TextFieldValidation(View view) {
            this.v = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // checking ids of each text field and applying functions accordingly.
            switch (v.getId()) {
                case R.id.shortDescriptionET:
                    validateShortDescription();
                    break;
                case R.id.longDescriptionET:
                    validateLongDescription();
                    break;
                case R.id.yearEt:
                    validateYear();
                    break;

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    // for just image
/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri image = data.getData();
            uriStr = image.toString();
            *//*try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                setProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }*//*
            CropImage.activity(image)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                timestamp = "" + System.currentTimeMillis();
                imageRename = timestamp + new Random().nextInt(1000);

                Uri resultUri = result.getUri();
                storageReference = FirebaseStorage.getInstance().getReference("Profile Images");
                storageReference.child(imageRename).putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                resultUriStr = uri.toString();
                               *//* Picasso.with(getApplicationContext())
                                        .load(resultUri)
                                        .into(addPastPaper);*//*
                            }
                        });
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 438 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            /*if (!checker.equals("image")) {
                timestamp = "" + System.currentTimeMillis();
                imageRename = timestamp + new Random().nextInt(1000);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Document Files");
                StorageReference filePath = storageReference.child(imageRename + "." + checker);
                filePath.putFile(fileUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    myUri = uri.toString();
                                }
                            });
                        }
                    }
                });

            } else if (checker.equals("image")) {
                timestamp = "" + System.currentTimeMillis();
                imageRename = timestamp + new Random().nextInt(1000);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Files");
                StorageReference filePath = storageReference.child(imageRename);
                uploadTask = filePath.putFile(fileUri);
                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            myUri = uri.toString();
                                        }
                                    });
                                }
                            }
                        });
            } else {
                Toast.makeText(this, "Nothing Selected, Error", Toast.LENGTH_SHORT).show();
            }*/

            StorageReference storageReference = FirebaseStorage.getInstance().getReference("Files");
//        Uri uri = Uri.fromFile(new File(fileName));
            storageReference.putFile(fileUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                myUri = uri.toString();
                            }
                        });
                    }
                }
            });
        }
    }
}