package com.baric.securityv3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tcalc extends AppCompatActivity {

    EditText clockIn;
    EditText clockOut;
    Button btnCalc;
    TextView result;
    TextView timeToEqual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcalc);

        clockIn = findViewById(R.id.inTime);
        clockOut = findViewById(R.id.outTime);
        btnCalc = findViewById(R.id.calc);
        result = findViewById(R.id.result);
        timeToEqual = findViewById(R.id.minToEven);


        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button was clicked", Toast.LENGTH_LONG).show();

                String f = clockIn.getText().toString();
                String l = clockOut.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

                try {
                    Date d1 = sdf.parse(f);
                    Date d2 = sdf.parse(l);

                    long diff = d1.getTime() - d2.getTime();
                    long timeBetween = (int) diff / (1000 * 60);


                    int timeto = Integer.parseInt(String.valueOf(timeBetween)) % 6 - 6 * -1;

                    result.setText(timeBetween * -1 + " Min");

                    if (timeto == 6) {
                        timeToEqual.setText("clock out now");
                    } else {
                        timeToEqual.setText(timeto + "min left till clockout");
                    }


                } catch (ParseException e) {
                    e.printStackTrace();


                }
            }
        });
    }


}
