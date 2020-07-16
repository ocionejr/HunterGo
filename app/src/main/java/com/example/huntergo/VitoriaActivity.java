package com.example.huntergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.huntergo.CRUD.InventarioCRUD;
import com.example.huntergo.CRUD.JogadorCRUD;
import com.example.huntergo.Classes.ItemInventario;
import com.example.huntergo.Classes.Jogador;

import java.util.ArrayList;
import java.util.Random;

public class VitoriaActivity extends AppCompatActivity {

    private JogadorCRUD jogadorCRUD;
    private Jogador jogador;
    private InventarioCRUD inventarioCRUD;
    private TextView txt;
    private TextView txt2;
    private ArrayList<ItemInventario> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitoria);

        inventarioCRUD = InventarioCRUD.getINSTANCE();
        jogadorCRUD = JogadorCRUD.getINSTANCE();
        jogador = jogadorCRUD.getJogador();
        txt = findViewById(R.id.loot);
        txt2 = findViewById(R.id.loot2);
        itens = inventarioCRUD.getInventario();

        int qtde = 0;
        final int random = new Random().nextInt(19);
        if (random == 1) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("armadura") == 0 ){
                    if(itns.getId() == 1){
                        qtde = itns.getQuantidade() + 1;
                    }
                }
            }
            if(qtde < 2){
                inventarioCRUD.adicionarArmadura("001");
            }else {
                inventarioCRUD.alterarQuantidade("armaduras", "001", qtde);
            }
            txt.setText("Você encontrou uma Armadura de Couro!");
        } else if (random == 3) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("armadura") == 0 ){
                    if(itns.getId() == 2){
                        qtde = itns.getQuantidade() + 1;
                    }
                }
            }
            if(qtde < 2){
                inventarioCRUD.adicionarArmadura("002");
            }else{
                inventarioCRUD.alterarQuantidade("armaduras", "002", qtde);
            }
            txt.setText("Você encontrou uma Armadura de Metal!");
        } else if (random == 5) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("armadura") == 0 ){
                    if(itns.getId() == 3){
                        qtde = itns.getQuantidade() + 1;
                    }
                }
            }
            if(qtde < 2){
                inventarioCRUD.adicionarArmadura("003");
            }else {
                inventarioCRUD.alterarQuantidade("armaduras", "003", qtde);
            }
            txt.setText("Você encontrou um Vestido de Seda!");
        } else if (random == 7) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("arma") == 0 ){
                    if(itns.getId() == 1){
                        qtde = itns.getQuantidade() + 1;
                    }
                }
            }
            if(qtde < 2){
                inventarioCRUD.adicionarArma("001");
            }else {
                inventarioCRUD.alterarQuantidade("arma", "001", qtde);
            }
            txt.setText("Você encontrou uma Espada de uma mão!");
        } else if (random == 8) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("arma") == 0 ){
                    if(itns.getId() == 2){
                        qtde = itns.getQuantidade() + 1;
                    }
                }
            }
            if(qtde < 2){
                inventarioCRUD.adicionarArma("002");
            }else {
                inventarioCRUD.alterarQuantidade("arma", "002", qtde);
            }
            txt.setText("Você encontrou uma Espada de duas mãos!");
        } else if (random == 9) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("arma") == 0 ){
                    if(itns.getId() == 3){
                        qtde = itns.getQuantidade() + 1;
                    }
                }
            }
            if(qtde < 2){
                inventarioCRUD.adicionarArma("003");
            }else {
                inventarioCRUD.alterarQuantidade("arma", "003", qtde);
            }
            txt.setText("Você encontrou uma Adaga!");
        } else if (random == 11) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("arma") == 0 ){
                    if(itns.getId() == 4){
                        qtde = itns.getQuantidade() + 1;
                    }
                }
            }
            if(qtde < 2){
                inventarioCRUD.adicionarArma("004");
            }else {
                inventarioCRUD.alterarQuantidade("arma", "004", qtde);
            }
            txt.setText("Você encontrou um Cajado!");
        } else if (random == 15) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("arma") == 0 ){
                    if(itns.getId() == 6){
                        qtde = itns.getQuantidade() + 1;
                    }
                }
            }
            if(qtde < 2){
                inventarioCRUD.adicionarArma("006");
            }else {
                inventarioCRUD.alterarQuantidade("arma", "006", qtde);
            }
            txt.setText("Você encontrou um Arco!");
        }

        final int random2 = new Random().nextInt(8);
        int qtde2 = 0;
        if (random2 == 1) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("consumivel") == 0){
                    if(itns.getId() == 1){
                        qtde2 = itns.getQuantidade() + 1;
                    }
                }
            }
            inventarioCRUD.alterarQuantidade("consumiveis", "001", qtde2);
            txt2.setText("Você encontrou uma Poção de Vida!");
        } else if (random2 == 3) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("consumivel") == 0){
                    if(itns.getId() == 1){
                        qtde2 = itns.getQuantidade() + 2;
                    }
                }
            }
            inventarioCRUD.alterarQuantidade("consumiveis", "001", qtde2);
            txt2.setText("Você encontrou duas Poções de Vida!");
        } else if (random2 == 5) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("consumivel") == 0){
                    if(itns.getId() == 2){
                        qtde2 = itns.getQuantidade() + 1;
                    }
                }
            }
            inventarioCRUD.alterarQuantidade("consumiveis", "002", qtde2);
            txt2.setText("Você encontrou uma Poção de Mana!");
        } else if (random2 == 7) {
            for(ItemInventario itns : itens){
                if(itns.getTipo().compareTo("consumivel") == 0 ){
                    if(itns.getId() == 2){
                        qtde2 = itns.getQuantidade() + 2;
                    }
                }
            }
            inventarioCRUD.alterarQuantidade("consumiveis", "002", qtde2);
            txt2.setText("Você encontrou duas Poções de Mana!");
        }
    }

    public void upatk(View v){
        int atk = jogador.getAtaque() + 1;
        jogadorCRUD.alteraAtk(atk);
        finish();
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
    }

    public void updef(View v){
        int def = jogador.getDefesa() + 1;
        jogadorCRUD.alteraDef(def);
        finish();
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
    }

    public void upspatk(View v){
        int spatk = jogador.getPodermagico() + 1;
        jogadorCRUD.alteraSpatk(spatk);
        finish();
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
    }
}
