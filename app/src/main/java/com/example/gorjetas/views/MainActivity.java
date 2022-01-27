package com.example.gorjetas.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gorjetas.DAO.CaixaDAO;
import com.example.gorjetas.DAO.FuncionarioDAO;
import com.example.gorjetas.DAO.GorjetaDAO;
import com.example.gorjetas.R;
import com.example.gorjetas.entities.Caixa;
import com.example.gorjetas.entities.Funcionario;
import com.example.gorjetas.entities.Gorjeta;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private double valorCaixa = 0;
    private static final CaixaDAO caixadao = new CaixaDAO();
    private static final FuncionarioDAO funcionarioDao = new FuncionarioDAO();
    private static final GorjetaDAO gorjetaDao = new GorjetaDAO();
    private Caixa caixaAtivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        abrecaixa();

    }

    public void abrecaixa(){
        caixadao.caixaAberto(new OnSuccessListener<Caixa>() {
            @Override
            public void onSuccess(Caixa caixa) {
                if (caixa == null) {
                    Intent intent = new Intent(getApplicationContext(), abrir_caixa.class);
                    startActivity(intent);
                } else {
                    caixaAtivo = caixa;
                    TextView textData = (TextView) findViewById(R.id.textViewData);
                    textData.setText(caixaAtivo.getData());
                    calculaTotal();
                }
            }
        });

    }

    public void calculaTotal(){

        ImageView img = (ImageView) findViewById(R.id.imageViewPote);
        gorjetaDao.getAllCaixaAtual(caixaAtivo.getIdCaixa(), new OnSuccessListener<List<Gorjeta>>() {
            @Override
            public void onSuccess(List<Gorjeta> gorjetas) {
                for(Gorjeta gorj : gorjetas){
                    valorCaixa += gorj.getValor();
                }
                if(valorCaixa == 0){
                    img.setImageResource(R.drawable.jarra00);
                }else if(valorCaixa >= 1 && valorCaixa < 50){
                    img.setImageResource(R.drawable.jarra11);
                }else if(valorCaixa >= 50 && valorCaixa < 150){
                    img.setImageResource(R.drawable.jarra22);
                }else
                    img.setImageResource(R.drawable.jarra33);

                exibeTotal(valorCaixa);
            }
        });
    }

    public void exibeTotal(Double valor){
        DecimalFormat decimal = new DecimalFormat("###,###,###,##0.00");
        TextView txtTotal = (TextView) findViewById(R.id.textTotal);
        txtTotal.setText("R$: " + decimal.format(valor));
        valorCaixa = 0;
        txtTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telaDetalhes(view);
            }
        });

    }

    public void telaDeposit(View view){

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_deposit);


        Spinner spinner = dialog.findViewById(R.id.spinner);
        funcionarioDao.getAll(new OnSuccessListener<List<Funcionario>>() {
            @Override
            public void onSuccess(List<Funcionario> funcionarios) {
                ArrayAdapter<Funcionario> adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.funcionario_list_item,funcionarios);
                spinner.setAdapter(adapter);
            }
        });

        ImageView imgVoltar = dialog.findViewById(R.id.imgVoltar);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        final TextInputEditText txtValor = dialog.findViewById(R.id.txtValor);
        Button btnDeposit = dialog.findViewById(R.id.btnCadastrar);
        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Funcionario funcGorjeta = (Funcionario) spinner.getSelectedItem();
                Gorjeta gorjeta = new Gorjeta();
                gorjeta.setIdFuncionario(funcGorjeta.getIdFuncionario());
                gorjeta.setIdCaixa(caixaAtivo.getIdCaixa());
                gorjeta.setValor(Double.parseDouble(txtValor.getText().toString()));
                gorjetaDao.create(gorjeta, new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                    }
                });
                calculaTotal();
                dialog.cancel();
            }
        });
        dialog.show();

    }

    public void telaGarcons(View view){
        Intent intent = new Intent(getApplicationContext(), Garcons.class);
        startActivity(intent);
    }

    public void telaHistorico(View view){
        Intent intent = new Intent(getApplicationContext(), Historico.class);
        startActivity(intent);
    }

    public void telaDetalhes(View view){
            Intent intent = new Intent(getApplicationContext(), DetalhesCaixa.class);
            String idCaixa = caixaAtivo.getIdCaixa();
            intent.putExtra("idCaixa", idCaixa);
            startActivity(intent);

    }

    public void telaConfirmacao(View view){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_confirmacao);

        ImageView imgVoltar = dialog.findViewById(R.id.imgVoltar2);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        Button btnConfirma = dialog.findViewById(R.id.btnExcluirSim);
        btnConfirma.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                caixadao.updateCaixa(caixaAtivo.getIdCaixa(), new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                    }
                });
                abrecaixa();
                dialog.cancel();
            }
        });
        dialog.show();
    }




}


