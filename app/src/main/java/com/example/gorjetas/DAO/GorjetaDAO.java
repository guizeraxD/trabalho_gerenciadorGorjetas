package com.example.gorjetas.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gorjetas.entities.Gorjeta;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GorjetaDAO implements Dao<Gorjeta> {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public GorjetaDAO(){
    };
    //retorna apenas uma gorjeta
    @Override
    public void getOne(String id, OnSuccessListener<Gorjeta> listener) {

        db.collection("gorjetas").document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        Gorjeta gorjeta = new Gorjeta();

                        Map<String, Object> dados = document.getData();
                        gorjeta.setIdGorjeta(document.getId());
                        gorjeta.setValor((Double) dados.get("valor"));
                        gorjeta.setIdCaixa((String) dados.get("idCaixa"));
                        gorjeta.setIdFuncionario((String) dados.get("idFuncionario"));

                        listener.onSuccess(gorjeta);
                    }
                });
    }

    //retorna todas gorjetas
    @Override
    public void getAll(OnSuccessListener<List<Gorjeta>> listener) {

        db.collection("gorjetas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Gorjeta> lista = new ArrayList<>();
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Map<String, Object> dados = document.getData();
                        Gorjeta gorjeta = new Gorjeta();
                        gorjeta.setIdGorjeta(document.getId());
                        gorjeta.setValor((Double) dados.get("valor"));
                        gorjeta.setIdCaixa((String) dados.get("idCaixa"));
                        gorjeta.setIdFuncionario((String) dados.get("idFuncionario"));

                        lista.add(gorjeta);
                    }
                    listener.onSuccess(lista);
                }else{
                    listener.onSuccess(new ArrayList<>());
                }
            }
        });
    }

    //busca se o funcionario possui alguma gorjeta recebida
    public void getGorjetaFuncionario(String idFuncionario, OnSuccessListener<Gorjeta> listener){
        db.collection("gorjetas").whereEqualTo("idFuncionario", idFuncionario)
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.getDocuments().size() == 0){
                            listener.onSuccess(null);
                            return;
                        }
                            Gorjeta gorjeta = new Gorjeta();
                            listener.onSuccess(gorjeta);
                    }
                });

    }

    //retorna todas gorjetas de um caixa
    public void getAllCaixaAtual(String caixaID, OnSuccessListener<List<Gorjeta>> listener){

        db.collection("gorjetas").whereEqualTo("idCaixa",caixaID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Gorjeta> lista = new ArrayList<>();
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Map<String, Object> dados = document.getData();
                        Gorjeta gorjeta = new Gorjeta();
                        gorjeta.setIdGorjeta(document.getId());
                        gorjeta.setValor((Double) dados.get("valor"));
                        gorjeta.setIdCaixa((String) dados.get("idCaixa"));
                        gorjeta.setIdFuncionario((String) dados.get("idFuncionario"));

                        lista.add(gorjeta);
                    }
                    listener.onSuccess(lista);
                }else{
                    listener.onSuccess(new ArrayList<>());
                }
            }
        });
    }
    //retorna total de gorjetas de um caixa
    public void getTotalCaixa(String caixaID, OnSuccessListener<Double> listener){

        db.collection("gorjetas").whereEqualTo("idCaixa",caixaID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    double valor = 0;
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Map<String, Object> dados = document.getData();
                        valor += (Double)dados.get("valor");
                    }
                    listener.onSuccess(valor);
                }else{
                    listener.onSuccess(null);
                }
            }
        });
    }
    //add gorjeta
    @Override
    public void create(Gorjeta gorjeta, OnSuccessListener<String> listener) {

        db.collection("gorjetas").add(gorjeta).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                listener.onSuccess(documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Log.w(TAG, "Error adding document", e);
            }
        });
    }
    //nao necessario no projeto
    @Override
    public void update(String id, Gorjeta entity, OnSuccessListener<String> listener) {
    }
    //nao necessario no projeto
    @Override
    public void delete(String id, OnSuccessListener<String> listener) {
    }

}
