package com.example.abnormal.crimereport.activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.activity.EditProfil;
import com.example.abnormal.crimereport.activity.LoginActivity;

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
        setContentView(R.layout.user_drawer_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbarUser);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction     = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayoutUser,new PostUser());
        transaction.commit();


        navigationView = (NavigationView) findViewById(R.id.navigation_viewUser);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                FragmentManager     fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction     = fragmentManager.beginTransaction();

                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {

                    case R.id.navigation1:
                        transaction.replace(R.id.framelayoutUser, new PostUser()).addToBackStack(null);
                        return true;
                    case R.id.navigation2:
                        Intent laporan = new Intent(DrawerUser.this, LaporanUser.class);
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




}
