package com.example.crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
        registerForContextMenu(lista);
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

    public void deletar(MenuItem menuItem){
        final AdapterView.AdapterContextMenuInfo clique = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        final Usuario usuarioExcluir = usuariosFiltrados.get(clique.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja excluir este usuário?")
                .setNegativeButton("NÃO",null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usuariosFiltrados.remove(usuarioExcluir);
                        usuarios.remove(usuarioExcluir);
                        conexao.excluirUsuario(usuarioExcluir);
                        lista.invalidateViews();
                    }
                }).create();
        dialog.show();
    }
    public void atualizar(MenuItem item){
        final AdapterView.AdapterContextMenuInfo clique = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Usuario usuarioAtualizar = usuariosFiltrados.get(clique.position);

        Intent i = new Intent(this,UpdateActivity.class);
        i.putExtra("usuario",usuarioAtualizar);
        startActivity(i);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto,menu);
    }
}
