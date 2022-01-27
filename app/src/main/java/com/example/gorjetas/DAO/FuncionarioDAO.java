package com.example.gorjetas.DAO;

import android.app.Dialog;
import android.util.Log;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.gorjetas.R;
import com.example.gorjetas.adapters.GarcomList;
import com.example.gorjetas.entities.Caixa;
import com.example.gorjetas.entities.Funcionario;
import com.example.gorjetas.entities.Gorjeta;
import com.example.gorjetas.views.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuncionarioDAO implements Dao<Funcionario>{


    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final GorjetaDAO gorjetaDao = new GorjetaDAO();
    public FuncionarioDAO(){
    }
    //retornar todos
    public void getAll(OnSuccessListener<List<Funcionario>> listener){

        db.collection("funcionarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Funcionario> lista = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> dados = document.getData();
                        Funcionario funcionario = new Funcionario();
                        funcionario.setIdFuncionario(document.getId());
                        funcionario.setNome((String) dados.get("nome"));
                        funcionario.setCpf((String) dados.get("cpf"));

                        lista.add(funcionario);
                    }
                    listener.onSuccess(lista);
                } else {
                    listener.onSuccess(new ArrayList<>());
                    //Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }
    //retornar um
    public void getOne(String id, OnSuccessListener<Funcionario> listener){
        db.collection("funcionarios").document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                                Funcionario func = new Funcionario();
                                    Map<String, Object> dados = document.getData();
                                    func.setIdFuncionario(document.getId());
                                    func.setNome((String) dados.get("nome"));
                                    func.setCpf((String) dados.get("cpf"));

                                    listener.onSuccess(func);
                    }
                });

    }
    //add
    public void create(Funcionario funcionario, OnSuccessListener<String> listener){

        db.collection("funcionarios").add(funcionario).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                listener.onSuccess(documentReference.getId());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    //editar
    public void update(String id, Funcionario updatedFuncionario, OnSuccessListener<String> listener){

        db.collection("funcionarios").document(id)
                .update(
                        "nome", updatedFuncionario.getNome(),
                        "cpf", updatedFuncionario.getCpf()
                ).addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onSuccess("Funcionario: " + id + "atualizado!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Log.w(TAG, "Error deleting document", e);
            }
        });
    }
    //remover
    public void delete(String id,OnSuccessListener<String> listener){

        gorjetaDao.getGorjetaFuncionario(id, new OnSuccessListener<Gorjeta>() {
            @Override
            public void onSuccess(Gorjeta gorjeta) {
                if(gorjeta == null){
                    db.collection("funcionarios").document(id)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    listener.onSuccess("Funcionario: " + id + "excluido com sucesso!");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                }else{
                    listener.onSuccess("erro");
                }
            }
        });

    }
}
