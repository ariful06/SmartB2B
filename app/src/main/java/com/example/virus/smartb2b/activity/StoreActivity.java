package com.example.virus.smartb2b.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;

import android.widget.Toast;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.virus.smartb2b.R;
import com.example.virus.smartb2b.activity.store.UpdateProductActivity;
import com.example.virus.smartb2b.adapter.ProductGridListViewAdapter;
import com.example.virus.smartb2b.dialog.AddNewProductDialog;
import com.example.virus.smartb2b.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.gridview)
    SwipeMenuListView listView;

    private ProductGridListViewAdapter adapter;
    private List<Products> productsList;

    private FirebaseFirestore db;
    Products product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);


        db = FirebaseFirestore.getInstance();
        getSupportActionBar();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Store");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewProductDialog dialog = new AddNewProductDialog();
                dialog.show(getFragmentManager(), "Add New Product");
            }
        });

        getProductList();
        adapter = new ProductGridListViewAdapter(this, R.layout.product_item, productsList);
        listView.setAdapter(adapter);


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item width
                openItem.setWidth(150);
                // set item title
//                openItem.setTitle("Edit");
                openItem.setBackground(R.drawable.ic_edit);
                // set item title fontsize
                openItem.setTitleSize(30);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
//                        0xFF, 0xFF)));
                deleteItem.setBackground(R.drawable.ic_delete);
                // set item width
                deleteItem.setWidth(150);
                // set a icon
//                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        product = productsList.get(position);
                        Intent intent = new Intent(StoreActivity.this, UpdateProductActivity.class);
                        intent.putExtra("product", (Serializable) product);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        // delete
                        AlertDialog.Builder builder = new AlertDialog.Builder(StoreActivity.this);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Are you sure you want to delete this item ?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                db.collection("Products").document(product.getId()).delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    recreate();
                                                    Toast.makeText(StoreActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                                dialog.dismiss();
                                Toast.makeText(StoreActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Do nothing
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                        break;
                }
                return false;
            }
        });


        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(StoreActivity.this, productsList.get(position).getProduct_name() + " should be deleted clicked", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void deleteProduct() {

    }


    public void getProductList() {

        productsList = new ArrayList<Products>();
        db = FirebaseFirestore.getInstance();
        try {
            db.collection("Products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            product = d.toObject(Products.class);
                            product.setId(d.getId());
                            productsList.add(product);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }


}
