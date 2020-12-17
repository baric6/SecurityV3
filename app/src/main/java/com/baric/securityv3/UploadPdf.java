package com.baric.securityv3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadPdf#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadPdf extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Uri uri;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView path;
    private Button btnChooseFile;
    private Button btnUpload;
    private ProgressBar uploadLoading;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public UploadPdf() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadPdf.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadPdf newInstance(String param1, String param2) {
        UploadPdf fragment = new UploadPdf();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_pdf, container, false);

        path = view.findViewById(R.id.path);
        btnChooseFile = view.findViewById(R.id.btnChooseFile);
        btnUpload = view.findViewById(R.id.btnUpload);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        uploadLoading = view.findViewById(R.id.uploadLoading);
        uploadLoading.setVisibility(View.INVISIBLE);

        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectFile();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    uploadLoading.setVisibility(View.VISIBLE);
                    fileUpload();


                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }


    private void selectFile() {
        Intent chooseFile = new Intent();
        chooseFile.setType("csv/*");
        chooseFile.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(chooseFile, 10);
    }

    private void fileUpload() {
        //calling storage reference
        StorageReference storageReference = storage.getReference();

        //name of file used with Fire-base storage with file type .xxx
        final String fileName = uri.getLastPathSegment();


        //This makes a sub dir in root of fire-base storage called files the name of the file is time it
        //was uploaded
        storageReference.child("files")
                .child(fileName)
                .putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // Get a URL to the uploaded content
                        Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                        while (!downloadUrl.isComplete()) ;
                        final Uri url = downloadUrl.getResult();
                        ///////////////////////////////////////////////////////////////////

                        String shortenedUri = convertFileName(uri);

                        //for real-time database add
                        databaseReference = database.getReference();
                        String key = databaseReference.push().getKey();
                        databaseReference.child("storageLinks").child(key).child("realFileName").setValue(uri.getLastPathSegment());
                        databaseReference.child("storageLinks").child(key).child("fileName").setValue(shortenedUri);
                        databaseReference.child("storageLinks").child(key).child("key").setValue(key);
                        databaseReference.child("storageLinks").child(key).child("url").setValue(url.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    //path.setText(shortenedUri);
                                    uploadLoading.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getContext(), "File uploaded", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        uploadLoading.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "File uploaded failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

    /*private String getPathExtention(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            path.setText(data.getData().getLastPathSegment());
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectFile();
        } else {
            Toast.makeText(getContext(), "Please provide permission", Toast.LENGTH_LONG).show();
        }
    }


    public String convertFileName(Uri uri) {
        String newUri = "";
        if (uri != null) {
            String urii = "";
            urii = uri.getLastPathSegment();

            newUri = urii.replaceAll("[^a-zA-Z0-9]", " ");
            Toast.makeText(getContext(), newUri, Toast.LENGTH_LONG).show();
        }
        return newUri;
    }


}