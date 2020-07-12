package com.example.huntergo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.huntergo.CRUD.ArmaCRUD;
import com.example.huntergo.CRUD.ArmaduraCRUD;
import com.example.huntergo.CRUD.ConsumivelCRUD;
import com.example.huntergo.CRUD.InventarioCRUD;
import com.example.huntergo.CRUD.ItensEquipadorsCRUD;
import com.example.huntergo.CRUD.JogadorCRUD;
import com.example.huntergo.Classes.Arma;
import com.example.huntergo.Classes.Armadura;
import com.example.huntergo.Classes.Consumivel;
import com.example.huntergo.Classes.ItemInventario;
import com.example.huntergo.Classes.ItensEquipados;
import com.example.huntergo.Classes.Jogador;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class InventarioActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String uid;
    InventarioCRUD inventarioCRUD;
    ArmaCRUD armaCRUD;
    ArmaduraCRUD armaduraCRUD;
    ConsumivelCRUD consumivelCRUD;
    ItensEquipadorsCRUD itensEquipadorsCRUD;
    ArrayList<Arma> armas;
    ArrayList<Armadura> armaduras;
    ArrayList<Consumivel> consumiveis;
    ArrayList<ItemInventario> itens;
    ItensEquipados itensEquipados;
    LinearLayout.LayoutParams params;
    JogadorCRUD jogadorCRUD;
    Jogador jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        configurarBottomNav();

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        inventarioCRUD = InventarioCRUD.getINSTANCE();
        armaCRUD = ArmaCRUD.getINSTANCE();
        armaduraCRUD = ArmaduraCRUD.getINSTANCE();
        consumivelCRUD = ConsumivelCRUD.getINSTANCE();
        itensEquipadorsCRUD = ItensEquipadorsCRUD.getINSTANCE();
        jogadorCRUD = JogadorCRUD.getINSTANCE();

        armas = armaCRUD.getArmas();
        armaduras = armaduraCRUD.getArmaduras();
        consumiveis = consumivelCRUD.getConsumiveis();
        itens = inventarioCRUD.getInventario();
        itensEquipados = itensEquipadorsCRUD.getItensEquipados();
        jogador = jogadorCRUD.getJogador();
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );

        configurarItemList();
        configurarItensEquipados();
        configurarStatus();
    }

    private void configurarBottomNav(){
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.inventarioSelect);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.inventarioSelect:
                        return true;

                    case R.id.mapaSelect:
                       itens = inventarioCRUD.getInventario();

                        finish();
                        return true;

                    case R.id.sairSelect:
                        startActivity(new Intent(getApplicationContext(), PopupActivity.class));
                        return true;
                }

                return false;
            }
        });
    }

    private void configurarItemList(){
        final LinearLayout listItem = findViewById(R.id.listItem);
        Log.d("Comsumiveis", "z");

        for(ItemInventario item : itens){
            Log.d("Comsumiveis", item.getTipo());
            final View v = View.inflate(this, R.layout.view_item, null);

            final TextView nomeItem= v.findViewById(R.id.nomeItem);
            final TextView qtdItem = v.findViewById(R.id.quantItem);
            ImageView imgItem = v.findViewById(R.id.imageView);
            TextView descItem = v.findViewById(R.id.descItem);
            final TextView idItem = v.findViewById(R.id.idItem);
            Button btItem = v.findViewById(R.id.btItem);
            final LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
            LinearLayout dadosItem = v.findViewById(R.id.dadosItem);

            if(item.getTipo().compareTo("arma") == 0){
                for(Arma arma : armas){
                    if(arma.getId() == item.getId()){
                        nomeItem.setText(arma.getNome());
                        qtdItem.setText(item.getQuantidade());
                        imgItem.setImageResource(item.getImage());
                        idItem.setText(item.getId());
                        String desc = "Dano: " + arma.getDano() +
                                      "\nVelocidade: "  +  arma.getVelocidade() +
                                      "\nMão: " + arma.getMao();
                        descItem.setText(desc);
                        btItem.setText("Equipar");
                    }
                }
            }else if(item.getTipo().compareTo("armadura") == 0){
                for(Armadura armadura : armaduras){
                    if(armadura.getId() == item.getId()){
                        nomeItem.setText(armadura.getNome());
                        qtdItem.setText(item.getQuantidade());
                        imgItem.setImageResource(item.getImage());
                        idItem.setText(item.getId());
                        String desc = "Defesa: " + armadura.getDefesa() +
                                "\nVelocidade: "  +  armadura.getVelocidade();
                        descItem.setText(desc);
                        btItem.setText("Equipar");
                    }
                }
            } else if(item.getTipo().compareTo("consumivel") == 0){
                Log.d("Consumiveis", "a");
                for(final Consumivel consumivel : consumiveis){
                    Log.d("Consumiveis", "b");
                    if(consumivel.getId() == item.getId()){
                        Log.d("Consumiveis", "c");
                        nomeItem.setText(consumivel.getNome());
                        qtdItem.setText("" + item.getQuantidade());
                        imgItem.setImageResource(item.getImage());
                        descItem.setText(consumivel.getEfeito());
                        idItem.setText("" + item.getId());
                        btItem.setText("Usar");

                        btItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v2) {
                                switch(consumivel.getId()){
                                    case 1:
                                        TextView vidaPersonagem = findViewById(R.id.textVida);
                                        String[] vidaString = vidaPersonagem.getText().toString().split("/");
                                        int vida = Integer.parseInt(vidaString[0]);
                                        vida += 10;
                                        if(vida > 100)
                                            vida -= (vida - 100);

                                        vidaPersonagem.setText(vida + "/100");
                                        jogadorCRUD.alteraVida(vida);
                                }

                                if(Integer.parseInt(qtdItem.getText().toString()) == 1){
                                    listItem.removeView(v);
                                    inventarioCRUD.excluirItem("consumiveis", "00" + idItem.getText());

                                }else{
                                    int qtd = Integer.parseInt(qtdItem.getText().toString()) - 1;
                                    inventarioCRUD.alterarQuantidade("consumiveis", "00" + idItem.getText(), qtd);
                                    qtdItem.setText("" + qtd);
                                }
                            }
                        });
                    }
                }
            }

            dadosItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(detalheItem.getVisibility() == LinearLayout.GONE) {
                    detalheItem.setVisibility(LinearLayout.VISIBLE);
                } else{
                    detalheItem.setVisibility(LinearLayout.GONE);
                }
                }
            });

            listItem.addView(v);

        }
    }

    private void configurarItensEquipados(){
        LinearLayout itensEquipadosView = findViewById(R.id.itensEquipados);

        for(Arma arma : armas){
            if(arma.getId() == Integer.parseInt(itensEquipados.getMaoDireita())){
                View v = View.inflate(this, R.layout.item_equipado, null);
                v.setLayoutParams(params);

                TextView nomeItem= v.findViewById(R.id.nomeItem);
                ImageView imgItem = v.findViewById(R.id.imageView);
                TextView descItem = v.findViewById(R.id.descItem);
                Button btItem = v.findViewById(R.id.btItem);


                final LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
                final LinearLayout dadosItem = v.findViewById(R.id.dadosItem);

                nomeItem.setText("Mão Direita:\n\n" + arma.getNome());

                switch(arma.getId()){
                    case 1: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 2: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 3: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 4: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 5: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 6: imgItem.setImageResource(R.drawable.esqueleto); break;
                }

                String desc = "Dano: " + arma.getDano() +
                        "\nVelocidade: "  +  arma.getVelocidade() +
                        "\nMão: " + arma.getMao();
                descItem.setText(desc);

                dadosItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dadosItem.setVisibility(LinearLayout.GONE);
                        detalheItem.setVisibility(LinearLayout.VISIBLE);

                    }
                });

                detalheItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        detalheItem.setVisibility(LinearLayout.GONE);
                        dadosItem.setVisibility(LinearLayout.VISIBLE);
                    }
                });

                itensEquipadosView.addView(v);
            }

            if(arma.getId() == Integer.parseInt(itensEquipados.getMaoEsquerda())){
                View v = View.inflate(this, R.layout.item_equipado, null);
                v.setLayoutParams(params);

                TextView nomeItem= v.findViewById(R.id.nomeItem);
                ImageView imgItem = v.findViewById(R.id.imageView);
                TextView descItem = v.findViewById(R.id.descItem);
                Button btItem = v.findViewById(R.id.btItem);

                final LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
                final LinearLayout dadosItem = v.findViewById(R.id.dadosItem);

                nomeItem.setText("Mão Esquerda:\n\n" + arma.getNome());

                switch(arma.getId()){
                    case 1: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 2: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 3: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 4: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 5: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 6: imgItem.setImageResource(R.drawable.esqueleto); break;
                }

                String desc = "Dano: " + arma.getDano() +
                        "\nVelocidade: "  +  arma.getVelocidade() +
                        "\nMão: " + arma.getMao();
                descItem.setText(desc);

                dadosItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dadosItem.setVisibility(LinearLayout.GONE);
                        detalheItem.setVisibility(LinearLayout.VISIBLE);
                    }
                });

                detalheItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        detalheItem.setVisibility(LinearLayout.GONE);
                        dadosItem.setVisibility(LinearLayout.VISIBLE);
                    }
                });

                itensEquipadosView.addView(v);
            }
        }

        for(Armadura armadura : armaduras){
            if(armadura.getId() == Integer.parseInt(itensEquipados.getArmadura())){
                View v = View.inflate(this, R.layout.item_equipado, null);
                v.setLayoutParams(params);

                TextView nomeItem = v.findViewById(R.id.nomeItem);
                ImageView imgItem = v.findViewById(R.id.imageView);
                TextView descItem = v.findViewById(R.id.descItem);
                Button btItem = v.findViewById(R.id.btItem);

                final LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
                final LinearLayout dadosItem = v.findViewById(R.id.dadosItem);

                nomeItem.setText("Armadura:\n\n" + armadura.getNome());

                switch(armadura.getId()){
                    case 1: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 2: imgItem.setImageResource(R.drawable.esqueleto); break;
                    case 3: imgItem.setImageResource(R.drawable.esqueleto); break;
                }

                String desc = "Defesa: " + armadura.getDefesa() +
                        "\nVelocidade: "  +  armadura.getVelocidade();
                descItem.setText(desc);

                dadosItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dadosItem.setVisibility(LinearLayout.GONE);
                        detalheItem.setVisibility(LinearLayout.VISIBLE);

                    }
                });

                detalheItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        detalheItem.setVisibility(LinearLayout.GONE);
                        dadosItem.setVisibility(LinearLayout.VISIBLE);
                    }
                });

                itensEquipadosView.addView(v);
            }
        }
    }

    private void configurarStatus(){
        TextView textVida = findViewById(R.id.textVida);
        TextView textMana = findViewById(R.id.textMana);
        TextView textAtaque = findViewById(R.id.textAtaque);
        TextView textDefesa = findViewById(R.id.textDefesa);
        TextView textVelocidade = findViewById(R.id.textVelocidade);
        TextView textPoder = findViewById(R.id.textPoder);

        textVida.setText(jogador.getVida() + "/" + "100");
        textMana.setText(jogador.getMana() + "/" + "100");
        textAtaque.setText(jogador.getAtaque() + "/" + "100");
        textDefesa.setText(jogador.getDefesa() + "/" + "100");
        textVelocidade.setText(jogador.getVelocidade() + "/" + "100");
        textPoder.setText(jogador.getPodermagico() + "/" + "100");
    }
}
