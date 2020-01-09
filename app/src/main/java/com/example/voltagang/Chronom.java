package com.example.voltagang;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.voltagang.R;

public class Chronom extends AppCompatActivity {

    private Chronometer chrono;
    private long pauseOffset;
    private boolean running;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chrono);

        chrono = findViewById(R.id.chrono);

    }

    public void startChronometer(View v){
        if(!running){
            chrono.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chrono.start();
            running =true;
        }

    }
    public void pauseChronometer(View v){
        if(running){
            chrono.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chrono.getBase();
            running = false;
        }

    }
    public void resetChronometer(View v){

        chrono.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0 ;
    }

}
