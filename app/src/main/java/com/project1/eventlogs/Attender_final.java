package com.project1.eventlogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.*;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

import com.google.android.material.snackbar.Snackbar;
import com.project1.eventlogs.Attender_1;
import com.project1.eventlogs.receiver.NetworkStateChangeReceiver;

import static com.project1.eventlogs.receiver.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;


public class Attender_final extends AppCompatActivity implements View.OnClickListener {

    EditText attendername;
    EditText attenderevent;
    Button buttonaddtodatabase;
    EditText textView;

    public String salutations = Attender_1.Salutations;
    final String username = salutations + Attender_1.username;
    final String title = Attender_1.title;
    final String venue = Attender_1.venue;
    final String menu = Attender_1.menu;
    final String fromdate = Attender_1.fromdate;
    final String todate = Attender_1.todate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attender_final);



        textView = findViewById(R.id.q6);
        textView.setVisibility(View.GONE);

        RadioButton rdb1 = (RadioButton) findViewById(R.id.rg5r1);
        rdb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                if (checked){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("No");
                }
                else{
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });

        RadioButton rdb = (RadioButton) findViewById(R.id.rg5r2);
        rdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                if (checked){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("");
                }
                else{
                    textView.setVisibility(View.INVISIBLE);
                }
            }
        });

        attendername = (EditText) findViewById(R.id.objective);
        attenderevent = (EditText) findViewById(R.id.outcome);
        buttonaddtodatabase = findViewById(R.id.attender);
        buttonaddtodatabase.setOnClickListener(this);

        Toast.makeText(Attender_final.this, "Hi!" + username +"Please fill the form Correctly!", Toast.LENGTH_SHORT).show();

    }


    private void addDataToSheet() {
        final ProgressDialog loading = ProgressDialog.show(this, "Saving Data ", "Please wait..!");
        final String objective = attendername.getText().toString().trim();
        final String outcome = attenderevent.getText().toString().trim();
        final String amount = textView.getText().toString().trim();

        StringRequest stringRequest = new StringRequest (Request.Method.POST, "https://script.google.com/macros/s/AKfycbzBT-rVkxqThYFFuLz_Amp27rWIdkv0quryPbBsHw1ykhOenDjR-m5-EQ/exec",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            Toast.makeText(Attender_final.this, response, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), AttenderImageUpload.class);
                            startActivity(intent);
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                parmas.put("action", "addItem");
                parmas.put("username", username);
                parmas.put("title", title);
                parmas.put("menu", menu);
                parmas.put("venue", venue);
                parmas.put("fromdate", fromdate);
                parmas.put("todate", todate);
                parmas.put("fund", amount);
                parmas.put("objective", objective);
                parmas.put("outcome", outcome);

                return parmas;
            }
        };

        int socketTimeOut = 10000;// we can change this .. here it is 10 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(Attender_final.this);

        queue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        if(v==buttonaddtodatabase){
            addDataToSheet();
        }
    }


}