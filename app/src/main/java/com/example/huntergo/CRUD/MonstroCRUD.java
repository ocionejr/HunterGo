package com.example.huntergo.CRUD;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.huntergo.Classes.Monstro;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MonstroCRUD {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("monstros");
    private ArrayList<Monstro> monstros = new ArrayList<>();

    public MonstroCRUD() {
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
    }

    public ArrayList<Monstro> getMonstros(){
        return monstros;

    }

}
