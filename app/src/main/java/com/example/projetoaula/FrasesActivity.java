package com.example.projetoaula;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class FrasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frases);

        TextView txtFrase = findViewById(R.id.txtFrase);
        ImageView imgFrase = findViewById(R.id.imgFrase);
        Button btnFrase = findViewById(R.id.btnFrase);

        int [] imagens={
                R.drawable.img01,
                R.drawable.img02,
                R.drawable.img03,
                R.drawable.img04,
                R.drawable.img05,
                R.drawable.img06
        };

        String [] frase = {
          "Acredite em você!",
          "Nunca Desista!",
          "Você é fera!",
          "Continue Tentando!",
          "Você que é o cara!",
          "Você é capaz!"
        };
        txtFrase.setText(frase[1]);
        imgFrase.setImageResource(imagens[1]);

        btnFrase.setOnClickListener(v -> {
            int numero = new Random().nextInt(frase.length);
            txtFrase.setText(frase[numero]);
            imgFrase.setImageResource(imagens[numero]);
        });
    }
}
