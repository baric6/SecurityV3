package com.baric.securityv3;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
 * Use the {@link Programming#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Programming extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private ProgrammingRecyclerAdapter adapter;
    private final ArrayList<ProgrammingdbModel> main1 = new ArrayList<>();
    private ProgrammingHelper helper;
    private DatabaseReference reference;
    private EditText proSearch;
    
    //public Programming() {
        // Required empty public constructor
    //}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Programming.
     */
    // TODO: Rename and change types and number of parameters
    public static Programming newInstance(String param1, String param2) {
        Programming fragment = new Programming();
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

        reference = FirebaseDatabase.getInstance().getReference().child("Programming");
        reference.keepSynced(true);
        helper = new ProgrammingHelper(reference);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    dataSnapshot.getValue();
                    fetchData();

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
    
    private ArrayList<ProgrammingdbModel> fetchData() {
        //need this wrapper helps to talk to the firebase db
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //clears array of objects
                main1.clear();

                //loops through the firebase db
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //selects the class i want to use as a model
                    ProgrammingdbModel notes = ds.getValue(ProgrammingdbModel.class);
                    //sets keys to there correlating objects
                    notes.setId(ds.getKey());
                    //add object to array of objects
                    main1.add(notes);
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
        return main1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_programming, container, false);

        recyclerView = view.findViewById(R.id.programmingRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProgrammingRecyclerAdapter(getContext(), main1);
        recyclerView.setAdapter(adapter);
        
        adapter.setOnItemClickLitener(new ProgrammingRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent toUpdateSec = new Intent(getContext(), ProgrammingUpdateActivity.class);
                toUpdateSec.putExtra("idPro", main1.get(pos).getId());
                toUpdateSec.putExtra("titPro", main1.get(pos).getTitle());
                toUpdateSec.putExtra("disPro", main1.get(pos).getComment());
                toUpdateSec.putExtra("topicPro", main1.get(pos).getTopic());
                toUpdateSec.putExtra("urlPro", main1.get(pos).getKeyRefrence());
                startActivity(toUpdateSec);
            }
        });

        proSearch = view.findViewById(R.id.proSearch);
        proSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();

            }
        });

        return view;
    }

}