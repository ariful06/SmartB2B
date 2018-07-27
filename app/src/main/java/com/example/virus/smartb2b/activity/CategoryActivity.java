package com.example.virus.smartb2b.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.virus.smartb2b.R;
import com.example.virus.smartb2b.adapter.CategoryGridViewListAdapter;
import com.example.virus.smartb2b.adapter.ProductGridListViewAdapter;
import com.example.virus.smartb2b.dialog.AddNewCategory;
import com.example.virus.smartb2b.model.Category;
import com.example.virus.smartb2b.model.Products;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.gridview)
    GridView gridView;

    private CategoryGridViewListAdapter adapter;
    private List<Category> categoryList;

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        getSupportActionBar();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Category");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewCategory dialog = new AddNewCategory();
                dialog.show(getFragmentManager(), "Add New Category");
            }
        });

        getCategoryList();
        adapter = new CategoryGridViewListAdapter(this,R.layout.category_item,categoryList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(onItemClickListener);


    }

    public void getCategoryList() {

        categoryList = new ArrayList<Category>();
        db = FirebaseFirestore.getInstance();
        try {
            db.collection("Category").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Category category = d.toObject(Category.class);
                            categoryList.add(category);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }catch(Exception e){
            e.getMessage();
        }
    }


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Toast.makeText(CategoryActivity.this, categoryList.get(position).getCategory()+" is clicked", Toast.LENGTH_SHORT).show();
        }
    };
}
