package com.example.voltagang;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.voltagang.Model.FiveKM;
import com.example.voltagang.Model.Gender;
import com.example.voltagang.Model.Marathon;
import com.example.voltagang.Model.SemiMarathon;
import com.example.voltagang.Model.Session;
import com.example.voltagang.Model.Stamina;
import com.example.voltagang.Model.TenKM;
import com.example.voltagang.Model.TwoPointFiveKM;
import  com.example.voltagang.Model.FiftyKM;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;


public class AnalyseSportActivity extends AppCompatActivity {

    //Composants XML
    private TextView laDate, leTemps, leRessenti, TvTip;
    private EditText addTemps,addRessenti;
    private Button btnAdd,btnTip;
    private SeekBar seekBar;

    private Bundle b;
    private String key;

    private Stamina stamina;

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

        stamina = new Stamina(getApplicationContext());

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



        mRef.child("users").child(mAuth.getCurrentUser().getUid()).child("mesSports").child(key).child("mesSessions").addValueEventListener(new ValueEventListener() {
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
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int z = 0;
                        Session[] array = new Session[2];

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if (child.child("date").exists() && child.child("temps").exists() && child.child("ressenti").exists()) {
                                Log.i("nb Z", "Bonjour je suis la " + z);
                                laDate.setText("Date: " + child.child("date").getValue().toString());
                                leTemps.setText("Time: " + child.child("temps").getValue().toString());
                                leRessenti.setText("Feeling: " + child.child("ressenti").getValue().toString());
                                Session cons = new Session();
                                cons.setDate(child.child("date").getValue().toString());
                                cons.setRessenti(child.child("ressenti").getValue().toString());
                                cons.setTemps(child.child("temps").getValue().toString());
                                array[z] = cons;
                                z++;
                            }

                        }
                        if (z < 2) {
                            TvTip.setText("Not enough data");
                        } else {
                            //ici on impl les conseils
                            Session last = array[1];
                            Session last2 = array[0];

                            if(last == null){
                                Log.i("null", "last est null");
                            }
                            if(last2 == null){
                                Log.i("null", "last2 est null");
                            }

                            final Session finalLast = last;
                            final Session finalLast1 = last2;
                            final Gender gender = null;
                            mRef.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Gender gender = null;
                                    if(dataSnapshot.child("sexe").exists()) {
                                        Log.i("Yes","Exist cest deja ca");
                                        Log.i("Contenu de sexe", dataSnapshot.child("sexe").getValue().toString());
                                        if (dataSnapshot.child("sexe").getValue() == Gender.Male.toString()) {
                                            gender = Gender.Male;
                                        } else {
                                            gender = Gender.Female;
                                        }
                                    }
                                    int perf = new Integer(finalLast.getTemps());
                                    if(gender == null){
                                        Log.i("C'est la mort", "null");
                                    }
                                    else {
                                        switch (key) {
                                            case "Marathon":
                                                TvTip.setText(Marathon.comparerPerformance(gender, perf, new Integer(finalLast.getRessenti()), new Integer(finalLast1.getTemps()), new Integer(finalLast1.getRessenti())));
                                                break;
                                            case "10KM":
                                                TvTip.setText(TenKM.comparerPerformance(gender, perf, new Integer(finalLast.getRessenti()), new Integer(finalLast1.getTemps()), new Integer(finalLast1.getRessenti())));
                                                break;
                                            case "2,5KM":
                                                TvTip.setText(TwoPointFiveKM.comparerPerformance(gender, perf, new Integer(finalLast.getRessenti()), new Integer(finalLast1.getTemps()), new Integer(finalLast1.getRessenti())));
                                                break;
                                            case "5KM":
                                                TvTip.setText(FiveKM.comparerPerformance(gender, perf, new Integer(finalLast.getRessenti()), new Integer(finalLast1.getTemps()), new Integer(finalLast1.getRessenti())));
                                                break;
                                            case "20KM":
                                                break;
                                            case "Semi-Marathon":
                                                TvTip.setText(SemiMarathon.comparerPerformance(gender, perf, new Integer(finalLast.getRessenti()), new Integer(finalLast1.getTemps()), new Integer(finalLast1.getRessenti())));
                                                break;
                                            case "50KM":
                                                TvTip.setText(FiftyKM.comparerPerformance(gender, perf, new Integer(finalLast.getRessenti()), new Integer(finalLast1.getTemps()), new Integer(finalLast1.getRessenti())));
                                                break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


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


                if (jour < 10) {
                    jourr = "0" + jour;
                } else {
                    jourr = jour + "";
                }

                if (jour < 10) {
                    moiss = "0" + mois;
                } else {
                    moiss = mois + "";
                }


                final String date = (jourr + "/" + moiss + "/" + annee);


                final String temps = addTemps.getText().toString();
                final String ressenti = addRessenti.getText().toString();


                if (temps.isEmpty()) {
                    addTemps.setError("Veuillez remplir ce champ");
                    addTemps.setFocusable(true);
                } else if (ressenti.isEmpty() || Integer.parseInt(addRessenti.getText().toString()) > 9) {
                    addRessenti.setError("Veuillez remplir ce champ avec une valeur adpatée");
                    addRessenti.setFocusable(true);
                } else {
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
