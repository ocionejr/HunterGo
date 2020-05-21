package com.example.huntergo.CRUD;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.huntergo.Classes.ItemInventario;
import com.example.huntergo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InventarioCRUD {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("inventario");
    private String uid;
    private  ArrayList<ItemInventario> itens = new ArrayList<>();

    public void IniciarListeners(String uid) {
        this.uid = uid;

        DatabaseReference referenceUID = reference.child(uid);
        referenceUID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    itens.clear();
                    for (DataSnapshot data2 : data.getChildren()){
                        switch (data.getKey()){
                            case "consumiveis":
                                itens.add(new ItemInventario(data2.getKey(), (int)(long)data2.getValue(), "consumivel", R.drawable.esqueleto));
                                break;

                            case "armas":
                                itens.add(new ItemInventario(data2.getKey(), (int)(long)data2.getValue(), "arma", R.drawable.esqueleto));
                                break;

                            case "armaduras":
                                itens.add(new ItemInventario(data2.getKey(), (int)(long)data2.getValue(), "armadura", R.drawable.esqueleto));
                                break;

                            default:
                                itens.add(new ItemInventario("erro", 2, data.getKey(), 22));
                                break;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void criarInventario(String uid){
        reference.child(uid).child("consumiveis").child("001").setValue(2);
        reference.child(uid).child("consumiveis").child("002").setValue(2);
    }

    public ArrayList<ItemInventario> getInventario (){
        return itens;
    }
}
