package com.example.huntergo.CRUD;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InventarioCRUD {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("inventario");

    public void criarInventario(String uid){
        reference.child(uid).child("consumiveis").child("001").setValue(2);
        reference.child(uid).child("consumiveis").child("002").setValue(2);
    }
}
