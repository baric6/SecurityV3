package com.baric.securityv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Mcalc extends AppCompatActivity {

    private RecyclerView recyclerView;
    private mRecyclerAdapter adapter;
    private ArrayList<mCalcdbModel> main23 = new ArrayList<>();
    private mCalcFirebaseHelper helper;
    private DatabaseReference reference;

    private EditText mMiles;
    private EditText mPlace;
    private Button mRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcalc);

        mMiles = findViewById(R.id.mMiles);
        mRecord = findViewById(R.id.mRecord);
        mPlace = findViewById(R.id.mPlace);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("mileage");
        mCalcFirebaseHelper helper = new mCalcFirebaseHelper(ref);

        //when add button is clicked
        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_LONG).show();
                Date currentTime = Calendar.getInstance().getTime();

                //makes a new object to add to database
                mCalcdbModel newEntry = new mCalcdbModel();
                newEntry.setStartTime(currentTime.toString());
                newEntry.setStartmiles(mMiles.getText().toString());
                newEntry.setStartPlace(mPlace.getText().toString());
                newEntry.setEndTime("N/A");
                newEntry.setEndMiles("0");
                newEntry.setEndPlace("N/A");
                newEntry.setTotalMiles(String.valueOf(Integer.valueOf(newEntry.getEndMiles()) + Integer.valueOf(newEntry.getStartmiles())));

                //call the helper method to save entry
                helper.save(newEntry);

                //finish();
            }
        });

//        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
//        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
//        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
//        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
//        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
//        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
//        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
//        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
//        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
//        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
//        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
//        

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new mRecyclerAdapter(this, main23);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new mRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent toCardEdit = new Intent(getApplicationContext(), mCalcCardEdit.class);
                toCardEdit.putExtra("idz", main23.get(pos).getId());
                toCardEdit.putExtra( "startTime" ,main23.get(pos).getStartTime());
                toCardEdit.putExtra( "startMiles" ,main23.get(pos).getStartmiles());
                toCardEdit.putExtra( "startPlace" ,main23.get(pos).getStartPlace());
                toCardEdit.putExtra("endTime", main23.get(pos).getEndTime());
                toCardEdit.putExtra("endMiles", main23.get(pos).getEndMiles());
                toCardEdit.putExtra("endPlace", main23.get(pos).getEndPlace());
                toCardEdit.putExtra("total", main23.get(pos).getTotalMiles());
                startActivity(toCardEdit);
            }
        });
        
        reference = FirebaseDatabase.getInstance().getReference().child("mileage");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    dataSnapshot.getValue();
                    fetchData();
                    //adapter = new RecyclerAdapter(recyclerView, getApplicationContext(),main);
                    //recyclerView.setAdapter(adapter);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "2 " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "3 " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<mCalcdbModel> fetchData() {
        //need this wrapper helps to talk to the firebase db
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //clears array of objects
                main23.clear();

                //loops through the firebase db
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //selects the class i want to use as a model
                    mCalcdbModel notes = ds.getValue(mCalcdbModel.class);
                    //sets keys to there correlating objects
                    notes.setId(ds.getKey());
                    //add object to array of objects
                    main23.add(notes);
                    adapter.notifyDataSetChanged();
                }

                //shows what is in the database if any
                //Toast.makeText(getApplicationContext(), main.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //returns list of objects
        return main23;
    }
}