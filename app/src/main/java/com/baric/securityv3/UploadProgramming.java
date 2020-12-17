package com.baric.securityv3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadProgramming#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadProgramming extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button programmingAdd;
    private EditText programmingTitle;
    private EditText programmingComment;
    private EditText programmingUrl;
    private Spinner programmingSpinner;
    private String f;
    private ImageView ProgrammingImage;
    public UploadProgramming() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadProgramming.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadProgramming newInstance(String param1, String param2) {
        UploadProgramming fragment = new UploadProgramming();
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
        View view = inflater.inflate(R.layout.fragment_upload_programming, container, false);
        //make data base for programming and connect
        //connect spinner

        programmingAdd = view.findViewById(R.id.programmingAdd);
        programmingTitle = view.findViewById(R.id.programmingTitle);
        programmingComment = view.findViewById(R.id.programmingComment);
        programmingUrl = view.findViewById(R.id.programmingUrl);
        programmingSpinner = view.findViewById(R.id.programmingSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.programmingTopics, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programmingSpinner.setAdapter(adapter);

        programmingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Programming");
        final FireBaseHelper helper = new FireBaseHelper(ref);

        //when add button is clicked
        programmingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ADDED", Toast.LENGTH_LONG).show();

                //makes a new object to add to database
                ListSecDBModel newEntry = new ListSecDBModel();
                newEntry.setTitle(programmingTitle.getText().toString());
                newEntry.setComment(programmingComment.getText().toString());
                newEntry.setkeyRefrence(programmingUrl.getText().toString());
                newEntry.setTopic(f);

                //call the helper method to save entry
                helper.save(newEntry);

                //finish();
            }
        });

        return view;
    }
}