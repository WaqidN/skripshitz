package com.example.abnormal.crimereport.activity.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.abnormal.crimereport.R;

public class ViewPost extends AppCompatActivity {

    TextView judul,isi,date;
    private String title,content,datepost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPost);
        setSupportActionBar(toolbar);

        judul = (TextView) findViewById(R.id.viewTitle);
        isi = (TextView) findViewById(R.id.viewIsi);
        date = (TextView) findViewById(R.id.viewDate);

        title = getIntent().getStringExtra("judulnya");
        content = getIntent().getStringExtra("isinya");
        datepost = getIntent().getStringExtra("date");

        judul.setText(title);
        isi.setText(content);
        date.setText(datepost);

    }
}
