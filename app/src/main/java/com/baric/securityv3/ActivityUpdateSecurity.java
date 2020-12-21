package com.baric.securityv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityUpdateSecurity extends AppCompatActivity {

    private TextView id;
    private EditText secTitle;
    private EditText secName;
    private EditText secDetails;
    private EditText secUrl;
    private Button secUpdate;
    private Button secDelete;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_security);

        Intent fromSecCard = getIntent();
        String idz =fromSecCard.getStringExtra("idSec");
        String titSec = fromSecCard.getStringExtra("titSec");
        String disSec = fromSecCard.getStringExtra("disSec");
        String topicSec = fromSecCard.getStringExtra("topicSec");
        String urlSec = fromSecCard.getStringExtra("urlSec");
        
        id = findViewById(R.id.posSec);
        secTitle = findViewById(R.id.secTitle);
        secName = findViewById(R.id.secName);
        secDetails = findViewById(R.id.secDetails);
        secUrl = findViewById(R.id.secUrl);
        
        secUpdate = findViewById(R.id.secUpdate);
        secDelete = findViewById(R.id.secDelete);
        
        id.setText(idz);
        secTitle.setText(titSec);
        secName.setText(disSec);
        secUrl.setText(urlSec);
        secDetails.setText(topicSec);
        

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notes");
        final FireBaseHelper helper = new FireBaseHelper(ref);
        
        
        secUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Update Clicked", Toast.LENGTH_SHORT).show();

                //makes a new object to add to database
                ListSecDBModel newEntry1 = new ListSecDBModel();
                newEntry1.setTitle(secTitle.getText().toString());
                newEntry1.setComment(secName.getText().toString());
                newEntry1.setkeyRefrence(secUrl.getText().toString());
                newEntry1.setTopic(secDetails.getText().toString());
                
                
                helper.update(idz,newEntry1);
                
                finish();
            }
        });
        
        secDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Delete Clicked", Toast.LENGTH_SHORT).show();
                
                helper.delete(idz);

                finish();
            }
        });
        
        
    }
}