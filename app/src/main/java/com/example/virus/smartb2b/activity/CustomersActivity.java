package com.example.virus.smartb2b.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.virus.smartb2b.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

public class CustomersActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FabSpeedDial fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        ButterKnife.bind(this);


        fab.addOnStateChangeListener(new FabSpeedDial.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean open) {
                // do something

            }
        });

        fab.addOnMenuItemClickListener(new FabSpeedDial.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionButton fab, TextView textView, int itemId) {

                switch (itemId){
                    case R.id.add_new:
                        startActivity(new Intent(CustomersActivity.this,AddNewCustomerActivity.class));
                        break;
                }

            }
        });

    }
}
