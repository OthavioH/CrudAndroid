package com.example.crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    TextView lblNome,lblCPF,lblEmail,lblTelefone,lblSenha,lblDDD,lblConfirmarSenha,lblPreencha;
    Button btnRegistrar,btnCancelar;
    EditText edtNome,edtCPF,edtEmail,edtTelefone,edtSenha,edtDDD,edtConfirmarSenha;
    AlertDialog alerta;
    String cpfFormatado;
    Conexao conexao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        startComponents();
        textShadows();
        buttonsClicks();



        conexao = new Conexao(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarMudancas();
    }


    private void startComponents(){
        lblPreencha = findViewById(R.id.lblPreencha);
        lblNome = findViewById(R.id.lblNome);
        lblCPF = findViewById(R.id.lblCPF);
        lblEmail = findViewById(R.id.lblEmail);
        lblSenha = findViewById(R.id.lblSenha);
        lblConfirmarSenha = findViewById(R.id.lblConfirmarSenha);
        lblDDD = findViewById(R.id.lblDDD);
        lblTelefone = findViewById(R.id.lblTelefone);
        btnCancelar = findViewById(R.id.btnCancelarCadastro);
        btnRegistrar = findViewById(R.id.btnRegistrarCadastro);
        edtNome = findViewById(R.id.edtNome);
        edtCPF = findViewById(R.id.edtCPF);
        edtEmail = findViewById(R.id.edtEmail);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtDDD = findViewById(R.id.edtDDD);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfirmarSenha = findViewById(R.id.edtConfirmarSenha);
    }

    private void buttonsClicks(){
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
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

    private void verificarVazios() {
        if(edtNome.getText().toString().trim().equals("")){
            edtNome.setBackground(getResources().getDrawable(R.drawable.error_input));
        }else if (edtCPF.getText().toString().trim().equals("")){
            edtCPF.setBackground(getResources().getDrawable(R.drawable.error_input));
        }else if (edtEmail.getText().toString().trim().equals("")){
            edtEmail.setBackground(getResources().getDrawable(R.drawable.error_input));
        }else if (edtDDD.getText().toString().trim().equals("")){
            edtDDD.setBackground(getResources().getDrawable(R.drawable.error_input));
        }else if (edtTelefone.getText().toString().trim().equals("")){
            edtTelefone.setBackground(getResources().getDrawable(R.drawable.error_input));
        }
        else if (edtSenha.getText().toString().equals("")){
            edtSenha.setBackground(getResources().getDrawable(R.drawable.error_input));
        }
        else if (edtConfirmarSenha.getText().toString().equals("")){
            edtConfirmarSenha.setBackground(getResources().getDrawable(R.drawable.error_input));
        }else{
            cadastrarUsuario();
        }
    }

    private void cadastrarUsuario() {

        boolean isInserted = conexao.inserirUsuario(edtNome.getText().toString(),edtCPF.getText().toString(),edtEmail.getText().toString(),"("+edtDDD.getText().toString()+") " + edtTelefone.getText().toString(),edtSenha.getText().toString());
        if(isInserted){
            Toast.makeText(this, "Dados Inseridos com sucesso", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,SignIn.class);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            startActivity(i);
        }
        else{
            Toast.makeText(this, "Falha ao inserir os dados", Toast.LENGTH_SHORT).show();
        }
    }

    private void textShadows() {
        lblConfirmarSenha.setShadowLayer(30,2,2, Color.BLACK);
        lblNome.setShadowLayer(30,2,2, Color.BLACK);
        lblTelefone.setShadowLayer(30,2,2, Color.BLACK);
        lblDDD.setShadowLayer(30,2,2, Color.BLACK);
        lblSenha.setShadowLayer(30,2,2, Color.BLACK);
        lblEmail.setShadowLayer(30,2,2, Color.BLACK);
        lblCPF.setShadowLayer(30,2,2, Color.BLACK);
        lblPreencha.setShadowLayer(30,2,2, Color.BLACK);
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
                    btnRegistrar.setEnabled(false);
                }
                else{
                    edtNome.setBackground(getResources().getDrawable(R.drawable.custom_input_lessround));
                    btnRegistrar.setEnabled(true);
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
        edtConfirmarSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtConfirmarSenha.getText().toString().equals("") || !edtConfirmarSenha.getText().toString().equals(edtSenha.getText().toString())){
                    edtConfirmarSenha.setBackground(getResources().getDrawable(R.drawable.error_input));
                }
                else {
                    edtConfirmarSenha.setBackground(getResources().getDrawable(R.drawable.custom_input_lessround));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



}