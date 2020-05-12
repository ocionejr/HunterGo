package com.example.huntergo.CRUD;

import android.util.Log;

import com.example.huntergo.Classes.Jogador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JogadorCRUD {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("jogadores");
    private ItensEquipadorsCRUD itensEquipadorsCRUD = new ItensEquipadorsCRUD();

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
}
