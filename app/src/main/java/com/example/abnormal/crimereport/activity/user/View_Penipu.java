package com.example.abnormal.crimereport.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.abnormal.crimereport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abnormal on 28/01/18.
 */

public class View_Penipu extends AppCompatActivity {

    private String webTitle, webHost, webLink,webStatus, webId;
    private TextView wjudul, whost, wlink, wstatus;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_view_penipuan);

        requestQueue = Volley.newRequestQueue(this);

        getSupportActionBar().setTitle("View Website");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wjudul = (TextView) findViewById(R.id.webT);
        whost = (TextView) findViewById(R.id.webH);
        whost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+webHost));
                startActivity(browserIntent);
            }
        });
        wlink = (TextView) findViewById(R.id.webL);
        wstatus = (TextView)findViewById(R.id.status);

        webId = getIntent().getStringExtra("id");
        webTitle = getIntent().getStringExtra("judul");
        webHost = getIntent().getStringExtra("host");
        webLink = getIntent().getStringExtra("full_link");
        webStatus = getIntent().getStringExtra("status");

        wjudul.setText(Html.fromHtml(webTitle).toString().replace("\n","").trim());
        whost.setText(Html.fromHtml(webHost).toString().replace("\n","").trim());
        wlink.setText(Html.fromHtml(webLink).toString().replace("\n","").trim());
        wstatus.setText("status : "+webStatus);

    }
}
