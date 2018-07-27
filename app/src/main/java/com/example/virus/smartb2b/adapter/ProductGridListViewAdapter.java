package com.example.virus.smartb2b.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.virus.smartb2b.R;
import com.example.virus.smartb2b.model.Products;

import java.util.List;

public class ProductGridListViewAdapter extends ArrayAdapter<Products> {


    private List<Products> productsList;
    private TextView productBrand, productName, productCategory, productPrice, productQuantity;

    public ProductGridListViewAdapter(Context context, int resource, List<Products> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.product_item, parent,false);
        }
         Products product = getItem(position);
        productBrand = view.findViewById(R.id.product_brand);
        productName = view.findViewById(R.id.product_name);
        productCategory = view.findViewById(R.id.product_category);
        productPrice = view.findViewById(R.id.product_price);
        productQuantity = view.findViewById(R.id.quantity);

        productBrand.setText(product.getBrand());
        productName.setText(product.getProduct_name());
        productCategory.setText(product.getCategory());
        productPrice.setText(String.valueOf(product.getPrice()));
        productQuantity.setText(String.valueOf(product.getQuantity()));
        return view;
    }


}
