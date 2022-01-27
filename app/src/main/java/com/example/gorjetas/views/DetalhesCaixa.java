package com.example.gorjetas.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gorjetas.DAO.CaixaDAO;
import com.example.gorjetas.DAO.GorjetaDAO;
import com.example.gorjetas.R;
import com.example.gorjetas.adapters.Detalhes;
import com.example.gorjetas.entities.Caixa;
import com.example.gorjetas.entities.Gorjeta;
import com.google.android.gms.tasks.OnSuccessListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class DetalhesCaixa extends AppCompatActivity {

    private static CaixaDAO caixaDAO = new CaixaDAO();
    private static GorjetaDAO gorjetaDao = new GorjetaDAO();
    private Caixa caixa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_caixa);
        getDados();
    }

    public void getDados(){
        Bundle dados = getIntent().getExtras();
        String idCaixa = dados.getString("idCaixa");
        caixaDAO.getOne(idCaixa, new OnSuccessListener<Caixa>() {
            @Override
            public void onSuccess(Caixa c) {
                caixa = c;
                TextView txtTitulo = (TextView) findViewById(R.id.txtTituloCaixa);
                txtTitulo.setText("Caixa - " + caixa.getData());
                gorjetaDao.getAllCaixaAtual(caixa.getIdCaixa(), new OnSuccessListener<List<Gorjeta>>() {
                    @Override
                    public void onSuccess(List<Gorjeta> gorjetas) {
                        setRecycle(gorjetas);
                    }
                });

                gorjetaDao.getTotalCaixa(idCaixa, new OnSuccessListener<Double>() {
                    @Override
                    public void onSuccess(Double total) {
                        DecimalFormat decimal = new DecimalFormat("###,###,###,##0.00");
                        TextView txtTotal = (TextView) findViewById(R.id.txtValorTotal);
                        txtTotal.setText("Total: R$ " + decimal.format(total));
                    }
                });

            }
        });
    }

    public void setRecycle(List<Gorjeta> gorjetas){
        RecyclerView rvDetalhes = (RecyclerView) findViewById(R.id.recyclerGorjetas);
        Detalhes gorjetaAdapter = new Detalhes(gorjetas, this);
        rvDetalhes.setAdapter(gorjetaAdapter);
        rvDetalhes.setLayoutManager(new LinearLayoutManager(this));
    }

    public void voltar(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}