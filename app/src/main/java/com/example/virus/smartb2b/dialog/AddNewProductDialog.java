package com.example.virus.smartb2b.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.virus.smartb2b.R;
import com.example.virus.smartb2b.activity.StoreActivity;
import com.example.virus.smartb2b.model.Products;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNewProductDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "Add New Product";

    //widgets

    //vars
    Button add, cancel;
    EditText productName, productCategory, productQuantity, productPrice,productBrand;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.add_new_product, container, false);

        add = view.findViewById(R.id.button_add);
        cancel = view.findViewById(R.id.button_cancel);
        //edittext
        productName = view.findViewById(R.id.product_name);
        productCategory = view.findViewById(R.id.product_category);
        productPrice = view.findViewById(R.id.product_price);
        productQuantity = view.findViewById(R.id.quantity);
        productBrand = view.findViewById(R.id.product_brand);

        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        getDialog().setTitle("Add New Product");
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.button_add:
                String name = productName.getText().toString().trim();
                String category = productCategory.getText().toString().trim();
                String brand = productBrand.getText().toString().trim();
                int price = Integer.parseInt(productPrice.getText().toString());
                int quantity = Integer.parseInt(productQuantity.getText().toString());
                if (!name.equals("") && !category.equals("")) {

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference dbProudct = db.collection("Products");

                    DocumentReference ref = db.collection("Products").document();


                    String productID = ref.getId();

                    Products product = new Products();

                    product.setProduct_name(name);
                    product.setCategory(category);
                    product.setBrand(brand);
                    product.setPrice(price);
                    product.setQuantity(quantity);

                    dbProudct.add(product)
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

                break;

            case R.id.button_cancel:
                getDialog().dismiss();
                break;
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
