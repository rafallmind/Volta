package com.example.voltagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voltagang.Model.Session;
import com.example.voltagang.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AnalyseSportActivity extends AppCompatActivity {

    //Composants XML
    private TextView laDate, leTemps, leRessenti, TvTip;
    private EditText addTemps,addRessenti;
    private Button btnAdd,btnTip;
    private SeekBar seekBar;

    private Bundle b;
    private String key;

    private Context mContext;

    //Référence FireBase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session);

        mContext =this.getApplicationContext();

        //Initialisation des composants du XML associés
        laDate = findViewById(R.id.laDate);
        leTemps = findViewById(R.id.leTemps);
        leRessenti = findViewById(R.id.leRessenti);
        addTemps = findViewById(R.id.addTime);
        addRessenti = findViewById(R.id.addFeeling);
        addRessenti.setActivated(false);
        btnAdd = findViewById(R.id.btnAdd);
        seekBar = findViewById(R.id.seekBar);
        btnTip = findViewById(R.id.btnTip);
        TvTip = findViewById(R.id.TvTip);
        //force input type
        leTemps.setInputType(InputType.TYPE_CLASS_NUMBER);
        leRessenti.setInputType(InputType.TYPE_CLASS_NUMBER);

        //Ca c'est stylé
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Chargement...");

        //Initialisation FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        b = getIntent().getExtras();
        key = b.getString("key");


        //affichage derniere session


        mRef.child("users").child(mAuth.getCurrentUser().getUid()).child("mesSports").child(key).child("mesSessions").limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    if(child.exists()){
                        laDate.setText("Date: " + child.child("date").getValue().toString());
                        leTemps.setText("Time: " + child.child("temps").getValue().toString());
                        leRessenti.setText("Feeling: " + child.child("ressenti").getValue().toString());
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            // When Progress value changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                addRessenti.setText(progressValue+"");

            }

            // Notification that the user has started a touch gesture.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("start", "Started tracking seekbar");
            }

            // Notification that the user has finished a touch gesture
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                addRessenti.setText(progress + "");

            }

        });


        btnTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRef.child("users").child(mAuth.getCurrentUser().getUid()).child("mesSports").child(key).child("mesSessions").limitToLast(2).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                        int z=0;
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            if(child.exists()){
                                z++;
                                laDate.setText("Date: " + child.child("date").getValue().toString());
                                leTemps.setText("Time: " + child.child("temps").getValue().toString());
                                leRessenti.setText("Feeling: " + child.child("ressenti").getValue().toString());
                            }

                        }

                        if(z<2){
                            TvTip.setText("Not enough data");
                        }else{
                            TvTip.setText("ouiiiii le conseil merci trop bien");
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        //ajout de la perf
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar rightNow = Calendar.getInstance();


                final int jour = rightNow.get(Calendar.DAY_OF_MONTH);
                final int mois = rightNow.get(Calendar.MONTH) + 1;
                final int annee = rightNow.get(Calendar.YEAR);


                String jourr, moiss;


                if(jour<10){
                     jourr = "0"+jour;
                }else{
                     jourr=jour+"";
                }

                if(jour<10){
                     moiss = "0"+mois;
                }else{
                     moiss=mois+"";
                }



                final String date = (jourr+"/"+moiss+"/"+annee);


                final String temps = addTemps.getText().toString();
                final String ressenti = addRessenti.getText().toString();


                if(temps.isEmpty()){
                    addTemps.setError("Veuillez remplir ce champ");
                    addTemps.setFocusable(true);
                }
                else if(ressenti.isEmpty() || Integer.parseInt(addRessenti.getText().toString()) >9){
                    addRessenti.setError("Veuillez remplir ce champ avec une valeur adpatée");
                    addRessenti.setFocusable(true);
                }
                else{
                    Session session = new Session(
                            date,
                            temps,
                            ressenti
                    );
                    HashMap sessionMap = new HashMap();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("mesSports").child(key).child("mesSessions");
                    sessionMap.put(ref.push().getKey(), session);
                    ref.updateChildren(sessionMap);

                    addTemps.getText().clear();
                    addRessenti.getText().clear();

                    }
            }
        });


    }

}
