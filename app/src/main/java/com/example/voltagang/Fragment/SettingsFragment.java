package com.example.voltagang.Fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.voltagang.FirstActivity;
import com.example.voltagang.MainActivity;
import com.example.voltagang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    //Composants XML
    private Button disconnectButton;
    private ImageView fr;
    private ImageView eng;


    //Référence FireBase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_settings_fragment, null);

        //Initialisation des composants XML
        disconnectButton = root.findViewById(R.id.disconnectButton);
        fr = root.findViewById(R.id.fr);
        eng = root.findViewById(R.id.eng);

        //Initialisation FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), FirstActivity.class));
            }
        });

        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFre();
            }
        });

        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setEng();
            }
        });

        return root;
    }


    @SuppressWarnings("deprecation")
    private void setLocale(Locale locale){

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
        } else{*/
            configuration.locale=locale;
       /* }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            getActivity().getApplicationContext().createConfigurationContext(configuration);
        } else {*/
            resources.updateConfiguration(configuration,displayMetrics);
        //}
        startActivity(new Intent(getActivity(), MainActivity.class));

    }

    public void setEng(){
        setLocale(Locale.ENGLISH);
    }

    public void setFre(){
        setLocale(Locale.FRANCE);
    }






}
