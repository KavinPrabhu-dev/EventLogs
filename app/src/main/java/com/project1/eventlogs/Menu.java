package com.project1.eventlogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.project1.eventlogs.receiver.NetworkStateChangeReceiver;

import static com.project1.eventlogs.receiver.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class Menu extends AppCompatActivity {
    Button atttender,admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        atttender = findViewById(R.id.attender);
        atttender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,Attender_1.class);
                startActivity(i);
            }
        });

        admin = findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,ListItem.class);
                startActivity(i);
            }
        });
    }
}