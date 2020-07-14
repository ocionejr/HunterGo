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
    private DatabaseReference referenceUID;
    private String uid;
    private  ArrayList<ItemInventario> itens = new ArrayList<>();
    private static InventarioCRUD INSTANCE;
    int posicao;

    public static final InventarioCRUD getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new InventarioCRUD();
        }

        return INSTANCE;
    }

    private InventarioCRUD() {
    }

    public void IniciarListeners(String uid) {
        this.uid = uid;

        referenceUID = reference.child(uid);
        referenceUID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itens.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    for (DataSnapshot data2 : data.getChildren()){
                        switch (data.getKey()){
                            case "consumiveis":
                                Log.d("inventario", "consumivel: " + data2.getKey());
                                itens.add(new ItemInventario(Integer.parseInt(data2.getKey()), (int)(long)data2.getValue(), "consumivel", R.drawable.esqueleto));
                                posicao = itens.size();
                                Log.d("inventario", "consumivel: " + posicao);
                                break;

                            case "armas":
                                itens.add(new ItemInventario(Integer.parseInt(data2.getKey()), (int)(long)data2.getValue(), "arma", R.drawable.esqueleto));
                                break;

                            case "armaduras":
                                Log.d("inventario", "armadura: " + data2.getKey());
                                itens.add(new ItemInventario(Integer.parseInt(data2.getKey()), (int)(long)data2.getValue(), "armadura", R.drawable.esqueleto));
                                posicao = itens.size();
                                Log.d("inventario", "armadura: " + posicao);
                                break;

                            default:
                                itens.add(new ItemInventario(99, 2, data.getKey(), 22));
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
        for(ItemInventario itemInventario : itens){
            Log.d("inventario", itemInventario.getTipo());
        }
        return itens;
    }

    public void excluirItem(String tipo, String id){
        referenceUID.child(tipo).child(id).removeValue();
    }

    public void alterarQuantidade(String tipo, String id, int qtd){
        referenceUID.child(tipo).child(id).setValue(qtd);
    }

    public void adicionarArmadura(String id){
        referenceUID.child("armaduras").child(id).setValue(1);
    }
}
