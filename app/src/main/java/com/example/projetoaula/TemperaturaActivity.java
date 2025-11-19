package com.example.projetoaula;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TemperaturaActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);

        Spinner spTemperaturaDe = findViewById(R.id.spTemperaturaDe);
        Spinner spTemperaturaPara = findViewById(R.id.spTemperaturaPara);
        Button BtnConverterTemperatura = findViewById(R.id.BtnConverterTemperatura);
        TextView txtResultadoTemperatura = findViewById(R.id.txtResultadoTemperatura);
        EditText txtTemperatura = findViewById(R.id.txtTemperatura);

        String[] escalas = {"Celsius", "Fahrenheit", "Kelvin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                escalas
        );

        //define o layout do spinner
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        //aplicar os valores ao spinner
        spTemperaturaDe.setAdapter(adapter);
        spTemperaturaPara.setAdapter(adapter);

        BtnConverterTemperatura.setOnClickListener(v -> {
            double resultado = 0;
            double temperatura = 0;

            String valorTemperatura = txtTemperatura.getText().toString();
            String de = spTemperaturaDe.getSelectedItem().toString();
            String para = spTemperaturaPara.getSelectedItem().toString();

            temperatura = Double.parseDouble(valorTemperatura);

            if (!valorTemperatura.isEmpty()) {
                resultado = converterTemperatura(temperatura, de, para);
            } else {
                Toast.makeText(TemperaturaActivity.this, "Digite uma Temperatura", Toast.LENGTH_SHORT).show();
            }
            txtResultadoTemperatura.setText(String.format("Resultado: %.2f %s",
                    resultado, para));
        });
    }

    private double converterTemperatura(double valor, String de, String para) {
        switch (de + " -> " + para) {
            case "Celsius -> Fahrenheit":
                return (valor * 9 / 5) + 32;
            case "Celsius -> Kelvin":
                return valor + 273.15;
            case "Fahrenheit -> Celsius":
                return (valor - 32) * 5 / 9;
            case "Fahrenheit -> Kelvin":
                return ((valor - 32) * 5 / 9) + 273.15;
            case "Kelvin -> Celsius":
                return valor - 273.15;
            case "Kelvin -> Fahrenheit":
                return ((valor - 273.15) * 5 / 9) + 32;
            default:
                return valor;
        }
    }


}


