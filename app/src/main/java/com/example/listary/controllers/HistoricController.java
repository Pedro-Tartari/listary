package com.example.listary.controllers;

import androidx.annotation.NonNull;

import com.example.listary.adapters.HistoricViewAdapter;
import com.example.listary.interfaces.Callback;
import com.example.listary.model.Firestore;
import com.example.listary.model.ShoppingList;
import com.example.listary.model.ShoppingListDocument;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class HistoricController {

    private Firestore connection = new Firestore();
    private String listName;
    private CollectionReference docRef = connection.getDb().collection("data")
            .document(connection.getUserId()).collection("shoppingList");

    public void getDataFromDatabase(String documentId, Callback callback){

        DocumentReference idRef = connection.getDb()
                .collection("data")
                .document(connection.getUserId())
                .collection("shoppingList")
                .document(documentId);

        idRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ShoppingList shoppingDocument = document.toObject(ShoppingListDocument.class).shoppingList;
                        listName =  shoppingDocument.getName();
                        callback.onCallback(new HistoricViewAdapter(null, shoppingDocument.getProductList()));
                    }
                }
            }
        });
    }

    public String getListName() {
        return listName;
    }

    public CollectionReference getDocRef() {
        return docRef;
    }
}