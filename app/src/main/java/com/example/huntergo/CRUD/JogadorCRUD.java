package com.example.huntergo.CRUD;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.huntergo.Classes.ItemInventario;
import com.example.huntergo.Classes.Jogador;
import com.example.huntergo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JogadorCRUD {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("jogadores");
    private ItensEquipadorsCRUD itensEquipadorsCRUD = ItensEquipadorsCRUD.getINSTANCE();
    private Jogador jogador;
    private static JogadorCRUD INSTANCE;

    public static final JogadorCRUD getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new JogadorCRUD();
        }

        return INSTANCE;
    }

    private JogadorCRUD() {
    }

    public void criarGuerreiro(String UID){
        Jogador jog = new Jogador("Guerreiro", 80, 10, 15, 5, 9, 3);
        reference.child(UID).setValue(jog);
        itensEquipadorsCRUD.criarEquipGuerreiro(UID);
    }

    public void criarCaçador(String UID){
        Jogador jog = new Jogador("Caçador", 70, 20, 20, 3, 14, 8);
        reference.child(UID).setValue(jog);
        itensEquipadorsCRUD.criarEquipCacador(UID);
    }

    public void criarMago(String UID){
        Jogador jog = new Jogador("Mago", 60, 40, 8, 2, 11, 20);
        reference.child(UID).setValue(jog);
        itensEquipadorsCRUD.criarEquipMago(UID);
    }

    public void IniciarListeners(String uid) {

        DatabaseReference referenceUID = reference.child(uid);
        referenceUID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    jogador = dataSnapshot.getValue(Jogador.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Jogador getJogador() {
        return jogador;
    }
}
