package com.project1.eventlogs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.content.SharedPreferences;

public class SplashScreen extends AppCompatActivity {



    public static boolean i;
    private static int S_SCREEN = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);



        SharedPreferences prefs = getApplicationContext().getSharedPreferences("SIGNINACTIVITY", 0);
        SharedPreferences.Editor editor = prefs.edit();
        i=getSharedPreferences("SIGNINACTIVITY", 0).getBoolean("hasLogin", Boolean.parseBoolean(""));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i2 = new Intent(SplashScreen.this,GoogleSignIn.class);
                Intent i1 = new Intent(SplashScreen.this,ProfileView.class);
                if(i==true) {
                    startActivity(i1);
                    overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
                    finish();
                }
                else{
                    startActivity(i2);
                    overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
                    finish();
                }

            }
        },S_SCREEN);

    }
}
