package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView lblRegistrar;
    Button btnEntrar;
    EditText txtEmail,txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startComponents();

        lblRegistrar.setPaintFlags(lblRegistrar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        lblRegistrar.setShadowLayer(30,2,2, Color.BLACK);

        buttonsClicks();
    }

    private void startComponents(){
        lblRegistrar = findViewById(R.id.lblRegistrar);
        txtEmail = findViewById(R.id.edtEmailLogin);
        txtSenha = findViewById(R.id.edtSenhaLogin);
        btnEntrar = findViewById(R.id.btnEntrar);
    }

    private void buttonsClicks(){
        lblRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SignIn.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

    }
}
