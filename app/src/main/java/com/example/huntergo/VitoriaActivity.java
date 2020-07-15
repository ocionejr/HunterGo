package com.example.huntergo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.huntergo.CRUD.JogadorCRUD;
import com.example.huntergo.Classes.Jogador;

public class VitoriaActivity extends AppCompatActivity {

    private JogadorCRUD jogadorCRUD;
    private Jogador jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitoria);

        jogadorCRUD = JogadorCRUD.getINSTANCE();
        jogador = jogadorCRUD.getJogador();


    }


}
