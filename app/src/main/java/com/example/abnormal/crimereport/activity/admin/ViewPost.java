package com.example.abnormal.crimereport.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.abnormal.crimereport.R;

public class ViewPost extends AppCompatActivity {

    TextView judul,isi,date;
    private String title,content,datepost,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        getSupportActionBar().setTitle("View Berita");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        judul = (TextView) findViewById(R.id.viewTitle);
        isi = (TextView) findViewById(R.id.viewIsi);
        date = (TextView) findViewById(R.id.viewDate);

        id = getIntent().getStringExtra("idnya");
        title = getIntent().getStringExtra("judulnya");
        content = getIntent().getStringExtra("isinya");
        datepost = getIntent().getStringExtra("date");

        judul.setText(title);
        isi.setText(Html.fromHtml(content).toString().replace("\n","").trim());
        date.setText(datepost);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_edit){

            Intent intent = new Intent(ViewPost.this, EditPost.class);
            intent.putExtra("iniid", id);
            intent.putExtra("titlenya", title);
            intent.putExtra("contentnya", content);
            startActivity(intent);
        }else {
            super.onBackPressed();
        }
        return true;
    }
}
