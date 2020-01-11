package com.example.voltagang.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.voltagang.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfilFragment extends Fragment {

    //Composants XML
    private TextView pseudoProfil, ageProfil, poidsProfil, tailleProfil, sexeProfil;
    private FloatingActionButton modifyProfilButton;

    //Référence FireBase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_profil_fragment, null);

        //Initialisation des composants du XML associés
        pseudoProfil = root.findViewById(R.id.pseudoProfil);
        ageProfil = root.findViewById(R.id.ageProfil);
        poidsProfil = root.findViewById(R.id.poidsProfil);
        tailleProfil = root.findViewById(R.id.tailleProfil);
        sexeProfil = root.findViewById(R.id.sexeProfil);
        modifyProfilButton = root.findViewById(R.id.modifyProfilButton);

        //Initialisation FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        mRef.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pseudoProfil.setText(dataSnapshot.child("pseudo").getValue().toString());
                ageProfil.setText("Age : " + dataSnapshot.child("age").getValue().toString());
                sexeProfil.setText("Sexe : " + dataSnapshot.child("sexe").getValue().toString());
                if(dataSnapshot.child("poids").exists()){
                    poidsProfil.setText("Poids : " + dataSnapshot.child("poids").getValue().toString());
                } else {
                    poidsProfil.setText("Poids : Non renseigné");
                }
                if(dataSnapshot.child("taille").exists()){
                    tailleProfil.setText("Taille : " + dataSnapshot.child("taille").getValue().toString());
                } else {
                    tailleProfil.setText("Taille : Non renseigné");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        modifyProfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String options[] = {"Changer de pseudo", "Changer d'age", "Changer de poids", "Changer de taille"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Edition du profil");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            changerDePseudo();
                        }else if (which == 1){
                            changerDAge();
                        }else if (which == 2){
                            changerDePoids();
                        }else if (which == 3){
                            changerDeTaille();
                        }
                    }
                });
                builder.create().show();
            }
        });
        return root;
    }

    private void changerDePseudo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("Changer de pseudo");
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setPadding(10,10,10,10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editText = new EditText(getActivity());
        editText.setHint("Nouveau pseudo...");
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        builder.setPositiveButton("Changer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();
                if(!TextUtils.isEmpty(value)){
                    HashMap hashMap = new HashMap();
                    hashMap.put("pseudo", value);
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
                    usersRef.updateChildren(hashMap);
                }else { editText.setError("Entrer un pseudo"); }
            }
        });
        builder.setNegativeButton("Annuler", null);
        builder.create().show();
    }

    private void changerDAge(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("Changer d'age");
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setPadding(10,10,10,10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editText = new EditText(getActivity());
        editText.setHint("Nouvelle age...");
        editText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        builder.setPositiveButton("Changer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();
                if(!TextUtils.isEmpty(value)){
                    HashMap hashMap = new HashMap();
                    hashMap.put("age", value);
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
                    usersRef.updateChildren(hashMap);
                }else { editText.setError("Entrer un age"); }
            }
        });
        builder.setNegativeButton("Annuler", null);
        builder.create().show();
    }

    private void changerDePoids(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("Changer de poids");
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setPadding(10,10,10,10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editText = new EditText(getActivity());
        editText.setHint("Nouveau poids...");
        editText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        builder.setPositiveButton("Changer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();
                if(!TextUtils.isEmpty(value)){
                    HashMap hashMap = new HashMap();
                    hashMap.put("poids", value);
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
                    usersRef.updateChildren(hashMap);
                }else { editText.setError("Entrer un poids"); }
            }
        });
        builder.setNegativeButton("Annuler", null);
        builder.create().show();
    }

    private void changerDeTaille(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("Changer de taille (en cm)");
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setPadding(10,10,10,10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editText = new EditText(getActivity());
        editText.setHint("Nouvelle taille...");
        editText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        builder.setPositiveButton("Changer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();
                if(!TextUtils.isEmpty(value)){
                    HashMap hashMap = new HashMap();
                    hashMap.put("taille", value);
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
                    usersRef.updateChildren(hashMap);
                }else { editText.setError("Entrer une taille"); }
            }
        });
        builder.setNegativeButton("Annuler", null);
        builder.create().show();
    }
}
