package com.example.abnormal.crimereport.activity.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.activity.EditProfil;
import com.example.abnormal.crimereport.activity.LoginActivity;

public class DrawerAdmin extends AppCompatActivity  {

    private Toolbar toolbaradmin;
    private NavigationView navigationViewAdmin;
    private DrawerLayout drawerLayoutAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_admin);

        toolbaradmin = (Toolbar) findViewById(R.id.toolbarAdmin);
        setSupportActionBar(toolbaradmin);


         FragmentManager fragmentManager = getSupportFragmentManager();
         FragmentTransaction transaction     = fragmentManager.beginTransaction();
         transaction.replace(R.id.framelayout, new LapMasuk());
         transaction.commit();


        navigationViewAdmin = (NavigationView) findViewById(R.id.navigation_viewAdmin);
        navigationViewAdmin.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                FragmentManager     fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction     = fragmentManager.beginTransaction();

                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayoutAdmin.closeDrawers();

                switch (menuItem.getItemId()) {

                    case R.id.A_nav2:
                        transaction.replace(R.id.framelayout,new LapMasuk()).addToBackStack(null);
                        transaction.commit();
                        return true;

                    case R.id.A_nav2_2:
                        //getSupportActionBar().setTitle("Proses Laporan");
                        transaction.replace(R.id.framelayout,new LapProsesActivity()).addToBackStack(null);
                        transaction.commit();
                        return true;

                    case R.id.A_nav2_3:
                        //getSupportActionBar().setTitle("Laporan Selesai");
                        transaction.replace(R.id.framelayout,new LapSelesaiActivity()).addToBackStack(null);
                        transaction.commit();
                        return true;

                    case R.id.A_nav3:
                        transaction.replace(R.id.framelayout,new PostActivity()).addToBackStack(null);
                        transaction.commit();
                        return true;

                    case R.id.A_nav5:
                        //getSupportActionBar().setTitle("Edit Profile");
                        transaction.replace(R.id.framelayout,new EditProfil()).addToBackStack(null);
                        transaction.commit();
                        return true;

                    case R.id.A_nav6:
                        Intent lacaklap = new Intent(DrawerAdmin.this, LacakLaporan.class);
                        startActivity(lacaklap);
                        return true;

                    case R.id.A_nav7:
                        Toast.makeText(getApplicationContext(), "Pengaturan telah dipilih", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.A_nav8:
                        Toast.makeText(getApplicationContext(), "Bantuan telah dipilih", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.A_nav9:
                        SharedPreferences sharedPreferences = getSharedPreferences("akun",MODE_APPEND);
                        sharedPreferences.edit().clear().commit();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                        return true;

                    default:
                        return true;
                }
            }
        });

        drawerLayoutAdmin = (DrawerLayout) findViewById(R.id.drawerAdmin);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayoutAdmin, toolbaradmin, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayoutAdmin.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
}
