package com.example.voltagang.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        Sport s1 = new Sport("10KM", getResources().getString(R.string.inter1), R.drawable.icinter1);
        Sport s2 = new Sport("5KM",getResources().getString(R.string.deb2), R.drawable.icdeb2);
        Sport s3 = new Sport("2,5KM", getResources().getString(R.string.deb1), R.drawable.icdeb1);
        Sport s4 = new Sport("20KM", getResources().getString(R.string.inter2), R.drawable.icinter2);
        Sport s5 = new Sport("Semi-Marathon", getResources().getString(R.string.inter3), R.drawable.icinter3);
        Sport s6 = new Sport("Marathon", getResources().getString(R.string.exp1), R.drawable.icexpert1);
        Sport s7 = new Sport("50KM", getResources().getString(R.string.exp2), R.drawable.icexpert2);
        mSports.add(s7);
        mSports.add(s6);
        mSports.add(s5);
        mSports.add(s4);
        mSports.add(s1);
        mSports.add(s2);
        mSports.add(s3);
        return mSports;
    }


}
