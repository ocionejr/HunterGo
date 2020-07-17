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
    private DatabaseReference referenceuid;
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
        ItensEquipados itensEquipados = new ItensEquipados("002", "0", "002");
        reference.child(uid).setValue(itensEquipados);
    }

    public void criarEquipCacador(String uid){
        ItensEquipados itensEquipados = new ItensEquipados("006", "0", "001");
        reference.child(uid).setValue(itensEquipados);
    }

    public void criarEquipMago(String uid){
        ItensEquipados itensEquipados = new ItensEquipados("004", "0", "003");
        reference.child(uid).setValue(itensEquipados);
    }

    public void IniciarListeners(String uid) {

        referenceuid = reference.child(uid);
        referenceuid.addValueEventListener(new ValueEventListener() {
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

    public void desequiparArmadura(){
        referenceuid.child("armadura").setValue("0");
    }

    public void desequiparMaoEsquerda(){
        referenceuid.child("maoEsquerda").setValue("0");
    }

    public void desequiparMaoDireita(){
        referenceuid.child("maoDireita").setValue("0");
    }

    public void equiparArmadura(String armaduraId){
        referenceuid.child("armadura").setValue(armaduraId);
    }

    public void equiparMaoDireita(String armaId){
        referenceuid.child("maoDireita").setValue(armaId);
    }

    public void equiparMaoEsquerda(String armaId){
        referenceuid.child("maoEsquerda").setValue(armaId);
    }

}
