package com.example.huntergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.huntergo.CRUD.InventarioCRUD;
import com.example.huntergo.CRUD.ItensEquipadorsCRUD;
import com.example.huntergo.CRUD.JogadorCRUD;
import com.google.firebase.auth.FirebaseAuth;

public class ClasseActivity extends AppCompatActivity {

    private Spinner spinner;
    public Button btSelecionar;
    private static final String[] classes = {"Guerreiro", "Caçador", "Mago"};
    private JogadorCRUD jogadorCRUD;
    private InventarioCRUD inventarioCRUD;
    private ItensEquipadorsCRUD itensEquipadorsCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classe);

        spinner = findViewById(R.id.spinner);
        btSelecionar = findViewById(R.id.btSelecionar);
        configurarSpinner();
        jogadorCRUD = JogadorCRUD.getINSTANCE();
        inventarioCRUD = InventarioCRUD.getINSTANCE();
        itensEquipadorsCRUD = ItensEquipadorsCRUD.getINSTANCE();

    }

    public void configurarSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, classes);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setDropDownVerticalOffset(spinner.getDropDownVerticalOffset() + spinner.getHeight());
            }
        });
    }

    public void onClickSelecionar(View view){

        String classeSelecionada = spinner.getSelectedItem().toString();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("classeselecionada", classeSelecionada);

        switch (classeSelecionada){
            case "Guerreiro":
                Log.d("classeselecionada", classeSelecionada);
                jogadorCRUD.criarGuerreiro(uid);
            break;
            case "Caçador":
                Log.d("classeselecionada", classeSelecionada);
                jogadorCRUD.criarCaçador(uid);
            break;
            case "Mago":
                Log.d("classeselecionada", classeSelecionada);
                jogadorCRUD.criarMago(uid);
            break;
        }

        inventarioCRUD.criarInventario(uid);
        startActivity(new Intent(this, MapsActivity.class));
    }
}
