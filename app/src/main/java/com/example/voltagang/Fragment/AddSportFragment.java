package com.example.voltagang.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.voltagang.Adapter.SportAdapter;
import com.example.voltagang.Model.Sport;
import com.example.voltagang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddSportFragment extends Fragment {

    //Composants XML
    private RecyclerView addSportRecyclerView;
    private Button addSportValidButton;

    //Référence FireBase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    private SportAdapter mSportAdapter;
    private List<Sport> mSports;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_add_sport_fragment, null);

        //Initialisation des composants du XML associés
        addSportRecyclerView = root.findViewById(R.id.addSportRecyclerView);
        addSportValidButton = root.findViewById(R.id.addSportValidButton);

        //Initialisation FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        mSports = new ArrayList<>();
        chargerRecyclerView(chargerSport());

        return root;
    }

    private void chargerRecyclerView(List<Sport> sports){
        mSportAdapter = new SportAdapter(getContext(), sports);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        addSportRecyclerView.setLayoutManager(layoutManager);
        mSportAdapter.notifyDataSetChanged();
        addSportRecyclerView.setAdapter(mSportAdapter);
    }

    private List<Sport> chargerSport(){
        Sport s1 = new Sport("10KM", "Facebook description", R.drawable.ic_facebook);
        Sport s2 = new Sport("5KM", "Facebook description", R.drawable.ic_facebook);
        Sport s3 = new Sport("2 virgule 5KM", "Facebook description", R.drawable.ic_facebook);
        Sport s4 = new Sport("20KM", "Facebook description", R.drawable.ic_facebook);
        Sport s5 = new Sport("SemiMarathon", "Facebook description", R.drawable.ic_facebook);
        Sport s6 = new Sport("Marathon", "Facebook description", R.drawable.ic_facebook);
        Sport s7 = new Sport("50KM", "Facebook description", R.drawable.ic_facebook);
        mSports.add(s1);
        mSports.add(s2);
        mSports.add(s3);
        mSports.add(s4);
        mSports.add(s5);
        mSports.add(s6);
        mSports.add(s7);
        return mSports;
    }
}
