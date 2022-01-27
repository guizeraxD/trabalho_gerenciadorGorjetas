package com.example.gorjetas.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gorjetas.DAO.FuncionarioDAO;
import com.example.gorjetas.R;
import com.example.gorjetas.adapters.GarcomList;
import com.example.gorjetas.entities.Funcionario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Garcons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garcons);
        getFuncionarios();
    }

    public void getFuncionarios(){
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.getAll(new OnSuccessListener<List<Funcionario>>() {
            @Override
            public void onSuccess(List<Funcionario> funcionarios) {
                setRecycle(funcionarios);
            }
        });
    }
    public void setRecycle(List<Funcionario> funcionarios){

        RecyclerView rvFuncionarios = (RecyclerView) findViewById(R.id.recycleFunc);
        GarcomList garcomAdapter = new GarcomList(funcionarios, this);
        rvFuncionarios.setAdapter(garcomAdapter);
        rvFuncionarios.setLayoutManager(new LinearLayoutManager(this));
    }

    public void telaCadastroGarcom(View view){

        final Dialog dialog = new Dialog(Garcons.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_cadastro_garcom);

        final TextInputEditText txtNome = dialog.findViewById(R.id.txtNome);
        final TextInputEditText txtCPF = dialog.findViewById(R.id.txtCPF);
        Button btnCadastrar = dialog.findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                FuncionarioDAO funcionarioDao = new FuncionarioDAO();
                Funcionario func = new Funcionario();

                func.setNome(txtNome.getText().toString());
                func.setCpf(txtCPF.getText().toString());

                funcionarioDao.create(func, new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        getFuncionarios();
                    }
                });
                dialog.cancel();
            }
        });

        ImageView imgVoltar = dialog.findViewById(R.id.imgVoltar4);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();

    }

    public void voltar(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


}