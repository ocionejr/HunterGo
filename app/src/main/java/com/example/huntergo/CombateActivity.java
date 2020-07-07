package com.example.huntergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huntergo.CRUD.JogadorCRUD;
import com.example.huntergo.Classes.Jogador;
import com.example.huntergo.Classes.Monstro;

public class CombateActivity extends AppCompatActivity {
    private Monstro monstro;
    private Jogador jogador;
    private JogadorCRUD jogadorCRUD;
    private ImageView img_monstro;
    private TextView vida_j;
    private TextView vida_m;
    private TextView evt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combate);

        monstro = getIntent().getExtras().getParcelable("monstro");
        jogadorCRUD = JogadorCRUD.getINSTANCE();
        jogador = jogadorCRUD.getJogador();
        img_monstro = (ImageView) findViewById(R.id.img_monstro);
        jogador.setVida(100);
        vida_j = findViewById(R.id.vida_jogador);
        vida_m = findViewById(R.id.vida_monstro);
        evt = findViewById(R.id.evento);
        vida_j.setText(Integer.toString(jogador.getVida()));
        vida_m.setText(Integer.toString(monstro.getVida()));

        if(monstro.getNome().equals("Esqueleto")){
            img_monstro.setImageResource(R.drawable.esqueleto_combate);
        }else if(monstro.getNome().equals("Ogro")){
            img_monstro.setImageResource(R.drawable.orc_combate);
        }else if(monstro.getNome().equals("Goblin")){
            img_monstro.setImageResource(R.drawable.goblin_combate);
        }else if(monstro.getNome().equals("Slime")){
            img_monstro.setImageResource(R.drawable.smile_combate);
        }

        Log.d("combate", monstro.getNome());
        Log.d("combate", jogador.getClasse());
    }

    public void fugir(View v){
        evt.setText("Fugiu! Galinha, covarde!");
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
    }

    public void ataque(View v){
        evt.setText("Atacou! Continue assim!");
        if(jogador.getVelocidade() >= monstro.getVelocidade()){
            int vida2 = monstro.getVida();
            vida2 = vida2 - jogador.getAtaque();
            monstro.setVida(vida2);
            /*if(monstro.getVida() == 0){

            }*/
            int vida = jogador.getVida() + (jogador.getDefesa() / 2);
            vida = vida - monstro.getAtaque();
            jogador.setVida(vida);
            /*if(jogador.getVida() == 0){

            }*/
            vida_j.setText(Integer.toString(jogador.getVida()));
            vida_m.setText(Integer.toString(monstro.getVida()));
        }
        if(jogador.getVelocidade() < monstro.getVelocidade()){
            int vida = jogador.getVida() + (jogador.getDefesa() / 2);
            vida = vida - monstro.getAtaque();
            jogador.setVida(vida);
            /*if(jogador.getVida() == 0){

            }*/
            int vida2 = monstro.getVida();
            vida2 = vida2 - jogador.getAtaque();
            monstro.setVida(vida2);
            /*if(monstro.getVida() == 0){

            }*/
            vida_j.setText(Integer.toString(jogador.getVida()));
            vida_m.setText(Integer.toString(monstro.getVida()));
        }
    }

    public void defesa(View v){
        evt.setText("Defendeu! Cuidado com os ataques!");
    }
}
