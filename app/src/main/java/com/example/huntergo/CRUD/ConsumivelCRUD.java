package com.example.huntergo.CRUD;

import androidx.annotation.NonNull;

import com.example.huntergo.Classes.Consumivel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConsumivelCRUD {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("consumiveis");
    private ArrayList<Consumivel> consumiveis = new ArrayList<>();
    private static ConsumivelCRUD INSTANCE;

    public static final ConsumivelCRUD getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new ConsumivelCRUD();
        }

        return INSTANCE;
    }

    public ConsumivelCRUD() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Consumivel consumivel = data.getValue(Consumivel.class);
                    consumivel.setId(Integer.parseInt(data.getKey()));
                    consumiveis.add(consumivel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Consumivel> getConsumiveis(){
        return consumiveis;
    }
}
