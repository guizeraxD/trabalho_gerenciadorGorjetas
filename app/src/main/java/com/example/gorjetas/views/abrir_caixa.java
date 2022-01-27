package com.example.gorjetas.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gorjetas.DAO.CaixaDAO;
import com.example.gorjetas.R;
import com.example.gorjetas.entities.Caixa;
import com.example.gorjetas.views.Historico;
import com.example.gorjetas.views.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;

public class abrir_caixa extends AppCompatActivity {

    private static CaixaDAO caixadao = new CaixaDAO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_caixa);
    }

    public void novoCaixa(View view){
        final Caixa novoCaixa = new Caixa();
        novoCaixa.setSituacao("aberto");


        caixadao.create(novoCaixa, new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String id) {
            }
        });

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}