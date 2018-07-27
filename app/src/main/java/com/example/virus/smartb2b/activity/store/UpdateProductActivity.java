package com.example.virus.smartb2b.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.virus.smartb2b.R;
import com.example.virus.smartb2b.activity.StoreActivity;
import com.example.virus.smartb2b.model.Products;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateProductActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.update_button)
    Button update;
    @BindView(R.id.button_cancel)
    Button cancel;

    @BindView(R.id.product_name)
    EditText productName;
    @BindView(R.id.product_category)
    EditText productCategory;
    @BindView(R.id.quantity)
    EditText productQuantity;
    @BindView(R.id.product_price)
    EditText productPrice;
    @BindView(R.id.product_brand)
    EditText productBrand;

    FirebaseFirestore db;
    Products products;

    @BindView(R.id.toolbar)
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upadate_product);
        ButterKnife.bind(this);


        db = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        products = (Products) i.getSerializableExtra("product");

        getSupportActionBar();
        setSupportActionBar(toolbar);
        productName.setText(products.getProduct_name());
        productBrand.setText(products.getBrand());
        productCategory.setText(products.getCategory());
        productPrice.setText(String.valueOf(products.getPrice()));
        productQuantity.setText(String.valueOf(products.getQuantity()));


        update.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    public boolean hasValidationError(String brand, String name, String category, String price, String quantity) {
        return !name.equals("") && !brand.equals("") && !category.equals("") && !price.equals("") && !quantity.equals("");
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.update_button:
                updateProducts();
                break;
            case R.id.button_cancel:

                startActivity(new Intent(this,StoreActivity.class));
                finish();
                break;
        }
    }
    private void updateProducts() {
        String name = productName.getText().toString().trim();
        String brand = productBrand.getText().toString().trim();
        String category = productCategory.getText().toString().trim();
        String price = productPrice.getText().toString().trim();
        String quantity = productQuantity.getText().toString().trim();
        if (hasValidationError(brand,name,category,price,quantity)){
            Products updatedProduct = new Products(brand,name,category,Integer.parseInt(price),Integer.parseInt(quantity));
            db.collection("Products").document(products.getId())
            .set(updatedProduct)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startActivity(new Intent(UpdateProductActivity.this, StoreActivity.class));
                    Toast.makeText(UpdateProductActivity.this, "Product Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }
}
