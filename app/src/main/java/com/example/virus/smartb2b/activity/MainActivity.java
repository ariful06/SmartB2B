package com.example.virus.smartb2b.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.virus.smartb2b.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.customers)
    Button customers;
    @BindView(R.id.employees)
    Button employees;
    @BindView(R.id.categories)
    Button categories;
    @BindView(R.id.store)
    Button store;
    @BindView(R.id.notice)
    Button notice;
    @BindView(R.id.orders)
    Button orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");

        customers.setOnClickListener(this);
        employees.setOnClickListener(this);
        categories.setOnClickListener(this);
        store.setOnClickListener(this);
        notice.setOnClickListener(this);
        orders.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.orders:
                startActivity(new Intent(MainActivity.this, OrderStatusActivity.class));
                break;
            case R.id.customers:
                startActivity(new Intent(MainActivity.this, CustomersActivity.class));
                break;
            case R.id.employees:
                startActivity(new Intent(MainActivity.this, EmployeeActivity.class));
                break;
            case R.id.store:
                startActivity(new Intent(MainActivity.this, StoreActivity.class));
                break;
            case R.id.notice:
                startActivity(new Intent(MainActivity.this, NoticeActivity.class));
                break;
            case R.id.categories:
                startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                break;

        }
    }
}
