package com.example.voltagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;

public class AnalyseSportActivity extends AppCompatActivity {

    //Composants XML
    private TextView laDate, leTemps, leRessenti;
    private EditText addDate,addTemps,addRessenti;
    private Button btnAdd;

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

        //Initialisation des composants du XML associés
        laDate = findViewById(R.id.laDate);
        leTemps = findViewById(R.id.leTemps);
        leRessenti = findViewById(R.id.leRessenti);
        addDate = findViewById(R.id.addDate);
        addTemps = findViewById(R.id.addTime);
        addRessenti = findViewById(R.id.addFeeling);
        btnAdd = findViewById(R.id.btnAdd);

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
                        laDate.setText(child.child("date").getValue().toString());
                        leTemps.setText(child.child("temps").getValue().toString());
                        leRessenti.setText(child.child("ressenti").getValue().toString());
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //ajout du sport
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String date = addDate.getText().toString();
                final String temps = addTemps.getText().toString();
                final String ressenti = addRessenti.getText().toString();

                if(date.isEmpty()){
                    addDate.setError("Veuillez remplir ce champ");
                    addDate.setFocusable(true);
                }
                else if(temps.isEmpty()){
                    addTemps.setError("Veuillez remplir ce champ");
                    addTemps.setFocusable(true);
                }
                else if(ressenti.isEmpty()){
                    addRessenti.setError("Veuillez remplir ce champ");
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




                    }
            }
        });


    }

}
