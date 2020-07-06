package com.example.huntergo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.huntergo.CRUD.JogadorCRUD;
import com.example.huntergo.Classes.Jogador;
import com.example.huntergo.Classes.Monstro;

public class CombateActivity extends AppCompatActivity {
    private Monstro monstro;
    private Jogador jogador;
    private JogadorCRUD jogadorCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combate);

        monstro = getIntent().getExtras().getParcelable("monstro");
        jogadorCRUD = JogadorCRUD.getINSTANCE();
        jogador = jogadorCRUD.getJogador();

        Log.d("combate", monstro.getNome());
        Log.d("combate", jogador.getClasse());
    }
}
