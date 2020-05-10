package com.example.huntergo.CRUD;

import android.util.Log;

import com.example.huntergo.Classes.Jogador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JogadorCRUD {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("basededados").child("jogadores");

    public void criarGuerreiro(String UID){
        Jogador jog = new Jogador("Guerreiro", 80, 10, 15, 5, 9, 3);
        Log.d("deubom", "Guerreiro");
        reference.child(UID).setValue(jog);
        Log.d("deubom", "Guerreiro2");

    }

    public void criarCaçador(String UID){
        Jogador jog = new Jogador("Caçador", 70, 20, 20, 3, 14, 8);
        Log.d("deubom", "Caçador");
        reference.child(UID).setValue(jog);
        Log.d("deubom", "Caçador2");

    }

    public void criarMago(String UID){
        Jogador jog = new Jogador("Mago", 60, 40, 8, 2, 11, 20);
        Log.d("deubom", "Mago");
        reference.child(UID).setValue(jog);
        Log.d("deubom", "Mago2");

    }
}
