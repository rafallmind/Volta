package com.example.voltagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnalyseSportActivity extends AppCompatActivity {

    //Composants XML
    private TextView sportNameAnalyse, sportDescriptionAnalyse;

    private Bundle b;

    //Référence FireBase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse_sport);

        //Initialisation des composants du XML associés
        sportNameAnalyse = findViewById(R.id.sportNameAnalyse);
        sportDescriptionAnalyse = findViewById(R.id.sportDescriptionAnalyse);

        //Initialisation FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        b = getIntent().getExtras();

        mRef.child("users").child(mAuth.getCurrentUser().getUid()).child("mesSports").child(b.getString("key")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    sportNameAnalyse.setText(dataSnapshot.child("name").getValue().toString());
                    sportDescriptionAnalyse.setText(dataSnapshot.child("description").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

}
