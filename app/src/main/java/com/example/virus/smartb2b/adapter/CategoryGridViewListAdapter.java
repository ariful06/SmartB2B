package com.example.virus.smartb2b.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.virus.smartb2b.R;
import com.example.virus.smartb2b.model.Category;
import com.example.virus.smartb2b.model.Products;

import java.util.List;

public class CategoryGridViewListAdapter extends ArrayAdapter<Category> {


    private TextView productCategory;

    public CategoryGridViewListAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_item, parent,false);
        }
        Category category = getItem(position);

        productCategory = view.findViewById(R.id.category);
        productCategory.setText(category.getCategory());
        return view;
    }

}
