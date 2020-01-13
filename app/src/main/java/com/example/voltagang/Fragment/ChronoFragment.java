package com.example.voltagang.Fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.voltagang.R;

public class ChronoFragment extends Fragment {

    //Composants XML
    private Chronometer chrono;
    private long pauseOffset;
    private boolean running;

    private Button start;
    private Button pause;
    private Button reset;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chrono, null);

        start = root.findViewById(R.id.start);
        pause = root.findViewById(R.id.stop);
        reset = root.findViewById(R.id.reset);
        chrono = root.findViewById(R.id.chrono);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!running){
                   chrono.setBase(SystemClock.elapsedRealtime() - pauseOffset);

                    chrono.start();
                    running =true;
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    chrono.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chrono.getBase();
                    running = false;
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chrono.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0 ;
            }
        });

        return root;
    }
}
