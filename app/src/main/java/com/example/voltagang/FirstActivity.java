package com.example.voltagang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {

    private Button firstToSignIn, firstToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        firstToSignIn = findViewById(R.id.firstToSignIn);
        firstToSignUp = findViewById(R.id.firstToSignUp);

        firstToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, SignInActivity.class));
                finish();
            }
        });

        firstToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, SignUpActivity.class));
                finish();
            }
        });

    }
}
