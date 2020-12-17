package com.baric.securityv3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pdf#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pdf extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView1;
    private StorageAdapter adapter;
    private DatabaseReference reference;
    private final ArrayList<UrlDataModel> main = new ArrayList<>();
    public Pdf() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Pdf.
     */
    // TODO: Rename and change types and number of parameters
    public static Pdf newInstance(String param1, String param2) {
        Pdf fragment = new Pdf();
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

        reference = FirebaseDatabase.getInstance().getReference().child("storageLinks");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    dataSnapshot.getValue();
                    fetchData();
                    //adapter = new RecyclerAdapter(recyclerView, getApplicationContext(),main);
                    //recyclerView.setAdapter(adapter);

                } catch (Exception e) {
                    Toast.makeText(getContext(), "2 " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "3 " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    private ArrayList<UrlDataModel> fetchData() {
        //need this wrapper helps to talk to the firebase db
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //clears array of objects
                main.clear();

                //loops through the firebase db
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //selects the class i want to use as a model
                    UrlDataModel notes = ds.getValue(UrlDataModel.class);
                    //sets keys to there correlating objects
                    notes.setId(ds.getKey());

                    //add object to array of objects
                    main.add(notes);
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
        return main;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pdf, container, false);

        try {

            recyclerView1 = view.findViewById(R.id.recyclerView);
            recyclerView1.setHasFixedSize(true);
            recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

            //the helper array holds all the urls
            adapter = new StorageAdapter(recyclerView1, getContext(), main);
            recyclerView1.setAdapter(adapter);


        } catch (Exception e) {
            Toast.makeText(getContext(), "1 " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        adapter.setOnItemClickLitener(new StorageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {

                //get position
                //Toast.makeText(getApplicationContext(), String.valueOf(pos) + main.get(pos).getUrl(), Toast.LENGTH_LONG).show();

                Intent viewData = new Intent(getContext(), ShowPdf.class);
                //main array holds all the data foe the file database
                viewData.putExtra("url", main.get(pos).getUrl());
                startActivity(viewData);
            }
        });
        return view;
    }
}