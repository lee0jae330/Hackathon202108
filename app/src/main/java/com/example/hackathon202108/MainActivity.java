package com.example.hackathon202108;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnTabItemSelectedListener{

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            public boolean onNavigationItemSelected(MenuItem item){
                switch(item.getItemId()){
                    case R.id.tab1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment1).commit();
                        return true;
                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment2).commit();
                        return true;
                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment3).commit();
                        return true;
                    case R.id.tab4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment4).commit();
                        return true;
                }
                return false;
            }
        });
    }

    public void onTabSelected(int position){
        if(position==0){
            bottomNavigation.setSelectedItemId(R.id.tab1);

        } else if(position==1){
            bottomNavigation.setSelectedItemId(R.id.tab2);
        } else if(position==2){
            bottomNavigation.setSelectedItemId(R.id.tab3);
        } else if(position==3){
            bottomNavigation.setSelectedItemId(R.id.tab4);
        }
    }



}