package com.example.virus.smartb2b.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.virus.smartb2b.R;
import com.example.virus.smartb2b.fragment.AddEmployeeFragment;
import com.example.virus.smartb2b.fragment.EmployeeFragment;
import com.example.virus.smartb2b.fragment.NoticeFragment;

import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

public class OrderStatusActivity extends AppCompatActivity {

    FabSpeedDial fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        fab = (FabSpeedDial) findViewById(R.id.fab);

        fab.addOnStateChangeListener(new FabSpeedDial.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean open) {
                // do something

            }
        });

        fab.addOnMenuItemClickListener(new FabSpeedDial.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionButton fab, TextView textView, int itemId) {
                // do something

            }
        });
        BottomNavigationView bNavigation = findViewById(R.id.bottom_navigation_container);
        bNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;
                    switch (item.getItemId()) {
                        case R.id.employee:
                            fragment = new EmployeeFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.chat:
                            fragment = new AddEmployeeFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.notice:
                            fragment = new NoticeFragment();
                            loadFragment(fragment);
                            return true;
                    }
                    return false;
                }
            };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
