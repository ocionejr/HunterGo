package com.example.huntergo.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.huntergo.Classes.Monstro;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MonstroDAO {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("monstros");
    private ArrayList<Monstro> monstros;

    public ArrayList<Monstro> getMonstros(){
        monstros = new ArrayList<>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("teste", "onDataChange");
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    monstros.add(data.getValue(Monstro.class));
                }

                for(Monstro m : monstros){
                    Log.d("Monstro nome", m.getNome());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return monstros;

    }

}
