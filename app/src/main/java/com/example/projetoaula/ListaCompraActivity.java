package com.example.projetoaula;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaCompraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listacompras);

        Button btnAdicionarItem = findViewById(R.id.btnAdcionarItem);
        ListView Lista = findViewById(R.id.lista);
        EditText txtProduto = findViewById(R.id.txtProduto);
        EditText txtQuantidade = findViewById(R.id.txtQuantidade);

        ArrayList<String> listar = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listar);

        Lista.setAdapter(adapter);

        btnAdicionarItem.setOnClickListener(v -> {
            String produto = txtProduto.getText().toString();
            String quantidade = txtQuantidade.getText().toString();

            if (produto.isEmpty() || quantidade.isEmpty()) {
                Toast.makeText(this, "Preencha produto e quantidade", Toast.LENGTH_SHORT).show();
                return;
            }

            int sequencia = listar.size() + 1;

            listar.add(sequencia + "  -  " + produto + " - " + quantidade);
            adapter.notifyDataSetChanged();

            txtProduto.setText("");
            txtQuantidade.setText("");
        });

        Lista.setOnItemClickListener((parent, view, position, id) -> {

            String itemSelecionado = listar.get(position);

            new AlertDialog.Builder(this)
                    .setTitle("Confirmar remoção")
                    .setMessage("Deseja remover o item:\n\n" + itemSelecionado + "?")
                    .setPositiveButton("Remover", (dialog, which) -> {

                        // Remove o item selecionado
                        listar.remove(position);

                        // ----- REORGANIZAR SEQUENCIAL -----

                        ArrayList<String> novaLista = new ArrayList<>();

                        for (int i = 0; i < listar.size(); i++) {

                            // Cada item tem formato: "X - Nome - Qtd"
                            String[] partes = listar.get(i).split(" - ");

                            if (partes.length >= 3) {
                                String nome = partes[1];
                                String quantidade = partes[2];

                                // Novo sequencial correto
                                novaLista.add((i + 1) + " - " + nome + " - " + quantidade);
                            }
                        }

                        // Substitui a lista antiga pela nova
                        listar.clear();
                        listar.addAll(novaLista);

                        // Atualiza a ListView
                        adapter.notifyDataSetChanged();

                        Toast.makeText(this, "Item removido", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }
}