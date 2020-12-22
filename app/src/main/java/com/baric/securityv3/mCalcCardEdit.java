package com.baric.securityv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;


public class mCalcCardEdit extends AppCompatActivity {

    private EditText mTime;
    private EditText mMiles;
    private EditText mPlace;
    private EditText mStopTime;
    private EditText mStopMiles;
    private EditText mEndPlace;
    
    
    private Button mRecord;
    private Button delRecord;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_calc_card_edit);

        mTime = findViewById(R.id.upStartTime);
        mMiles = findViewById(R.id.mMiles);
        mPlace = findViewById(R.id.mPlace);
        mStopTime = findViewById(R.id.upEndTime);
        mStopMiles = findViewById(R.id.upEndMiles);
        mEndPlace = findViewById(R.id.upEndPlace);

        mRecord = findViewById(R.id.mRecord);
        delRecord = findViewById(R.id.delRecord);
        
        
        //getting intent from mainActivity when card is clicked
        Intent fromMcard = getIntent();
        String id = fromMcard.getStringExtra("idz");
        String startTime = fromMcard.getStringExtra("startTime");
        String startMiles = fromMcard.getStringExtra("startMiles");
        String startPlace =  fromMcard.getStringExtra("startPlace");
        String endTime = fromMcard.getStringExtra("endTime");
        String endMiles = fromMcard.getStringExtra("endMiles");
        String endPlace = fromMcard.getStringExtra("endPlace");
        String total = fromMcard.getStringExtra("total");

        Date currentTime = Calendar.getInstance().getTime();

        mTime.setText(startTime);
        mMiles.setText(startMiles);
        mPlace.setText(startPlace);
        mStopTime.setText(currentTime.toString());
        mStopMiles.setText(endMiles);
        mEndPlace.setText(endPlace);
        
        

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("mileage");
        final mCalcFirebaseHelper helper = new mCalcFirebaseHelper(ref);

        delRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                helper.delete(id);
                
                finish();
                //Toast.makeText(getApplicationContext(), "Item has been Deleted", Toast.LENGTH_LONG).show();
            }
        });



        //when add button is clicked
        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_LONG).show();
               

                //makes a new object to add to database
                mCalcdbModel newEntry = new mCalcdbModel();
                newEntry.setStartTime(startTime);
                newEntry.setStartmiles(mMiles.getText().toString());
                newEntry.setStartPlace(mPlace.getText().toString());
                newEntry.setEndTime(mStopTime.getText().toString());
                newEntry.setEndMiles(mStopMiles.getText().toString());
                newEntry.setEndPlace(mEndPlace.getText().toString());
                newEntry.setTotalMiles(String.valueOf(Integer.valueOf(newEntry.getEndMiles()) + Integer.valueOf(newEntry.getStartmiles())*-1));

                //call the helper method to save entry
                helper.update(id,newEntry);

                finish();
            }
        });
    }
}