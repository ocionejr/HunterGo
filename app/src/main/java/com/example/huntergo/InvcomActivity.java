package com.example.huntergo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.huntergo.CRUD.ConsumivelCRUD;
import com.example.huntergo.CRUD.InventarioCRUD;
import com.example.huntergo.CRUD.JogadorCRUD;
import com.example.huntergo.Classes.ItemInventario;
import com.example.huntergo.Classes.Jogador;

import java.util.ArrayList;

public class InvcomActivity extends AppCompatActivity {
    private TextView qtde_hp;
    private TextView qtde_mp;
    private ArrayList<ItemInventario> itens;
    private InventarioCRUD inventarioCRUD;
    private JogadorCRUD jogadorCRUD;
    private Jogador jogador;
    int qtde = 0;
    int qtde2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invcom);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.9), (int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        qtde_hp = findViewById(R.id.qtd_hp);
        qtde_mp = findViewById(R.id.qtd_mp);
        inventarioCRUD = InventarioCRUD.getINSTANCE();
        itens = inventarioCRUD.getInventario();
        jogadorCRUD = JogadorCRUD.getINSTANCE();
        jogador = jogadorCRUD.getJogador();

        for(ItemInventario itns : itens){
            if(itns.getTipo().compareTo("consumivel") == 0){
                if(itns.getId() == 1){
                    qtde = itns.getQuantidade();
                }
            }
        }

        for(ItemInventario itns : itens){
            if(itns.getTipo().compareTo("consumivel") == 0){
                if(itns.getId() == 2){
                    qtde2 = itns.getQuantidade();
                }
            }
        }

        qtde_hp.setText(qtde + "");
        qtde_mp.setText(qtde2 + "");
    }

    public void usarHp(View v){
        int aux = 0;

        for(ItemInventario itns : itens){
            if(itns.getTipo().compareTo("consumivel") == 0){
                if(itns.getId() == 1){
                    aux = itns.getQuantidade();
                }
            }
        }

        if(aux > 0){
            inventarioCRUD.alterarQuantidade("consumiveis", "001", aux-1);
            int vida = jogador.getVida() + 10;
            jogador.setVida(vida);
            jogadorCRUD.alteraVida(vida);
            Intent intent=new Intent();
            setResult(1,intent);
            finish();//finishing activity
        }
    }

    public void usarMp(View v){
        int aux2 = 0;

        for(ItemInventario itns : itens){
            if(itns.getTipo().compareTo("consumivel") == 0){
                if(itns.getId() == 2){
                    aux2 = itns.getQuantidade();
                }
            }
        }

        if(aux2 > 0){
            inventarioCRUD.alterarQuantidade("consumiveis", "002", aux2-1);
            int mana = jogador.getMana() + 10;
            jogador.setMana(mana);
            jogadorCRUD.alteraMana(mana);
            Intent intent=new Intent();
            setResult(2,intent);
            finish();//finishing activity
        }
    }

    public void exit(View v){
        Intent intent=new Intent();
        setResult(Activity.RESULT_CANCELED,intent);
        finish();//finishing activity
    }
}
