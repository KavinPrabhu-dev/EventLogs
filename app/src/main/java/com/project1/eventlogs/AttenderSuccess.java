package com.project1.eventlogs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.ImageView;
import java.util.Timer;
import java.util.TimerTask;

public class AttenderSuccess extends AppCompatActivity {

    ImageView done;
    Timer timer;
    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;
    MediaPlayer check;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attender_success);

        check=MediaPlayer.create(AttenderSuccess.this,R.raw.check);
        check.start();

        done = findViewById(R.id.done);

        Drawable drawable = done.getDrawable();

        if(drawable instanceof AnimatedVectorDrawableCompat){
            avd = (AnimatedVectorDrawableCompat) drawable;
            avd.start();
        }else if(drawable instanceof AnimatedVectorDrawable){
            avd2 = (AnimatedVectorDrawable) drawable;
            avd2.start();
        }
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(AttenderSuccess.this,Menu.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }

    @Override
    protected void onPause(){
        super.onPause();
        check.release();
        finish();
    }
    }
