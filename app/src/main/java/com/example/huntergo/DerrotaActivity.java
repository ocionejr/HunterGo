package com.example.huntergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.huntergo.CRUD.JogadorCRUD;
import com.example.huntergo.Classes.Jogador;

public class DerrotaActivity extends AppCompatActivity {
    private JogadorCRUD jogadorCRUD;
    private Jogador jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_derrota);

        jogadorCRUD = JogadorCRUD.getINSTANCE();
        jogador = jogadorCRUD.getJogador();
    }

    public void back(View v){
        jogadorCRUD.alteraVida(20);
        jogadorCRUD.alteraMana(40);

        Intent intent=new Intent();
        setResult(0,intent);
        finish();//finishing activity
    }
}
