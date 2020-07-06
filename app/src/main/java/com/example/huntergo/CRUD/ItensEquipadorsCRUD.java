package com.example.huntergo.CRUD;

import com.example.huntergo.Classes.ItensEquipados;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ItensEquipadorsCRUD {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("itensEquipados");
    private static ItensEquipadorsCRUD INSTANCE;

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
}
