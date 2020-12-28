package com.baric.securityv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProgrammingUpdateActivity extends AppCompatActivity {

    private TextView id;
    private EditText proTitle;
    private EditText proName;
    private EditText proDetails;
    private EditText proUrl;
    private Button proUpdate;
    private Button proDelete;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_update);

        Intent fromSecCard = getIntent();
        String idz =fromSecCard.getStringExtra("idPro");
        String titSec = fromSecCard.getStringExtra("titPro");
        String disSec = fromSecCard.getStringExtra("disPro");
        String topicSec = fromSecCard.getStringExtra("topicPro");
        String urlSec = fromSecCard.getStringExtra("urlPro");

        id = findViewById(R.id.proPos);
        proTitle = findViewById(R.id.proTitle);
        proName = findViewById(R.id.proName);
        proDetails = findViewById(R.id.proDetails);
        proUrl = findViewById(R.id.proUrl);

        proUpdate = findViewById(R.id.proUpdate);
        proDelete = findViewById(R.id.proDelete);

        id.setText(idz);
        proTitle.setText(titSec);
        proName.setText(disSec);
        proUrl.setText(urlSec);
        proDetails.setText(topicSec);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Programming");
        final ProgrammingHelper helper = new ProgrammingHelper(ref);


        proUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Update Clicked", Toast.LENGTH_SHORT).show();

                //makes a new object to add to database
                ProgrammingdbModel newEntry1 = new ProgrammingdbModel();
                newEntry1.setTitle(proTitle.getText().toString());
                newEntry1.setComment(proName.getText().toString());
                newEntry1.setKeyRefrence(proUrl.getText().toString());
                newEntry1.setTopic(proDetails.getText().toString());


                helper.update(idz,newEntry1);

                finish();
            }
        });

        proDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Delete Clicked", Toast.LENGTH_SHORT).show();

                helper.delete(idz);

                finish();
            }
        });
    }
}