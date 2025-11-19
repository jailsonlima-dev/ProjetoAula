package com.example.projetoaula;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MoedaActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moeda);

        TextView txtResultadoMoeda = findViewById(R.id.txtResultadoMoeda);
        Button btnConverterMoeda = findViewById(R.id.btnConverterMoeda);
        EditText txtValor = findViewById(R.id.txtValor);
        Spinner spMoeda = findViewById(R.id.spMoeda);

        final double taxaDolar = 0.20;
        final double taxaEuro = 0.18;

        //Criar listar de opções
        String[] moedas = {"Dolar", "Euro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                moedas
        );

        //define o layout do spinner
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        //aplicar os valores ao spinner
        spMoeda.setAdapter(adapter);
        btnConverterMoeda.setOnClickListener(v -> {
            double valor = Double.parseDouble(txtValor.getText().toString());

            double resultado = 0;
            String moeda = spMoeda.getSelectedItem().toString();

            if (moeda.equals("Dolar")) {
                resultado = valor * taxaDolar;
            } else if (moeda.equals("Euro")) {
                resultado = valor * taxaEuro;

            }

            txtResultadoMoeda.setText(
                    String.format("valor Convertido: %.2f %s", resultado, moeda)

            );
        });

    }
}