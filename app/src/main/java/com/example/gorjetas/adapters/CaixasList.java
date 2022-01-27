package com.example.gorjetas.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gorjetas.DAO.FuncionarioDAO;
import com.example.gorjetas.DAO.GorjetaDAO;
import com.example.gorjetas.R;
import com.example.gorjetas.entities.Caixa;
import com.example.gorjetas.views.DetalhesCaixa;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CaixasList extends RecyclerView.Adapter<CaixasList.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textoData;
        public TextView textoValor;
        public ImageView imgDetails;

        public ViewHolder(View itemView){
            super(itemView);
            textoData = (TextView) itemView.findViewById(R.id.funcData);
            textoValor = (TextView) itemView.findViewById(R.id.funcValor);
            imgDetails = (ImageView) itemView.findViewById(R.id.imgDetalhes);
        }
    }
    private List<Caixa> caixas;
    private Activity activity;

    public CaixasList(List<Caixa> caixas, Activity activity){
        Collections.sort(caixas, Collections.reverseOrder(new Comparator<Caixa>() {
            @Override
            public int compare(Caixa caixa, Caixa t1) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date data1 = new Date();
                Date data2 = new Date();
                try {
                    data1 = sdf.parse(caixa.getData());
                    data2 = sdf.parse(t1.getData());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return data1.compareTo(data2);
            }
        }));
        this.caixas = caixas;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View caixasView = inflater.inflate(R.layout.caixa_list_item, parent, false);
        CaixasList.ViewHolder viewHolder = new CaixasList.ViewHolder(caixasView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CaixasList.ViewHolder holder, int position) {

        GorjetaDAO gorjetaDao = new GorjetaDAO();
        Caixa caixa = caixas.get(position);
        ImageView imgDetalhes = holder.imgDetails;

        gorjetaDao.getTotalCaixa(caixa.getIdCaixa(), new OnSuccessListener<Double>() {
            @Override
            public void onSuccess(Double total) {
                TextView textoData = holder.textoData;
                textoData.setText(caixa.getData());

                DecimalFormat decimal = new DecimalFormat("###,###,###,##0.00");
                TextView textoValor = holder.textoValor;
                textoValor.setText("R$: " + decimal.format(total));

                imgDetalhes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        telaDetalhes(view, caixa.getIdCaixa());

                    };
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return caixas.size();
    }

    public void telaDetalhes(View view, String id){

            Intent intent = new Intent(activity, DetalhesCaixa.class);
            String idCaixa = id;
            intent.putExtra("idCaixa", idCaixa);
            activity.startActivity(intent);

    }

}
