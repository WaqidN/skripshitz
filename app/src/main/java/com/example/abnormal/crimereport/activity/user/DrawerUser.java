package com.example.abnormal.crimereport.activity.user;

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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.activity.LoginActivity;
import com.example.abnormal.crimereport.activity.Bantuan;
import com.example.abnormal.crimereport.activity.TentangAplikasi;
import com.example.abnormal.crimereport.activity.admin.DrawerAdmin;
import com.example.abnormal.crimereport.pojo.Keluar;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

/**
 * Created by abnormal on 20/07/17.
 */

public class DrawerUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "Utama";
    private TextView txtName;
    private View navHeader;
    private SharedPreferences sharedPreferences;
    private ImageView imageView;


    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_drawer_activity);

        sharedPreferences = this.getSharedPreferences("akun", MODE_APPEND);

        toolbar = (Toolbar) findViewById(R.id.toolbarUser);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerUser);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation_viewUser);
        navigationView.setNavigationItemSelectedListener(this);

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        imageView = (ImageView) navHeader.findViewById(R.id.foto);

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
            Toast.makeText(DrawerUser.this, "error bro " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        showHome();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.A_nav6:
                logOut();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerUser);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragment instanceof PostUser){
                super.onBackPressed();
            } else {
                showHome();
            }

        }
    }

    private void showHome() {

        fragment = new PostUser();
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framelayoutUser, fragment);
            ft.commit();
        }

    }

    Fragment fragment = null;

    public boolean onNavigationItemSelected(MenuItem itemId) {

        //creating fragment object

        int id = itemId.getItemId();
        //initializing the fragment object which is selected
        switch (id) {
            case R.id.navigation1:
                fragment = new PostUser();
                break;
            case R.id.navigation2:
                fragment = new LaporanUser();
                break;
            case R.id.navigation7:
                fragment = new List_Penipu();
                break;
            case R.id.navigation3:
                fragment = new UserEditProfile();
                break;
            case R.id.navigation4:
                fragment = new Bantuan();
                break;
            case R.id.navigation5:
                fragment = new TentangAplikasi();
                break;
            case R.id.navigation6:
                logOut();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framelayoutUser, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerUser);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logOut() {
       /* SharedPreferences sharedPreferences = getSharedPreferences("akun", MODE_APPEND);
        sharedPreferences.edit().clear().commit();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();*/
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
                        DrawerUser.this.finish();
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
