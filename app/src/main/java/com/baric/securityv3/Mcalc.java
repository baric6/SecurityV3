package com.baric.securityv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Mcalc extends AppCompatActivity {

    private RecyclerView recyclerView;
    private mRecyclerAdapter adapter;
    private ArrayList<mCalcdbModel> main23 = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcalc);
        
        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
        main23.add(new mCalcdbModel("start time", "start miles", "start place", "end time", "end miles", "end place", "total miles"));
        

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new mRecyclerAdapter(this, main23);
        recyclerView.setAdapter(adapter);
        
        adapter.setOnItemClickLitener(new mRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(getApplicationContext(), "Index: " + String.valueOf(pos), Toast.LENGTH_SHORT).show();
            }
        });
        
    }
}