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
 * Use the {@link Security#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Security extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private FireBaseHelper helper;
    private DatabaseReference reference;
    private final ArrayList<ListSecDBModel> main = new ArrayList<>();

    public Security() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Security.
     */
    // TODO: Rename and change types and number of parameters
    public static Security newInstance(String param1, String param2) {
        Security fragment = new Security();
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

        reference = FirebaseDatabase.getInstance().getReference().child("notes");
        reference.keepSynced(true);
        helper = new FireBaseHelper(reference);

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

    private ArrayList<ListSecDBModel> fetchData() {
        //need this wrapper helps to talk to the firebase db
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //clears array of objects
                main.clear();

                //loops through the firebase db
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //selects the class i want to use as a model
                    ListSecDBModel notes = ds.getValue(ListSecDBModel.class);
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
        View view = inflater.inflate(R.layout.fragment_security, container, false);
        recyclerView = view.findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerAdapter(getContext(), main);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent toUpdateSec = new Intent(getContext(), ActivityUpdateSecurity.class);
                toUpdateSec.putExtra("idSec", main.get(pos).getId());
                toUpdateSec.putExtra("titSec", main.get(pos).getTitle());
                toUpdateSec.putExtra("disSec", main.get(pos).getComment());
                toUpdateSec.putExtra("topicSec", main.get(pos).getTopic());
                toUpdateSec.putExtra("urlSec", main.get(pos).getkeyRefrence());
                startActivity(toUpdateSec);
            }
        });
        

        return view;


    }
}