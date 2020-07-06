package com.example.huntergo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huntergo.CRUD.ArmaCRUD;
import com.example.huntergo.CRUD.ArmaduraCRUD;
import com.example.huntergo.CRUD.ConsumivelCRUD;
import com.example.huntergo.CRUD.InventarioCRUD;
import com.example.huntergo.Classes.Arma;
import com.example.huntergo.Classes.Armadura;
import com.example.huntergo.Classes.Consumivel;
import com.example.huntergo.Classes.ItemInventario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class InventarioActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String uid;
    InventarioCRUD inventarioCRUD;
    ArmaCRUD armaCRUD;
    ArmaduraCRUD armaduraCRUD;
    ConsumivelCRUD consumivelCRUD;
    ArrayList<Arma> armas;
    ArrayList<Armadura> armaduras;
    ArrayList<Consumivel> consumiveis;
    ArrayList<ItemInventario> itens;

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

        armas = armaCRUD.getArmas();
        armaduras = armaduraCRUD.getArmaduras();
        consumiveis = consumivelCRUD.getConsumiveis();
        itens = inventarioCRUD.getInventario();

        configurarItemList();
    }

    public void configurarBottomNav(){
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

    public void configurarItemList(){
        LinearLayout listItem = findViewById(R.id.listItem);
        Log.d("Comsumiveis", "z");

        for(ItemInventario item : itens){
            Log.d("Comsumiveis", item.getTipo());
            View v = View.inflate(this, R.layout.view_item, null);

            TextView nomeItem= v.findViewById(R.id.nomeItem);
            TextView qtdItem = v.findViewById(R.id.quantItem);
            ImageView imgItem = v.findViewById(R.id.imageView);
            TextView descItem = v.findViewById(R.id.descItem);
            Button btItem = v.findViewById(R.id.btItem);
            final LinearLayout detalheItem = v.findViewById(R.id.detalheItem);
            LinearLayout dadosItem = v.findViewById(R.id.dadosItem);

            if(item.getTipo().compareTo("arma") == 0){
                for(Arma arma : armas){
                    if(arma.getId() == item.getId()){
                        nomeItem.setText(arma.getNome());
                        qtdItem.setText(item.getQuantidade());
                        imgItem.setImageResource(item.getImage());
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
                        String desc = "Defesa: " + armadura.getDefesa() +
                                "\nVelocidade: "  +  armadura.getVelocidade();
                        descItem.setText(desc);
                        btItem.setText("Equipar");
                    }
                }
            } else if(item.getTipo().compareTo("consumivel") == 0){
                Log.d("Consumiveis", "a");
                for(Consumivel consumivel : consumiveis){
                    Log.d("Consumiveis", "b");
                    if(consumivel.getId() == item.getId()){
                        Log.d("Consumiveis", "c");
                        nomeItem.setText(consumivel.getNome());
                        qtdItem.setText("" + item.getQuantidade());
                        imgItem.setImageResource(item.getImage());
                        descItem.setText(consumivel.getEfeito());
                        btItem.setText("Usar");
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
}
