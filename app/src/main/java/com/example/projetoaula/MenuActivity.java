package com.example.projetoaula;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnFrases = findViewById(R.id.btnFrases);
        Button btnConversorMoeda= findViewById(R.id.btnConversorMoeda);
        Button btnConversorTemperatura= findViewById(R.id.btnConversorTemperatura);
        Button btnConsultarCep = findViewById(R.id.btnConsultarCep);
        Button btnListaCompras = findViewById(R.id.btnListaCompras);

        btnFrases.setOnClickListener(v ->{
            Intent intent = new Intent(MenuActivity.this, FrasesActivity.class);
            startActivity(intent);
        });
        btnConversorMoeda.setOnClickListener(v ->{
            Intent intent = new Intent(MenuActivity.this, MoedaActivity.class);
            startActivity(intent);
        });
        btnConversorTemperatura.setOnClickListener(v ->{
            Intent intent = new Intent(MenuActivity.this, TemperaturaActivity.class);
            startActivity(intent);
        });
        btnConsultarCep.setOnClickListener(v ->{
            Intent intent = new Intent(MenuActivity.this, ConsultarCepActivity.class);
            startActivity(intent);
        });
        btnListaCompras.setOnClickListener(v ->{
            Intent intent = new Intent(MenuActivity.this, ListaCompraActivity.class);
            startActivity(intent);
        });


    }
}