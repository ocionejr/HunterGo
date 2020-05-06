package com.example.huntergo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ClasseActivity extends AppCompatActivity {

    private Spinner spinner;
    public Button btSelecionar;
    private static final String[] classes = {"Guerreiro", "Caçador", "Mago"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classe);

        spinner = findViewById(R.id.spinner);
        btSelecionar = findViewById(R.id.btSelecionar);
        configurarSpinner();

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

        switch (classeSelecionada){
            case "Guerreiro":

            break;
            case "Caçador":

            break;
            case "Mago":

            break;
        }
    }
}
