package com.example.gorjetas.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gorjetas.entities.Caixa;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaixaDAO implements Dao<Caixa>{

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CaixaDAO(){
    }
    //retorna o caixa aberto
    public void caixaAberto( OnSuccessListener<Caixa> listener ){
        db.collection("caixas")
                .whereEqualTo("situacao", "aberto")
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot){
                        if (querySnapshot.getDocuments().size() == 0) {
                            listener.onSuccess(null);
                            return;
                        }

                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                        Caixa caixa = new Caixa();
                        Map<String, Object> dados = document.getData();

                        caixa.setIdCaixa(document.getId());
                        caixa.setSituacao((String) dados.get("situacao"));
                        caixa.setData((String) dados.get("data"));

                        listener.onSuccess(caixa);
                    }
                });
    }
    //retorna todos
    public void getAll(OnSuccessListener<List<Caixa>> listener) {
        db.collection("caixas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Caixa> lista = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> dados = document.getData();
                        Caixa caixa = new Caixa();
                        caixa.setIdCaixa(document.getId());
                        caixa.setSituacao((String) dados.get("situacao"));
                        caixa.setData((String) dados.get("data"));

                        lista.add(caixa);
                    }

                    listener.onSuccess(lista);
                } else {
                    listener.onSuccess(new ArrayList<>());
//                    Log.d("appgorjetas", "Error getting documents: ", task.getException());
                }
            }

        });
    }
    //retorna o caixa desejado
    public void getOne(String id, OnSuccessListener<Caixa> listener){
        db.collection("caixas").document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Caixa caixa = new Caixa();
                        Map<String, Object> dados = documentSnapshot.getData();
                        caixa.setIdCaixa(documentSnapshot.getId());
                        caixa.setSituacao((String) dados.get("situacao"));
                        caixa.setData((String) dados.get("data"));

                        listener.onSuccess(caixa);
                    }
                });
    }
    //add
    @Override
    public void create(Caixa caixa, OnSuccessListener<String> listener){

        db.collection("caixas").add(caixa).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                listener.onSuccess(documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    //edita a situacao para fechado
    public void updateCaixa(String id, OnSuccessListener<String> listener){
        db.collection("caixas").document(id)
                .update(
                        "situacao", "fechado"
                ).addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void aVoid) {
                listener.onSuccess("Caixa" + id + "fechado com sucesso!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Log.w(TAG, "Error deleting document", e);
            }
        });
    }
    //nao utilizado
    public void update(String id, Caixa caixa, OnSuccessListener<String> listener){
    }
    //nao necessario no projeto
     public void delete(String id, OnSuccessListener<String> listener){
    }



}
