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
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    boolean achou;
    boolean achou2;
    int idMaoDireira;
    int idMaoEsquerda;
    int idArmadura;
    LinearLayout listItem;
    LinearLayout itensEquipadosView;
    DatabaseReference reference1;
    DatabaseReference referenceUID1;
    int posicao1;
    DatabaseReference reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        configurarBottomNav();

        idMaoDireira = View.generateViewId();
        idMaoEsquerda = View.generateViewId();
        idArmadura = View.generateViewId();

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
        listItem = findViewById(R.id.listItem);
        itensEquipadosView = findViewById(R.id.itensEquipados);
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );

        configurarItemList();
        configurarItensEquipados();
        configurarStatus();

        reference1 = FirebaseDatabase.getInstance().getReference("basededados").child("inventario");
        reference2 = FirebaseDatabase.getInstance().getReference("basededados").child("itensEquipados");
        IniciarListeners(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
            Log.d("Item", item.getTipo());
            final View v = View.inflate(this, R.layout.view_item, null);

            final TextView nomeItem= v.findViewById(R.id.nomeItem);
            final TextView qtdItem = v.findViewById(R.id.quantItem);
            ImageView imgItem = v.findViewById(R.id.imageView);
            TextView descItem = v.findViewById(R.id.descItem);
            final TextView idItem = v.findViewById(R.id.idItem);
            Button btItem = v.findViewById(R.id.btItem);
            final LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
            LinearLayout dadosItem = v.findViewById(R.id.dadosItem);
            Button btItem2 = v.findViewById(R.id.btItem2);

            if(item.getTipo().compareTo("arma") == 0){
                for(final Arma arma : armas){
                    if(arma.getId() == item.getId()) {
                        nomeItem.setText(arma.getNome());
                        qtdItem.setText(Integer.toString(item.getQuantidade()));
                        switch(arma.getId()){
                            case 1: imgItem.setImageResource(R.drawable.sword); break;
                            case 2: imgItem.setImageResource(R.drawable.sword); break;
                            case 3: imgItem.setImageResource(R.drawable.dagger); break;
                            case 4: imgItem.setImageResource(R.drawable.staff); break;
                            case 6: imgItem.setImageResource(R.drawable.bow); break;
                        }
                        idItem.setText("" + item.getId());
                        String desc = "Dano: " + arma.getDano() +
                                "\nVelocidade: " + arma.getVelocidade() +
                                "\nMão: " + arma.getMao();
                        descItem.setText(desc);

                        if (arma.getMao() == 2){
                            btItem.setText("Equipar");
                            btItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final View v3 = itensEquipadosView.findViewById(idMaoDireira);
                                    final View v4 = itensEquipadosView.findViewById(idMaoEsquerda);
                                    if(v3.getTag().toString() != "" || v4.getTag().toString() != "" ){
                                        Toast.makeText(getApplicationContext(), "Desequipe suas armas primeiro", Toast.LENGTH_SHORT).show();
                                    }else{
                                        itensEquipadorsCRUD.equiparMaoDireita("00" + arma.getId());

                                        if (Integer.parseInt(qtdItem.getText().toString()) == 1) {
                                            inventarioCRUD.excluirItem("armas", "00" + idItem.getText());
                                        } else {
                                            int qtd = Integer.parseInt(qtdItem.getText().toString()) - 1;
                                            inventarioCRUD.alterarQuantidade("armas", "00" + idItem.getText(), qtd);
                                        }
                                    }
                                }
                            });
                        }else{
                            btItem.setText("Equipar Mão Direita");
                            btItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final View v3 = itensEquipadosView.findViewById(idMaoDireira);
                                    if(v3.getTag().toString() != ""){
                                        Toast.makeText(getApplicationContext(), "Desequipe sua mão direita primeiro", Toast.LENGTH_SHORT).show();
                                    }else{
                                        itensEquipadorsCRUD.equiparMaoDireita("00" + arma.getId());

                                        if (Integer.parseInt(qtdItem.getText().toString()) == 1) {
                                            inventarioCRUD.excluirItem("armas", "00" + idItem.getText());
                                        } else {
                                            int qtd = Integer.parseInt(qtdItem.getText().toString()) - 1;
                                            inventarioCRUD.alterarQuantidade("armas", "00" + idItem.getText(), qtd);
                                        }
                                    }
                                }
                            });

                            btItem2.setVisibility(View.VISIBLE);
                            btItem2.setText("Equipar Mão Esquerda");
                            btItem2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final View v4 = itensEquipadosView.findViewById(idMaoEsquerda);
                                    if(v4.getTag().toString() != ""){
                                        Toast.makeText(getApplicationContext(), "Desequipe sua mão esquerda primeiro", Toast.LENGTH_SHORT).show();
                                    }else{
                                        itensEquipadorsCRUD.equiparMaoEsquerda("00" + arma.getId());

                                        if (Integer.parseInt(qtdItem.getText().toString()) == 1) {
                                            inventarioCRUD.excluirItem("armas", "00" + idItem.getText());
                                        } else {
                                            int qtd = Integer.parseInt(qtdItem.getText().toString()) - 1;
                                            inventarioCRUD.alterarQuantidade("armas", "00" + idItem.getText(), qtd);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }else if(item.getTipo().compareTo("armadura") == 0){
                Log.d("armadura", "armadura");
                for(final Armadura armadura : armaduras){
                    if(armadura.getId() == item.getId()){
                        Log.d("armadura", "armadura");
                        nomeItem.setText(armadura.getNome());
                        qtdItem.setText(Integer.toString(item.getQuantidade()));
                        switch(armadura.getId()){
                            case 1: imgItem.setImageResource(R.drawable.leather); break;
                            case 2: imgItem.setImageResource(R.drawable.metal); break;
                            case 3: imgItem.setImageResource(R.drawable.cloth); break;
                        }
                        idItem.setText(""+item.getId());
                        String desc = "Defesa: " + armadura.getDefesa() +
                                "\nVelocidade: "  +  armadura.getVelocidade();
                        descItem.setText(desc);
                        btItem.setText("Equipar");

                        btItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v2) {
                                final View v3 = itensEquipadosView.findViewById(idArmadura);
                                Log.d("idArmadura", ""+idArmadura);

                                if(v3.getTag().toString() != ""){
                                    Log.d("Armadura", "a");
                                    Toast.makeText(getApplicationContext(), "Desequipe a armadura primeiro",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.d("Armadura", "b");
                                    itensEquipadorsCRUD.equiparArmadura("00" + idItem.getText().toString());

                                    if (Integer.parseInt(qtdItem.getText().toString()) == 1) {
                                        inventarioCRUD.excluirItem("armaduras", "00" + idItem.getText());
                                    } else {
                                        int qtd = Integer.parseInt(qtdItem.getText().toString()) - 1;
                                        inventarioCRUD.alterarQuantidade("armaduras", "00" + idItem.getText(), qtd);
                                    }
                                }
                            }
                        });
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
                        switch(consumivel.getId()){
                            case 1: imgItem.setImageResource(R.drawable.hp); break;
                            case 2: imgItem.setImageResource(R.drawable.mp); break;
                        }
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
                                        break;

                                    case 2:
                                        TextView manaPersonagem = findViewById(R.id.textMana);
                                        String[] manaString = manaPersonagem.getText().toString().split("/");
                                        int mana = Integer.parseInt(manaString[0]);
                                        mana += 10;
                                        if(mana > 100)
                                            mana -= (mana - 100);

                                        manaPersonagem.setText(mana + "/100");
                                        jogadorCRUD.alteraMana(mana);
                                        break;
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
        achou = false;
        achou2 = false;
        for(final Arma arma : armas){
            if(arma.getId() == Integer.parseInt(itensEquipados.getMaoDireita())){
                achou = true;
                View v = View.inflate(this, R.layout.item_equipado, null);
                v.setLayoutParams(params);

                TextView nomeItem= v.findViewById(R.id.nomeItem);
                ImageView imgItem = v.findViewById(R.id.imageView);
                TextView descItem = v.findViewById(R.id.descItem);
                Button btItem = v.findViewById(R.id.btItem);
                final TextView idItem = v.findViewById(R.id.idItem);
                v.setTag(arma.getId());
                v.setId(idMaoDireira);

                final LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
                final LinearLayout dadosItem = v.findViewById(R.id.dadosItem);

                nomeItem.setText("Mão Direita:\n\n" + arma.getNome());
                idItem.setText(""+arma.getId());

                switch(arma.getId()){
                    case 1: imgItem.setImageResource(R.drawable.sword); break;
                    case 2: imgItem.setImageResource(R.drawable.sword); break;
                    case 3: imgItem.setImageResource(R.drawable.dagger); break;
                    case 4: imgItem.setImageResource(R.drawable.staff); break;
                    case 6: imgItem.setImageResource(R.drawable.bow); break;
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

                btItem.setText("Desequipar");
                btItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v2) {
                        int count = listItem.getChildCount();
                        boolean jaExiste = false;
                        for (int i = 0; i < count; i++) {
                            View view = listItem.getChildAt(i);
                            TextView id = view.findViewById(R.id.idItem);
                            TextView qtd = view.findViewById(R.id.quantItem);
                            if (id.getText().toString().compareTo(idItem.getText().toString()) == 0) {
                                jaExiste = true;
                                int quant = Integer.parseInt(qtd.getText().toString()) + 1;
                                inventarioCRUD.alterarQuantidade("armas", "00" + idItem.getText().toString(), quant);
                            }
                        }

                        if (!jaExiste) {
                            inventarioCRUD.adicionarArma("00" + arma.getId());
                        }
                        itensEquipadorsCRUD.desequiparMaoDireita();

                    }
                });

                itensEquipadosView.addView(v);
                Log.d("Equipou", "Equipou");
            }

            if(arma.getId() == Integer.parseInt(itensEquipados.getMaoEsquerda())){
                achou2 = true;
                final View v = View.inflate(this, R.layout.item_equipado, null);
                v.setLayoutParams(params);

                final TextView nomeItem= v.findViewById(R.id.nomeItem);
                final ImageView imgItem = v.findViewById(R.id.imageView);
                TextView descItem = v.findViewById(R.id.descItem);
                Button btItem = v.findViewById(R.id.btItem);
                final TextView idItem = v.findViewById(R.id.idItem);
                v.setTag(arma.getId());
                v.setId(idMaoEsquerda);

                final LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
                final LinearLayout dadosItem = v.findViewById(R.id.dadosItem);

                nomeItem.setText("Mão Esquerda:\n\n" + arma.getNome());
                idItem.setText(""+arma.getId());

                switch(arma.getId()){
                    case 1: imgItem.setImageResource(R.drawable.sword); break;
                    case 2: imgItem.setImageResource(R.drawable.sword); break;
                    case 3: imgItem.setImageResource(R.drawable.dagger); break;
                    case 4: imgItem.setImageResource(R.drawable.staff); break;
                    case 6: imgItem.setImageResource(R.drawable.bow); break;
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

                btItem.setText("Desequipar");
                btItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v2) {
                        int count = listItem.getChildCount();
                        boolean jaExiste = false;
                        for (int i = 0; i < count; i++) {
                            View view = listItem.getChildAt(i);
                            TextView id = view.findViewById(R.id.idItem);
                            TextView qtd = view.findViewById(R.id.quantItem);
                            if (id.getText().toString().compareTo(idItem.getText().toString()) == 0) {
                                jaExiste = true;
                                int quant = Integer.parseInt(qtd.getText().toString()) + 1;
                                inventarioCRUD.alterarQuantidade("armas", "00" + idItem.getText().toString(), quant);
                            }
                        }

                        if (!jaExiste) {
                            inventarioCRUD.adicionarArma("00" + arma.getId());
                        }
                        itensEquipadorsCRUD.desequiparMaoEsquerda();

                    }
                });

                itensEquipadosView.addView(v);
                Log.d("Equipou", "Equipou");
            }
        }

        if(!achou){
            View v = View.inflate(this, R.layout.item_equipado, null);
            v.setLayoutParams(params);

            TextView nomeItem = v.findViewById(R.id.nomeItem);
            ImageView imgItem = v.findViewById(R.id.imageView);

            LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
            LinearLayout dadosItem = v.findViewById(R.id.dadosItem);

            nomeItem.setText("Mão Direita:\n\n" + "Nada Equipado");
            imgItem.setImageResource(android.R.color.transparent);
            detalheItem.setVisibility(LinearLayout.GONE);
            dadosItem.setVisibility(LinearLayout.VISIBLE);
            dadosItem.setOnClickListener(null);
            v.setTag("");
            v.setId(idMaoDireira);
            Log.d("idMaoDireita", v.getId() +"");

            itensEquipadosView.addView(v);
            Log.d("Equipou", "Equipou");
        }

        if(!achou2){
            View v = View.inflate(this, R.layout.item_equipado, null);
            v.setLayoutParams(params);

            TextView nomeItem = v.findViewById(R.id.nomeItem);
            ImageView imgItem = v.findViewById(R.id.imageView);

            LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
            LinearLayout dadosItem = v.findViewById(R.id.dadosItem);

            nomeItem.setText("Mão Esquerda:\n\n" + "Nada Equipado");
            imgItem.setImageResource(android.R.color.transparent);
            detalheItem.setVisibility(LinearLayout.GONE);
            dadosItem.setVisibility(LinearLayout.VISIBLE);
            dadosItem.setOnClickListener(null);
            v.setTag("");
            v.setId(idMaoEsquerda);
            Log.d("idMaoEsquerda", v.getId() +"");

            itensEquipadosView.addView(v);
            Log.d("Equipou", "Equipou");
        }

        achou = false;
        for(final Armadura armadura : armaduras){

            if(armadura.getId() == Integer.parseInt(itensEquipados.getArmadura())){
                achou = true;
                final View v = View.inflate(this, R.layout.item_equipado, null);
                v.setLayoutParams(params);

                final TextView nomeItem = v.findViewById(R.id.nomeItem);
                final ImageView imgItem = v.findViewById(R.id.imageView);
                TextView descItem = v.findViewById(R.id.descItem);
                Button btItem = v.findViewById(R.id.btItem);
                final TextView idItem = v.findViewById(R.id.idItem);
                v.setTag(armadura.getId());
                v.setId(idArmadura);

                final LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
                final LinearLayout dadosItem = v.findViewById(R.id.dadosItem);

                nomeItem.setText("Armadura:\n\n" + armadura.getNome());
                idItem.setText(""+armadura.getId());

                switch(armadura.getId()){
                    case 1: imgItem.setImageResource(R.drawable.leather); break;
                    case 2: imgItem.setImageResource(R.drawable.metal); break;
                    case 3: imgItem.setImageResource(R.drawable.cloth); break;
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

                btItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v2) {
                        int count = listItem.getChildCount();
                        boolean jaExiste = false;
                        for (int i = 0; i < count; i++) {
                            View view = listItem.getChildAt(i);
                            TextView id = view.findViewById(R.id.idItem);
                            TextView qtd = view.findViewById(R.id.quantItem);
                            if (id.getText().toString().compareTo(idItem.getText().toString()) == 0) {
                                jaExiste = true;
                                int quant = Integer.parseInt(qtd.getText().toString()) + 1;
                                inventarioCRUD.alterarQuantidade("armaduras", "00" + idItem.getText().toString(), quant);
                            }
                        }

                        if (!jaExiste) {
                            final View v3 = View.inflate(getApplicationContext(), R.layout.view_item, null);
                            final TextView nomeItem2 = v3.findViewById(R.id.nomeItem);
                            final TextView qtdItem2 = v3.findViewById(R.id.quantItem);
                            ImageView imgItem2 = v3.findViewById(R.id.imageView);
                            TextView descItem2 = v3.findViewById(R.id.descItem);
                            final TextView idItem2 = v3.findViewById(R.id.idItem);
                            Button btItem2 = v3.findViewById(R.id.btItem);
                            final LinearLayout detalheItem2 = v3.findViewById(R.id.detalheItem);
                            LinearLayout dadosItem2 = v3.findViewById(R.id.dadosItem);

                            nomeItem2.setText(armadura.getNome());
                            qtdItem2.setText("1");
                            switch (armadura.getId()) {
                                case 1:
                                    imgItem2.setImageResource(R.drawable.leather);
                                    break;
                                case 2:
                                    imgItem2.setImageResource(R.drawable.metal);
                                    break;
                                case 3:
                                    imgItem2.setImageResource(R.drawable.cloth);
                                    break;
                            }
                            idItem2.setText("" + armadura.getId());
                            String desc = "Defesa: " + armadura.getDefesa() +
                                    "\nVelocidade: " + armadura.getVelocidade();
                            descItem2.setText(desc);
                            btItem2.setText("Equipar");

                            dadosItem2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (detalheItem2.getVisibility() == LinearLayout.GONE) {
                                        detalheItem2.setVisibility(LinearLayout.VISIBLE);
                                    } else {
                                        detalheItem2.setVisibility(LinearLayout.GONE);
                                    }
                                }
                            });

                            listItem.addView(v3);
                            inventarioCRUD.adicionarArmadura("00" + armadura.getId());
                        }

                        itensEquipadorsCRUD.desequiparArmadura();

                    }
                });

                itensEquipadosView.addView(v);
                Log.d("Equipou", "Equipou");
            }
        }

        if(!achou){
            View v = View.inflate(this, R.layout.item_equipado, null);
            v.setLayoutParams(params);

            TextView nomeItem = v.findViewById(R.id.nomeItem);
            ImageView imgItem = v.findViewById(R.id.imageView);

            LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
            LinearLayout dadosItem = v.findViewById(R.id.dadosItem);

            nomeItem.setText("Armadura:\n\n" + "Nada Equipado");
            imgItem.setImageResource(android.R.color.transparent);
            detalheItem.setVisibility(LinearLayout.GONE);
            dadosItem.setVisibility(LinearLayout.VISIBLE);
            dadosItem.setOnClickListener(null);
            v.setTag("");
            v.setId(idArmadura);
            Log.d("idArmadura", v.getId() +"");

            itensEquipadosView.addView(v);
            Log.d("Equipou", "Equipou");
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

    public final void updateUI(){
        listItem.removeAllViews();
        itensEquipadosView.removeAllViews();
        configurarItemList();
        configurarItensEquipados();
    }

    public void IniciarListeners(String uid) {
        this.uid = uid;

        referenceUID1 = reference1.child(uid);
        referenceUID1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itens.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    for (DataSnapshot data2 : data.getChildren()){
                        switch (data.getKey()){
                            case "consumiveis":
                                Log.d("inventario", "consumivel: " + data2.getKey());
                                itens.add(new ItemInventario(Integer.parseInt(data2.getKey()), (int)(long)data2.getValue(), "consumivel", R.drawable.esqueleto));
                                posicao1 = itens.size();
                                Log.d("inventario", "consumivel: " + posicao1);
                                break;

                            case "armas":
                                itens.add(new ItemInventario(Integer.parseInt(data2.getKey()), (int)(long)data2.getValue(), "arma", R.drawable.esqueleto));
                                break;

                            case "armaduras":
                                Log.d("inventario", "armadura: " + data2.getKey());
                                itens.add(new ItemInventario(Integer.parseInt(data2.getKey()), (int)(long)data2.getValue(), "armadura", R.drawable.esqueleto));
                                posicao1 = itens.size();
                                Log.d("inventario", "armadura: " + posicao1);
                                break;

                            default:
                                itens.add(new ItemInventario(99, 2, data.getKey(), 22));
                                break;
                        }

                    }
                }
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference2 = reference2.child(uid);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itensEquipados = dataSnapshot.getValue(ItensEquipados.class);
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
