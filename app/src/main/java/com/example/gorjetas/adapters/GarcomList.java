package com.example.gorjetas.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.example.gorjetas.R;
import com.example.gorjetas.entities.Funcionario;
import com.example.gorjetas.views.Garcons;
import com.example.gorjetas.views.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class GarcomList extends RecyclerView.Adapter<GarcomList.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textoNome;
        public TextView textoCPF;
        public ImageView imgEdit;
        public ImageView imgRemove;

        public ViewHolder(View itemView){
            super(itemView);
            textoNome = (TextView) itemView.findViewById(R.id.funcNome);
            textoCPF = (TextView) itemView.findViewById(R.id.funcCPF);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            imgRemove = (ImageView) itemView.findViewById(R.id.imgRemove);
        }
    }
    private List<Funcionario> funcionarios;
    private Activity activity;

    public GarcomList(List<Funcionario> funcs, Activity activity){
        funcionarios = funcs;
        this.activity = activity;
    }



    @NonNull
    @Override
    public GarcomList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View funcionarioView = inflater.inflate(R.layout.garcom_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(funcionarioView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GarcomList.ViewHolder holder, int position) {
        Funcionario func = funcionarios.get(position);

        TextView textViewNome = holder.textoNome;
        textViewNome.setText(func.getNome());
        TextView textViewCPF = holder.textoCPF;
        textViewCPF.setText(func.getCpf());

        ImageView imgEdit = holder.imgEdit;
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                telaEdit(func.getIdFuncionario(), func.getNome(), func.getCpf());
                };
        });
        ImageView imgRemove = holder.imgRemove;
        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telaRemove(func.getIdFuncionario());
            }
        });
    }

    @Override
    public int getItemCount() {
        return funcionarios.size();
    }

    public void telaEdit(String id, String nome, String cpf){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_cadastro_garcom);

        final TextInputEditText txtNome = dialog.findViewById(R.id.txtNome);
        txtNome.setText(nome);
        final TextInputEditText txtCPF = dialog.findViewById(R.id.txtCPF);
        txtCPF.setText(cpf);
        Button btnCadastrar = dialog.findViewById(R.id.btnCadastrar);
        btnCadastrar.setText("Editar Funcionario");

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                FuncionarioDAO funcionarioDao = new FuncionarioDAO();
                Funcionario editFunc = new Funcionario();

                editFunc.setNome(txtNome.getText().toString());
                editFunc.setCpf(txtCPF.getText().toString());

                funcionarioDao.update(id, editFunc, new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        activity.recreate();
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

    public void telaRemove(String id){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.func_excluido);

        final Button btnSim = dialog.findViewById(R.id.btnExcluirSim);
        final Button btnNao = dialog.findViewById(R.id.btnExcluirNao);

        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FuncionarioDAO funcDao = new FuncionarioDAO();
                funcDao.delete(id, new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if(s == "erro"){
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setMessage("O funcionario já possui gorjetas e não pode ser excluido");
                            builder.setPositiveButton("Ok", null);
                            builder.show();

                            dialog.cancel();

                        }else{
                            dialog.cancel();
                            activity.recreate();
                        }

                    }

                });
            }
        });

        dialog.show();
    }

}
