package com.project1.eventlogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.project1.eventlogs.receiver.NetworkStateChangeReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.project1.eventlogs.receiver.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class Attender_1 extends AppCompatActivity {
    static String username ="";
    static String title ="";
    static String menu ="";
    static String venue ="";
    static String Salutations ="";
    static String fromdate ="";
    static String todate ="";


    Spinner dropdown_1,dropdown_3;
    EditText name,event_title,event_venue;
    Button next;
    TextView from,to;
    private static final String TAG = "Attender_1";
    DatePickerDialog.OnDateSetListener mDateSetListener;
    DatePickerDialog.OnDateSetListener mDateSetListener1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attender_1);


        name = (EditText) findViewById(R.id.nameatt);
        event_title =(EditText) findViewById(R.id.title);
        event_venue = (EditText) findViewById(R.id.idvenu);

//===============================================================================================____SPINNER 1___==================================================

        dropdown_1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list1 = new ArrayList<>();
        list1.add("Mr.");
        list1.add("Ms.");
        list1.add("Mrs.");
        list1.add("Dr.");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown_1.setAdapter(adapter1);

        assert dropdown_1 != null;

        dropdown_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String spinnerstring2 = dropdown_1.getSelectedItem().toString();
                Salutations = spinnerstring2;

            }

            public String getSpin(String spinnerstring2) {
                return spinnerstring2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//---------------------------------------------------------------date start ----------------------------------------------------------------------
        from = findViewById(R.id.idfromdate);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Attender_1.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                from.setText(date);
            }
        };



        to = findViewById(R.id.idtodate);
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Attender_1.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener1,
                        year,month,day);
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                to.setText(date);
            }
        };

        //===========================================================date end==================================================================================
        dropdown_3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list3 = new ArrayList<>();
        list3.add("Faculty Developement Program");
        list3.add("Short term Training courses");
        list3.add("Online Certificate");
        list3.add("Seminar");
        list3.add("Workshop");
        list3.add("Conference");
        list3.add("Geust Lecture");
        list3.add("Training");
        list3.add("Others");


        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown_3.setAdapter(adapter3);

        assert dropdown_3 != null;

        dropdown_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String spinnerstring = dropdown_3.getSelectedItem().toString();

                //Toast.makeText(Attender_1.this, "Selected:" + spinnerstring, Toast.LENGTH_SHORT).show();

            }

            public String getSpin(String spinnerstring3) {
                return spinnerstring3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next = findViewById(R.id.page1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Salutations = dropdown_1.getSelectedItem().toString();
                username = name.getText().toString().trim();
                title = event_title.getText().toString().trim();
                venue = event_venue.getText().toString().trim();
                menu = dropdown_3.getSelectedItem().toString();
                fromdate = from.getText().toString().trim();
                todate = to.getText().toString().trim();

                Intent i = new Intent(Attender_1.this,Attender_final.class);
                startActivity(i);
            }
        });


    }
    
}