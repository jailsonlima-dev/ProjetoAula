package com.example.projetoaula;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ApiActivity extends AppCompatActivity {
    ListView lstApiPython;
    EditText nomeapi, senhaapi,emailapi;
    Button btncadapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        lstApiPython= findViewById(R.id.lstApiPython);
        btncadapi = findViewById(R.id.btncadapi);
        nomeapi = findViewById(R.id.nomeapi);
        senhaapi = findViewById(R.id.senhaapi);
        emailapi = findViewById(R.id.emailapi);


        btncadapi.setOnClickListener(v -> {
            //buscaUsuarios(lstApiPython);
            String nome, email, senha;
            nome = nomeapi.getText().toString();
            email = emailapi.getText().toString();
            senha = senhaapi.getText().toString();
            if (!nome.isEmpty() || !email.isEmpty() || !senha.isEmpty()) {
                cadastrarUsuario(nome, email, senha);
                nomeapi.setText("");
                emailapi.setText("");
                senhaapi.setText("");

            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscaUsuarios(ListView listView) {
        String url = "http://10.0.2.2:5000/usuarios";

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

                // Converte para JSONObject
                JSONObject json = new JSONObject(resultado.toString());

                // Pega o array "usuarios"
                JSONArray usuariosArray = json.getJSONArray("usuarios");

                // Armazena nomes/emails
                ArrayList<String> listaUsuarios = new ArrayList<>();

                for (int i = 0; i < usuariosArray.length(); i++) {
                    JSONObject usuario = usuariosArray.getJSONObject(i);
                    String nome = usuario.optString("nome", "");
                    String email = usuario.optString("email", "");
                    listaUsuarios.add(nome + " - " + email);
                }


                runOnUiThread(() -> {
                    // Exibe  quantidade de itens do json
                    //txtResultado.setText("Total de usuários: " + usuariosArray.length());

                    // EXIBE DADOS
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_list_item_1,
                            listaUsuarios
                    ) {

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            if (position % 2 == 0) {
                                view.setBackgroundColor(Color.parseColor("#F0F0F0")); // cinza claro
                            } else {
                                view.setBackgroundColor(Color.parseColor("#FFFFFF")); // branco
                            }

                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                });

            } catch (Exception e) {
                //runOnUiThread(() -> txtResultado.setText("Erro ao Buscar Usuários"));
            }
        }).start();
    }

    private void cadastrarUsuario(String nome, String email, String senha) {
        String url = "http://10.0.2.2:5000/cadastro ";

        new Thread(() -> {
            try {
                URL apiUrl = new URL(url);
                HttpURLConnection conexao = (HttpURLConnection) apiUrl.openConnection();
                conexao.setRequestMethod("POST");
                conexao.setDoOutput(true); // permite enviar dados
                conexao.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // Monta os parâmetros no formato esperado pelo Flask
                String parametros = "nome=" + URLEncoder.encode(nome, "UTF-8") +
                        "&email=" + URLEncoder.encode(email, "UTF-8") +
                        "&senha=" + URLEncoder.encode(senha, "UTF-8");

                // ENVIA OS DADOS
                OutputStream os = conexao.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(parametros);
                writer.flush();
                writer.close();
                os.close();

                // RESPOSTA API
                int responseCode = conexao.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                    StringBuilder resposta = new StringBuilder();
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        resposta.append(linha);
                    }
                    reader.close();


                    runOnUiThread(() -> {
                        buscaUsuarios(lstApiPython);
                        Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    });
                } else {

                    runOnUiThread(() -> {
                        Toast.makeText(this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                    });
                }

            } catch (Exception e) {

                runOnUiThread(() -> {
                    Toast.makeText(this, "Erro na requisição", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
}