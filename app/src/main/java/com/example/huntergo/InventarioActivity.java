package com.example.huntergo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.huntergo.CRUD.InventarioCRUD;
import com.example.huntergo.Classes.ItemInventario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class InventarioActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String uid;
    InventarioCRUD inventarioCRUD;
    ArrayList<ItemInventario> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        configurarBottomNav();

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        inventarioCRUD = new InventarioCRUD();
        inventarioCRUD.IniciarListeners(uid);

        itens = getIntent().getParcelableArrayListExtra("itensInventario");
        for (ItemInventario item2 : itens){
            Log.d("inventario", item2.getId());
            Log.d("inventario", item2.getTipo());
        }

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


                        for (ItemInventario item2 : itens){
                            Log.d("inventario", item2.getId());
                            Log.d("inventario", item2.getTipo());
                        }
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
}
