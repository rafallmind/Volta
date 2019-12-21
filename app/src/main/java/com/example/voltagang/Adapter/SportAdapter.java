package com.example.voltagang.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voltagang.Model.Sport;
import com.example.voltagang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.MyViewHolder> {

    //Référence FireBase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    private Context mContext;
    private List<Sport> mSport;

    public SportAdapter(Context mContext, List<Sport> mSport) {
        this.mContext = mContext;
        this.mSport = mSport;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View sportView = inflater.inflate(R.layout.all_sport_display, parent, false);

        //Initialisation FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        return new MyViewHolder(sportView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Sport sport = mSport.get(position);

        holder.sportPicture.setImageResource(sport.getImage());
        holder.sportTitle.setText(sport.getName());
        holder.sportSubtile.setText(sport.getDescription());

        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("mesSports").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(sport.getName()).exists()){
                    holder.sportLinear.setBackgroundColor(Color.parseColor("#ffc46b"));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });



        holder.sportLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((ColorDrawable) view.getBackground()).getColor() == Color.WHITE){
                    holder.sportLinear.setBackgroundColor(Color.parseColor("#ffc46b"));
                    Toast.makeText(mContext.getApplicationContext(),sport.getName() + " SELECTED",Toast.LENGTH_SHORT).show();
                    String userUID = mAuth.getCurrentUser().getUid();
                    HashMap usersMap = new HashMap();
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(userUID).child("mesSports");
                    usersMap.put(sport.getName(), sport);
                    usersRef.updateChildren(usersMap);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                            .setTitle("ATTENTION")
                            .setMessage("Vous êtes sur le point de supprimer toutes les informations du sport : " + sport.getName())
                            .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String userUID = mAuth.getCurrentUser().getUid();
                                    FirebaseDatabase.getInstance().getReference().child("users").child(userUID).child("mesSports").child(sport.getName()).removeValue();
                                    holder.sportLinear.setBackgroundColor(Color.WHITE);
                                    Toast.makeText(mContext.getApplicationContext(),sport.getName() + " UNSELECTED",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Annuler", null)
                            .setIcon(R.drawable.ic_exclamation_mark)
                            .show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mSport.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView sportPicture;
        public TextView sportTitle, sportSubtile;
        public LinearLayout sportLinear;


        public MyViewHolder(final View itemView) {
            super(itemView);
            sportPicture = (ImageView) itemView.findViewById(R.id.imageAddSportDisplay);
            sportTitle = (TextView) itemView.findViewById(R.id.titreAddSportDisplay);
            sportSubtile = (TextView) itemView.findViewById(R.id.sousTitreAddSportDisplay);
            sportLinear = (LinearLayout) itemView.findViewById(R.id.linearLayoutAddSportDisplay);
        }
    }




}


