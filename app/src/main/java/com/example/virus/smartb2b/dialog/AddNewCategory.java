package com.example.virus.smartb2b.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.virus.smartb2b.R;
import com.example.virus.smartb2b.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddNewCategory extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "Add New Category";

    //widgets

    //vars
    Button add, cancel;
    EditText productCategory;

    static boolean isCategoryExist = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_category_dialog, container, false);
        add = view.findViewById(R.id.button_add);
        cancel = view.findViewById(R.id.button_cancel);

        //edittext
        productCategory = view.findViewById(R.id.product_category);

        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        getDialog().setTitle("Add New Category");
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.button_add:
                String category = productCategory.getText().toString().trim();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference dbProudct = db.collection("Category");
                if (!category.equals("")) {
                    Toast.makeText(getActivity(), checkCategory(category)+" ", Toast.LENGTH_SHORT).show();
//                  DocumentReference ref = db.collection("Category").document();

                    if (checkCategory(category)) {
                        Toast.makeText(getActivity(), "Category Exist", Toast.LENGTH_SHORT).show();
                    } else {

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("category", category);

                        dbProudct.add(map)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getActivity(), "success", Toast
                                                .LENGTH_SHORT).show();
                                        getDialog().dismiss();
                                        getActivity().recreate();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                                        e.getMessage();
                                        Toast.makeText(getActivity(), e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                                        getDialog().dismiss();
                                    }
                                });
                    }
                }
                break;

            case R.id.button_cancel:
                getDialog().dismiss();
                break;
        }
    }

    public boolean checkCategory(String category) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Query mQuery = db.collection("Category")
                .whereEqualTo("category", category);

        mQuery.addSnapshotListener(new EventListener<QuerySnapshot>(){
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                for (DocumentSnapshot ds: queryDocumentSnapshots){
                    if (ds!=null && ds.exists()){
                        isCategoryExist = true;
                    }
                }
            }
        });
        return isCategoryExist;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
