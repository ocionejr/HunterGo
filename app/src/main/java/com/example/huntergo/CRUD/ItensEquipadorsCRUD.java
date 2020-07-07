package com.example.huntergo.CRUD;

import androidx.annotation.NonNull;

import com.example.huntergo.Classes.ItensEquipados;
import com.example.huntergo.Classes.Jogador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ItensEquipadorsCRUD {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("itensEquipados");
    private static ItensEquipadorsCRUD INSTANCE;
    private ItensEquipados itensEquipados;

    public static final ItensEquipadorsCRUD getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new ItensEquipadorsCRUD();
        }

        return INSTANCE;
    }

    private ItensEquipadorsCRUD() {
    }

    public void criarEquipGuerreiro(String uid){
        ItensEquipados itensEquipados = new ItensEquipados("002", "005", "002");
        reference.child(uid).setValue(itensEquipados);
    }

    public void criarEquipCacador(String uid){
        ItensEquipados itensEquipados = new ItensEquipados("006", "vazio", "001");
        reference.child(uid).setValue(itensEquipados);
    }

    public void criarEquipMago(String uid){
        ItensEquipados itensEquipados = new ItensEquipados("004", "vazio", "003");
        reference.child(uid).setValue(itensEquipados);
    }

    public void IniciarListeners(String uid) {

        DatabaseReference referenceUID = reference.child(uid);
        referenceUID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itensEquipados = dataSnapshot.getValue(ItensEquipados.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ItensEquipados getItensEquipados() {
        return itensEquipados;
    }
}
