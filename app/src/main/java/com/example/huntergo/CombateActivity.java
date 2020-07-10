package com.example.huntergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.huntergo.CRUD.JogadorCRUD;
import com.example.huntergo.Classes.Jogador;
import com.example.huntergo.Classes.Monstro;

import java.util.ArrayList;
import java.util.Random;

public class CombateActivity extends AppCompatActivity {
    private Monstro monstro;
    private Jogador jogador;
    private JogadorCRUD jogadorCRUD;
    private ImageView img_monstro;
    private TextView vida_j;
    private TextView vida_m;
    private TextView evt;
    private ProgressBar hp_p;
    private ProgressBar mp_p;
    private ProgressBar hp_m;
    private int hp_monster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combate);

        monstro = getIntent().getExtras().getParcelable("monstro");
        jogadorCRUD = JogadorCRUD.getINSTANCE();
        jogador = jogadorCRUD.getJogador();
        img_monstro = (ImageView) findViewById(R.id.img_monstro);
        evt = findViewById(R.id.evento);
        hp_p = findViewById(R.id.hp_player);
        hp_p.getProgressDrawable().setColorFilter(
                Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        hp_p.setProgress(jogador.getVida());
        hp_p.setScaleY(3f);
        hp_m = findViewById(R.id.hp_monster);
        hp_m.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        hp_monster = monstro.getVida();
        hp_m.setMax(hp_monster);
        hp_m.setProgress(hp_monster);
        hp_m.setScaleY(3f);
        mp_p = findViewById(R.id.mp_player);
        mp_p.getProgressDrawable().setColorFilter(
                Color.CYAN, android.graphics.PorterDuff.Mode.SRC_IN);
        mp_p.setProgress(jogador.getMana());
        mp_p.setScaleY(3f);

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
        evt.setText(" Fugindo! Covarde!");
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
    }

    public void ataque(View v){
        int vi = monstro.getAtaque();
        evt.setText(" Atacou! +"+jogador.getAtaque()+"   -"+vi);
        if(jogador.getVelocidade() >= monstro.getVelocidade()){
            hp_monster = hp_monster - jogador.getAtaque();
            hp_m.setProgress(hp_monster);
            /*if(monstro.getVida() == 0){

            }*/
            int vida = jogador.getVida() + (jogador.getDefesa() / 2);
            vida = vida - monstro.getAtaque();
            jogador.setVida(vida);
            hp_p.setProgress(jogador.getVida());
            /*if(jogador.getVida() == 0){

            }*/
        }
        if(jogador.getVelocidade() < monstro.getVelocidade()){
            int vida = jogador.getVida() + (jogador.getDefesa() / 2);
            vida = vida - monstro.getAtaque();
            jogador.setVida(vida);
            hp_p.setProgress(jogador.getVida());
            /*if(jogador.getVida() == 0){

            }*/
            hp_monster = hp_monster - jogador.getAtaque();
            hp_m.setProgress(hp_monster);
            /*if(monstro.getVida() == 0){

            }*/
        }
    }

    public void defesa(View v){
        evt.setText(" Defendeu! -1");
        int vida = jogador.getVida() - 1;
        jogador.setVida(vida);
        hp_p.setProgress(jogador.getVida());
    }

    public void especial(View v){
        int vi = monstro.getAtaque();
        evt.setText(" Ataque especial! +"+jogador.getPodermagico()+"   -"+vi);
        if(jogador.getVelocidade() >= monstro.getVelocidade()){
            hp_monster = hp_monster - jogador.getAtaque();
            hp_m.setProgress(hp_monster);
            /*if(monstro.getVida() == 0){

            }*/
            int vida = jogador.getVida() + (jogador.getDefesa() / 2);
            vida = vida - monstro.getAtaque();
            jogador.setVida(vida);
            hp_p.setProgress(jogador.getVida());
            /*if(jogador.getVida() == 0){

            }*/
        }
        if(jogador.getVelocidade() < monstro.getVelocidade()){
            int vida = jogador.getVida() + (jogador.getDefesa() / 2);
            vida = vida - monstro.getAtaque();
            jogador.setVida(vida);
            hp_p.setProgress(jogador.getVida());
            /*if(jogador.getVida() == 0){

            }*/
            hp_monster = hp_monster - jogador.getPodermagico();
            hp_m.setProgress(hp_monster);
            int mana = jogador.getMana();
            /*if(monstro.getVida() == 0){

            }*/
        }
    }
}
