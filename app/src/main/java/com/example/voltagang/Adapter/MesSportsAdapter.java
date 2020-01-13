package com.example.voltagang.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voltagang.AnalyseSportActivity;
import com.example.voltagang.Model.Sport;
import com.example.voltagang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MesSportsAdapter extends RecyclerView.Adapter<MesSportsAdapter.MyViewHolder> {

    //Référence FireBase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    private Context mContext;
    private List<Sport> mSport;

    private Bundle b;

    public MesSportsAdapter(Context mContext, List<Sport> mSport) {
        this.mContext = mContext;
        this.mSport = mSport;
        this.b = new Bundle();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View sportView = inflater.inflate(R.layout.all_my_sport_display, parent, false);



        //Initialisation FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        return new MyViewHolder(sportView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Intent intent = new Intent(mContext, AnalyseSportActivity.class);
        final Sport sport = mSport.get(position);
        holder.sportTitle.setText(sport.getName());
        holder.sportSubtile.setText(sport.getDescription());

        holder.buttonSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.putString("key", sport.getName());
                final ProgressDialog progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Loading...");
                intent.putExtras(b);
                mContext.startActivity(intent);
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

        public TextView sportTitle, sportSubtile;
        public LinearLayout sportLinear;
        public Button buttonSession;


        public MyViewHolder(final View itemView) {
            super(itemView);
            sportTitle = (TextView) itemView.findViewById(R.id.titreMySportDisplay);
            sportSubtile = (TextView) itemView.findViewById(R.id.sousTitreMySportDisplay);
            sportLinear = (LinearLayout) itemView.findViewById(R.id.linearLayoutMySportDisplay);
            buttonSession = (Button) itemView.findViewById(R.id.buttonsession);
        }
    }
}
