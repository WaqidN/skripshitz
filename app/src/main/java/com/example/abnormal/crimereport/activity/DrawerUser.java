package com.example.abnormal.crimereport.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.adapter.RecyclerViewAdapter;
import com.example.abnormal.crimereport.model.Recycler;

import java.util.ArrayList;

/**
 * Created by abnormal on 20/07/17.
 */

public class DrawerUser extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "Utama";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_user);

        toolbar = (Toolbar) findViewById(R.id.toolbarUser);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.newLap);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newReport = new Intent(DrawerUser.this,NewReport.class);
                startActivity(newReport);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.view_postUser);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        navigationView = (NavigationView) findViewById(R.id.navigation_viewUser);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {

                    case R.id.navigation1:
                        Intent brnda = new Intent(DrawerUser.this, DrawerUser.class );
                        startActivity(brnda);
                        return true;
                    case R.id.navigation2:
                        Intent laporan = new Intent(DrawerUser.this, DrawerUser.class);
                        startActivity(laporan);
                        return true;
                    case R.id.navigation3:
                        Intent editp = new Intent(DrawerUser.this, EditProfil.class);
                        startActivity(editp);
                        return true;
                    default:
                        SharedPreferences sharedPreferences = getSharedPreferences("akun",MODE_APPEND);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                        return true;
                }
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerUser);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((RecyclerViewAdapter) mAdapter).setOnItemClickListener(new RecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<Recycler> getDataSet() {
        ArrayList results = new ArrayList<Recycler>();
        for (int index = 0; index < 20; index++) {
            Recycler obj = new Recycler("Some Primary Text " + index,
                    "Secondary " + index);
            results.add(index, obj);
        }
        return results;
    }


}
