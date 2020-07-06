package com.example.huntergo.CRUD;

import androidx.annotation.NonNull;

import com.example.huntergo.Classes.Arma;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArmaCRUD {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("armas");
    private ArrayList<Arma> armas = new ArrayList<>();
    private static ArmaCRUD INSTANCE;

    public static final ArmaCRUD getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new ArmaCRUD();
        }

        return INSTANCE;
    }

    private ArmaCRUD() {

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Arma arma = data.getValue(Arma.class);
                    arma.setId(Integer.parseInt(data.getKey()));
                    armas.add(arma);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Arma> getArmas(){
        return armas;
    }
}
