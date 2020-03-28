package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    TextView lblNome,lblCPF,lblEmail,lblTelefone,lblSenha,lblPreencha;
    Button btnUpdate,btnCancelar;
    EditText edtNome,edtCPF,edtEmail,edtTelefone,edtSenha;
    String cpfFormatado;
    Conexao conexao;
    private Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent i = getIntent();
        usuario = (Usuario) i.getSerializableExtra("usuario");

        startComponents();
        textShadows();
        buttonsClicks();
        conexao = new Conexao(this);


        edtNome.setText(usuario.getNome());
        edtCPF.setText(usuario.getCpf());
        edtEmail.setText(usuario.getEmail());
        edtTelefone.setText(usuario.getTelefone());
        edtSenha.setText(usuario.getSenha());
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarMudancas();
    }

    private void buttonsClicks() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarVazios();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
    }

    private void startComponents() {
        lblPreencha = findViewById(R.id.lblPreenchaUpdate);
        lblNome = findViewById(R.id.lblNomeUpdate);
        lblCPF = findViewById(R.id.lblCPFUpdate);
        lblEmail = findViewById(R.id.lblEmailUpdate);
        lblSenha = findViewById(R.id.lblSenhaUpdate);
        lblTelefone = findViewById(R.id.lblTelefoneUpdate);
        btnCancelar = findViewById(R.id.btnCancelarUpdate);
        btnUpdate = findViewById(R.id.btnAtualizarUsuario);
        edtNome = findViewById(R.id.edtNomeUpdate);
        edtCPF = findViewById(R.id.edtCPFUpdate);
        edtEmail = findViewById(R.id.edtEmailUpdate);
        edtTelefone = findViewById(R.id.edtTelefoneUpdate);
        edtSenha = findViewById(R.id.edtSenhaUpdate);
    }

    private void textShadows() {
        lblNome.setShadowLayer(30,2,2, Color.WHITE);
        lblTelefone.setShadowLayer(30,2,2, Color.WHITE);
        lblSenha.setShadowLayer(30,2,2, Color.WHITE);
        lblEmail.setShadowLayer(30,2,2, Color.WHITE);
        lblCPF.setShadowLayer(30,2,2, Color.WHITE);
        lblPreencha.setShadowLayer(30,2,2, Color.WHITE);
    }

    private void verificarVazios() {
        if(edtNome.getText().toString().trim().equals("")){
            edtNome.setBackground(getResources().getDrawable(R.drawable.error_input));
        }else if (edtCPF.getText().toString().trim().equals("")){
            edtCPF.setBackground(getResources().getDrawable(R.drawable.error_input));
        }else if (edtEmail.getText().toString().trim().equals("")){
            edtEmail.setBackground(getResources().getDrawable(R.drawable.error_input));
        }else if (edtTelefone.getText().toString().trim().equals("")){
            edtTelefone.setBackground(getResources().getDrawable(R.drawable.error_input));
        }
        else if (edtSenha.getText().toString().equals("")){
            edtSenha.setBackground(getResources().getDrawable(R.drawable.error_input));
        } else{
            atualizarUsuario();
        }
    }

    private void atualizarUsuario() {
        boolean isUpdate = conexao.atualizarDados(usuario.getId().toString(),edtNome.getText().toString(),edtCPF.getText().toString(),edtEmail.getText().toString(),edtTelefone.getText().toString(),edtSenha.getText().toString());
        if(isUpdate){
            Toast.makeText(this, "Dados atualizados", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,SignIn.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }else {
            Toast.makeText(this, "Dados não atualizados", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateEmailFormat(final String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    private void verificarMudancas() {
        edtNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtNome.getText().toString().trim().equals("")){
                    edtNome.setBackground(getResources().getDrawable(R.drawable.error_input));
                    btnUpdate.setEnabled(false);
                }
                else{
                    edtNome.setBackground(getResources().getDrawable(R.drawable.custom_input_lessround));
                    btnUpdate.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCPF.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!ValidarCPF.isCPF(edtCPF.getText().toString().trim())){
                    edtCPF.setBackground(getResources().getDrawable(R.drawable.error_input));
                }else{
                    edtCPF.setBackground(getResources().getDrawable(R.drawable.custom_input_lessround));
                    cpfFormatado = ValidarCPF.imprimeCPF(edtCPF.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtEmail.getText().toString().trim().equals("") || !validateEmailFormat(edtEmail.getText().toString().trim())){
                    edtEmail.setBackground(getResources().getDrawable(R.drawable.error_input));
                }
                else {
                    edtEmail.setBackground(getResources().getDrawable(R.drawable.custom_input_lessround));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtSenha.getText().toString().equals("") || edtSenha.length() < 6){
                    edtSenha.setBackground(getResources().getDrawable(R.drawable.error_input));
                }
                else {
                    edtSenha.setBackground(getResources().getDrawable(R.drawable.custom_input_lessround));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
