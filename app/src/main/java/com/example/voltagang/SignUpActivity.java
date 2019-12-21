package com.example.voltagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.voltagang.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    // Composants du XML
    private EditText pseudoSignUp, emailSignUp, passwordSignUp, ageSignUp;
    private ListView listeSignUp;
    private Button buttonSignUp;

    //Référence FireBase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;


    private String mSexe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Ca c'est stylé
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Chargement...");

        //Initialisation des composants du XML associés
        pseudoSignUp = findViewById(R.id.pseudoSignUp);
        emailSignUp = findViewById(R.id.emailSignUp);
        passwordSignUp = findViewById(R.id.passwordSignUp);
        ageSignUp = findViewById(R.id.ageSignUp);
        listeSignUp = findViewById(R.id.listeSignUp);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        //Initialisation FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();


        //Définition des différents sexes
        final ArrayList<String> listSexe = new ArrayList<>();
        listSexe.add("Homme");
        listSexe.add("Femme");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listSexe);
        listeSignUp.setAdapter(arrayAdapter);

        listeSignUp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSexe = listeSignUp.getItemAtPosition(i).toString();
            }
        });

        //Inscription
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pseudo = pseudoSignUp.getText().toString();
                final String email = emailSignUp.getText().toString();
                final String password = passwordSignUp.getText().toString();
                final String age = ageSignUp.getText().toString();

                if (pseudo.isEmpty()){
                    pseudoSignUp.setError("Veuillez remplir ce champ");
                    pseudoSignUp.setFocusable(true);
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailSignUp.setError("Email invalide");
                    emailSignUp.setFocusable(true);
                }else if(password.length()<6){
                    passwordSignUp.setError("Mot de passe > 6");
                    passwordSignUp.setFocusable(true);
                }else if(age.isEmpty()){
                    ageSignUp.setError("Veuillez remplir ce champ");
                    ageSignUp.setFocusable(true);
                }else if(mSexe == null){
                    Toast.makeText(getApplicationContext(), "Veuillez choisir un sexe", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        mAuth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if(task.isSuccessful()){
                                                            User user = new User(
                                                                    pseudo,
                                                                    mSexe,
                                                                    Integer.parseInt(age)
                                                            );
                                                            String userUID = mAuth.getCurrentUser().getUid();
                                                            HashMap usersMap = new HashMap();
                                                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
                                                            usersMap.put(userUID, user);
                                                            usersRef.updateChildren(usersMap);
                                                            progressDialog.dismiss();
                                                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                                            finish();
                                                        }else {
                                                            Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(), Toast.LENGTH_LONG).show();
                                                            progressDialog.dismiss();

                                                        }
                                                    }
                                                });
                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

            }
        });



    }
}
