package com.example.abnormal.crimereport.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.adapter.RecyclerViewAdapter;
import com.example.abnormal.crimereport.model.Recycler;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "PostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.newPost);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NewPost();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
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

    private void NewPost() {

        final Dialog dialog = new Dialog(PostActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_new_post, null);
        dialog.setContentView(layout);

        final TextView simpan = (TextView) dialog.findViewById(R.id.simpanP);
        final TextView batal = (TextView) dialog.findViewById(R.id.batalP);

        dialog.show();

        batal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

    }
}
