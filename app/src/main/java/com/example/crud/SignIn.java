package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {

    private ListView lista;
    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados = new ArrayList<>();
    private Conexao conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setTitle("Usuários");

        lista = findViewById(R.id.listview);
        conexao = new Conexao(this);
        usuarios = conexao.obterTodos();
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this,android.R.layout.simple_list_item_1,usuariosFiltrados);
        usuariosFiltrados.addAll(usuarios);
        lista.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar,menu);
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_consulta).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String textoDigitado) {
                procurarUsuario(textoDigitado);
                return false;
            }
        });

        return true;
    }

    public void procurarUsuario(String textoDigitado){
        usuariosFiltrados.clear();
        for(Usuario a: usuarios){
            if(a.getNome().toLowerCase().contains(textoDigitado.toLowerCase())){
                usuariosFiltrados.add(a);
            }else if(a.getEmail().toLowerCase().contains(textoDigitado.toLowerCase())) {
                usuariosFiltrados.add(a);
            }else if(a.getTelefone().toLowerCase().contains(textoDigitado.toLowerCase())){
                usuariosFiltrados.add(a);
            }
        }
        lista.invalidateViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnlogout:
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
                startActivity(i);
                Toast.makeText(this, "oi", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
