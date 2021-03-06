package com.example.abnormal.crimereport.activity.admin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.activity.Bantuan;
import com.example.abnormal.crimereport.activity.LoginActivity;
import com.example.abnormal.crimereport.activity.TentangAplikasi;
import com.example.abnormal.crimereport.pojo.Keluar;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

public class DrawerAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbaradmin;
    private NavigationView navigationView;
    private DrawerLayout drawerLayoutAdmin;
    private TextView txtName;
    private View navHeader;
    private SharedPreferences sharedPreferences;
    private ImageView imageView, imageView1;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_admin);

        toolbaradmin = (Toolbar) findViewById(R.id.toolbarAdmin);
        setSupportActionBar(toolbaradmin);

        sharedPreferences = this.getSharedPreferences("akun", MODE_APPEND);

        drawerLayoutAdmin = (DrawerLayout) findViewById(R.id.drawerAdmin);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayoutAdmin, toolbaradmin, R.string.openDrawer, R.string.closeDrawer);
        drawerLayoutAdmin.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation_viewAdmin);
        navigationView.setNavigationItemSelectedListener(this);

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        imageView = (ImageView) navHeader.findViewById(R.id.foto);
        //imageView1 = (ImageView)findViewById(R.id.foto1);

        try {
            JSONObject jsonObject = new JSONObject(sharedPreferences.getString("data", null));
            txtName.setText(jsonObject.getString("user_username"));
            String status = jsonObject.getString("user_jk");

            if (status.equals("perempuan")){
                imageView.setImageResource(R.drawable.icon_prmpan);
            }else {
                imageView.setImageResource(R.drawable.icon_laki);
            }


        }catch (Exception e){
            Toast.makeText(DrawerAdmin.this, "error bro " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        showHome();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.A_nav9:
                logOut();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerAdmin);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragment instanceof LapMasuk){
                super.onBackPressed();
            } else {
                showHome();
            }

        }
    }

    private void showHome() {

        fragment = new LapMasuk();
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout, fragment);
            ft.commit();
        }

    }

    Fragment fragment = null;

    public boolean onNavigationItemSelected(MenuItem itemId) {

        //creating fragment object

        int id = itemId.getItemId();
        //initializing the fragment object which is selected
        switch (id) {
            case R.id.A_nav2:
                fragment = new LapMasuk();
                break;
            case R.id.A_nav2_2:
                fragment = new LapProsesActivity();
                break;
            case R.id.A_nav2_3:
                fragment = new LapSelesaiActivity();
                break;
            case R.id.A_nav3:
                fragment = new PostActivity();
                break;
            case R.id.A_nav5:
                fragment = new EditProfil();
                break;
            case R.id.A_nav6:
                Intent intent = new Intent(DrawerAdmin.this, LacakLaporan.class);
                startActivity(intent);
                //fragment = new LacakLaporan();
                break;
            case R.id.A_nav4:
                fragment = new DaftarWebSite();
                break;
            case R.id.A_nav7:
                fragment = new Bantuan();
                break;
            case R.id.A_nav8:
                fragment = new TentangAplikasi();
                break;
            case R.id.A_nav9:
                logOut();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerAdmin);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logOut() {
        final String token = FirebaseInstanceId.getInstance().getToken();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                Keluar keluar = new Keluar();
                return keluar.hapusToken(token);
            }

            protected  void onPostExecute(String s){
                super.onPostExecute(s);
                @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("akun", MODE_APPEND);
                sharedPreferences.edit().clear().commit();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }.execute();
    }

    private void keluarApp() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Keluar dari aplikasi ?");
        alertBuilder.setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DrawerAdmin.this.finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

}