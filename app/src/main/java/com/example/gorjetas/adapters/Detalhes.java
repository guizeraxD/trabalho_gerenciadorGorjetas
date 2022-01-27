package com.example.gorjetas.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gorjetas.DAO.FuncionarioDAO;
import com.example.gorjetas.R;
import com.example.gorjetas.entities.Funcionario;
import com.example.gorjetas.entities.Gorjeta;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;
import java.util.List;

public class Detalhes extends RecyclerView.Adapter<Detalhes.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeFunc;
        public TextView valorGorjeta;

        public ViewHolder(View itemView) {
            super(itemView);
            nomeFunc = (TextView) itemView.findViewById(R.id.nomeFunc);
            valorGorjeta = (TextView) itemView.findViewById(R.id.valorGorjeta);

        }
    }

    private List<Gorjeta> gorjetas;
    private Activity activity;

    public Detalhes(List<Gorjeta> gorjetas, Activity activity){
        this.gorjetas = gorjetas;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View gorjetasView = inflater.inflate(R.layout.gorjeta_list_item, parent, false);
        Detalhes.ViewHolder viewHolder = new Detalhes.ViewHolder(gorjetasView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Gorjeta gorjeta = gorjetas.get(position);
        TextView textNome = holder.nomeFunc;
        TextView textValor = holder.valorGorjeta;
        if(gorjeta.getIdFuncionario() != null){
            FuncionarioDAO funcionarioDao = new FuncionarioDAO();
            funcionarioDao.getOne(gorjeta.getIdFuncionario(), new OnSuccessListener<Funcionario>() {
                @Override
                public void onSuccess(Funcionario funcionario) {
                    textNome.setText(funcionario.getNome());
                    DecimalFormat decimal = new DecimalFormat("###,###,###,##0.00");
                    String valor = decimal.format(gorjeta.getValor());
                    textValor.setText("R$: " + valor);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return gorjetas.size();
    }
}
