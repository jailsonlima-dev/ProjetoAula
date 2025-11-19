package com.example.projetoaula;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsultarCepActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cep);

        EditText txtCep = findViewById(R.id.txtCep);
        TextView txtResultadoCep = findViewById(R.id.txtResultadoCep);
        Button btnConsultaCep = findViewById(R.id.btnConsultaCep);

        btnConsultaCep.setOnClickListener(v -> {
            String cep = txtCep.getText().toString();
            if (!cep.isEmpty()) {
                buscaEndereco(cep, txtResultadoCep);
            } else {
                txtResultadoCep.setText("Informe um CEP Válido");
            }
        });
    }

    private void buscaEndereco(String cep, TextView txtResultadoCep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json";
        new Thread(() -> {

            try {
                URL apiUrl = new URL(url);
                HttpURLConnection conexao = (HttpURLConnection) apiUrl.openConnection();
                conexao.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                StringBuilder resultado = new StringBuilder();
                String linha;

                while ((linha = reader.readLine()) != null) {
                    resultado.append(linha);

                }
                reader.close();

                JSONObject json = new JSONObject(resultado.toString());
                String rua = json.optString("logradouro", "");
                String bairro = json.optString("bairro", "");
                String cidade = json.optString("localidade", "");
                String UF = json.optString("uf", "");
                String EnderecoCompleto = rua + ", " + bairro + "\n" + cidade + "-" + UF;

                runOnUiThread(() -> txtResultadoCep.setText(EnderecoCompleto));


            } catch (Exception e) {
                runOnUiThread(() -> txtResultadoCep.setText("Erro ao buscar Endereço"));
            }


        }).start();

    }


}
