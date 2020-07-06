package com.example.huntergo.CRUD;

import androidx.annotation.NonNull;

import com.example.huntergo.Classes.Armadura;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArmaduraCRUD {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("armaduras");
    private ArrayList<Armadura> armaduras = new ArrayList<>();
    private static ArmaduraCRUD INSTANCE;

    public static final ArmaduraCRUD getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new ArmaduraCRUD();
        }

        return INSTANCE;
    }

    private ArmaduraCRUD() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Armadura armadura = data.getValue(Armadura.class);
                    armadura.setId(Integer.parseInt(data.getKey()));
                    armaduras.add(armadura);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Armadura> getArmaduras(){
        return armaduras;
    }
}
