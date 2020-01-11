package com.example.voltagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    // Composants du XML
    private EditText emailSignIn, passwordSignIn;
    private Button buttonSignIn;

    //Référence FireBase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Ca c'est stylé
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        //Initialisation des composants du XML associés
        emailSignIn = findViewById(R.id.emailSignIn);
        passwordSignIn = findViewById(R.id.passwordSignIn);
        buttonSignIn = findViewById(R.id.buttonSignIn);

        //Initialisation FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        //Connexion
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailSignIn.getText().toString();
                final String password = passwordSignIn.getText().toString();


                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailSignIn.setError("Email invalide");
                    emailSignIn.setFocusable(true);
                }else if(password.length()<6){
                    passwordSignIn.setError("Mot de passe < 6");
                    passwordSignIn.setFocusable(true);
                }else {
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                        progressDialog.dismiss();
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(), Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();

                                    }
                                }
                            });
                }
            }
        });

    }
}
