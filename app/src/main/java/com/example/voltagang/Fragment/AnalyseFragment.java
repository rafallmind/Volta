package com.example.voltagang.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.voltagang.Adapter.MesSportsAdapter;
import com.example.voltagang.Adapter.SportAdapter;
import com.example.voltagang.Model.Sport;
import com.example.voltagang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnalyseFragment extends Fragment {

    //Composants XML
    private RecyclerView mySportRecyclerView;

    //Référence FireBase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    private MesSportsAdapter mSportAdapter;
    private List<Sport> mSports;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_analyse_fragment, null);

        //Initialisation des composants du XML associés
        mySportRecyclerView = root.findViewById(R.id.mySportRecyclerView);

        //Initialisation FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        mSports = new ArrayList<>();

        chargerRecyclerView(chargerSport());

        return root;
    }

    private void chargerRecyclerView(List<Sport> sports){
        mSportAdapter = new MesSportsAdapter(getContext(), sports);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        mySportRecyclerView.setLayoutManager(layoutManager);
        mSportAdapter.notifyDataSetChanged();
        mySportRecyclerView.setAdapter(mSportAdapter);
    }

    private List<Sport> chargerSport(){
        mRef.child("users").child(mAuth.getCurrentUser().getUid()).child("mesSports").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    Sport sport = new Sport(
                        child.child("name").getValue().toString(),
                        child.child("description").getValue().toString(),
                        Integer.parseInt(child.child("image").getValue().toString())
                    );
                    mSports.add(sport);
                    chargerRecyclerView(mSports);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return mSports;
    }

}
