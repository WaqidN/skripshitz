package com.example.abnormal.crimereport.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.adapter.MessagesAdapter;
import com.example.abnormal.crimereport.helper.DividerItemDecoration;
import com.example.abnormal.crimereport.model.Message;
import com.example.abnormal.crimereport.network.ApiClient;
import com.example.abnormal.crimereport.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerAdmin extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, MessagesAdapter.MessageAdapterListener {

    private Toolbar toolbaradmin;
    private NavigationView navigationViewAdmin;
    private DrawerLayout drawerLayoutAdmin;

    private List<Message> messages = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessagesAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerAdmin.ActionModeCallback actionModeCallback;
    private ActionMode actionMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_admin);

        toolbaradmin = (Toolbar) findViewById(R.id.toolbarAdmin);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new MessagesAdapter(this, messages, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        actionModeCallback = new DrawerAdmin.ActionModeCallback();


        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getInbox();
                    }
                }
        );

        navigationViewAdmin = (NavigationView) findViewById(R.id.navigation_viewAdmin);
        navigationViewAdmin.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayoutAdmin.closeDrawers();

                switch (menuItem.getItemId()) {

                    case R.id.A_nav2:
                        Intent login = new Intent(DrawerAdmin.this, DrawerAdmin.class);
                        startActivity(login);
                        return true;

                    case R.id.A_nav2_2:
                        Intent lapproses = new Intent(DrawerAdmin.this, LapPeosesActivity.class);
                        startActivity(lapproses);
                        return true;

                    case R.id.A_nav2_3:
                        Intent lapselesai = new Intent(DrawerAdmin.this, LapSelesaiActivity.class);
                        startActivity(lapselesai);
                        return true;

                    case R.id.A_nav3:
                        Intent post = new Intent(DrawerAdmin.this, PostActivity.class);
                        startActivity(post);
                        return true;

                    case R.id.A_nav5:
                        Intent profile = new Intent(DrawerAdmin.this, EditProfil.class);
                        startActivity(profile);
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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.newLap);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent newReport = new Intent(DrawerAdmin.this, NewReport.class);
                startActivity(newReport);
            }
        });

    }

    private void getInbox() {
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiService =
               ApiClient.getClient().create(ApiInterface.class);

        Call<List<Message>> call = apiService.getInbox();
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                messages.clear();


                for (Message message : response.body()) {

                    message.setColor(getRandomMaterialColor("400"));
                    messages.add(message);
                }

                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array", getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search...", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getInbox();
    }

    @Override
    public void onIconClicked(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }

        toggleSelection(position);
    }

    @Override
    public void onIconImportantClicked(int position) {

        Message message = messages.get(position);
        message.setImportant(!message.isImportant());
        messages.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMessageRowClicked(int position) {

        if (mAdapter.getSelectedItemCount() > 0) {
            enableActionMode(position);
        } else {

            Message message = messages.get(position);
            message.setRead(true);
            messages.set(position, message);
            mAdapter.notifyDataSetChanged();

            //Toast.makeText(getApplicationContext(), "Read: " + message.getMessage(), Toast.LENGTH_SHORT).show();
            Intent viewIntent = new Intent(DrawerAdmin.this, ViewLaporan.class);
            startActivity(viewIntent);
        }
    }

    @Override
    public void onRowLongClicked(int position) {

        enableActionMode(position);
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);


            swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:

                    deleteMessages();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelections();
            swipeRefreshLayout.setEnabled(true);
            actionMode = null;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.resetAnimationIndex();

                }
            });
        }
    }

    private void deleteMessages() {
        mAdapter.resetAnimationIndex();
        List<Integer> selectedItemPositions =
                mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.removeData(selectedItemPositions.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }



}
