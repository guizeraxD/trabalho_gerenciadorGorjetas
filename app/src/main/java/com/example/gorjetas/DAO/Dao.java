package com.example.gorjetas.DAO;

import androidx.annotation.NonNull;

import com.example.gorjetas.entities.Caixa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Dao<E>{

    public void getOne(String id, OnSuccessListener<E> listener);

    public void getAll(OnSuccessListener<List<E>> listener);

    public void create(E entity, OnSuccessListener<String> listener);

    public void update(String id, E entity, OnSuccessListener<String> listener);

    public void delete(String id, OnSuccessListener<String> listener);

}
