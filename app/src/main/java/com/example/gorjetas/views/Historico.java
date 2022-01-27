package com.example.gorjetas.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gorjetas.DAO.CaixaDAO;
import com.example.gorjetas.R;
import com.example.gorjetas.adapters.CaixasList;
import com.example.gorjetas.entities.Caixa;
import com.google.android.gms.tasks.OnSuccessListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class Historico extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        getHistorico();
    }

    public void getHistorico(){
        CaixaDAO caixaDao = new CaixaDAO();
        caixaDao.getAll(new OnSuccessListener<List<Caixa>>() {
            @Override
            public void onSuccess(List<Caixa> caixas) {
                setRecycle(caixas);
            }
        });
    }

    public void setRecycle(List<Caixa> caixas){
        RecyclerView rvHistorico = (RecyclerView) findViewById(R.id.recyclerCaixas);
        CaixasList caixasAdapter = new CaixasList(caixas, this);
        rvHistorico.setAdapter(caixasAdapter);
        rvHistorico.setLayoutManager(new LinearLayoutManager(this));
    }

    public void voltar(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}