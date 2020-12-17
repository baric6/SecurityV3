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
 * Use the {@link UploadSecurity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadSecurity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button modAdd;
    private EditText modTitle;
    private EditText modComment;
    private EditText modUrl;
    private Spinner modSpinner;
    private String f;
    private ImageView imageView;
    public UploadSecurity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadSecurity.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadSecurity newInstance(String param1, String param2) {
        UploadSecurity fragment = new UploadSecurity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //actions go here
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //display/view items go here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_security, container, false);

        //widgets
        modAdd = view.findViewById(R.id.modAdd);
        modTitle = view.findViewById(R.id.modTitle);
        modComment = view.findViewById(R.id.modComment);
        modUrl = view.findViewById(R.id.modUrl);
        modSpinner = view.findViewById(R.id.modSpinner);
        imageView = view.findViewById(R.id.imageView3);

        //broke null object
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.topics, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modSpinner.setAdapter(adapter);

        modSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //calls to database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notes");
        final FireBaseHelper helper = new FireBaseHelper(ref);

        //when add button is clicked
        modAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ADDED", Toast.LENGTH_LONG).show();

                //makes a new object to add to database
                ListSecDBModel newEntry = new ListSecDBModel();
                newEntry.setTitle(modTitle.getText().toString());
                newEntry.setComment(modComment.getText().toString());
                newEntry.setkeyRefrence(modUrl.getText().toString());
                newEntry.setTopic(f);

                //call the helper method to save entry
                helper.save(newEntry);

                //finish();
            }
        });

        return view;
    }
}