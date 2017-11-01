package com.example.abnormal.crimereport.activity.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.abnormal.crimereport.R;

/**
 * Created by abnormal on 24/10/17.
 */

public class ViewLapSelesai extends AppCompatActivity {

    TextView email3, web3,nohp3,title3,ket3,status3;

    private String emails,nohps,webs,titles,dess,statuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_lapselesai);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarView);
        setSupportActionBar(toolbar);

        email3 = (TextView) findViewById(R.id.view1);
        web3= (TextView) findViewById(R.id.view2);
        nohp3 = (TextView) findViewById(R.id.view3);
        title3 = (TextView) findViewById(R.id.view5);
        ket3 = (TextView) findViewById(R.id.view6);
        status3 = (TextView) findViewById(R.id.liats);


        emails = getIntent().getStringExtra("emails");
        webs = getIntent().getStringExtra("websites");
        nohps = getIntent().getStringExtra("nohps");
        titles = getIntent().getStringExtra("juduls");
        dess = getIntent().getStringExtra("kets");
        statuss = getIntent().getStringExtra("statuss");

        email3.setText(emails);
        web3.setText(webs);
        nohp3.setText(nohps);
        title3.setText(titles);
        ket3.setText(dess);
        status3.setText("Laporan : "+statuss);
    }
}
