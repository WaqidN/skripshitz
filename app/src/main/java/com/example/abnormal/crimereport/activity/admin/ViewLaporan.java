package com.example.abnormal.crimereport.activity.admin;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.Url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewLaporan extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner1;
    TextView emailV, webV,nohpV,titleV,ketV, namaV,pictV;
    private String emailp,nohpp,webp,titlep,desp,statusp,namap,pictp;
    private static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_laporan);

        getSupportActionBar().setTitle("View Laporan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);
        final List<String> categories = new ArrayList<String>();
        //categories.add("masuk");
        categories.add("proses");
        categories.add("selesai");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

        namaV = (TextView) findViewById(R.id.view0);
        namaV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewLaporan.this, LacakLaporan.class);
                intent.putExtra("data", namap);
                startActivity(intent);
            }
        });

        emailV = (TextView) findViewById(R.id.view1);
        emailV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ViewLaporan.this, LacakLaporan.class);
                    intent.putExtra("data", emailp);
                    startActivity(intent);

            }
        });

        webV= (TextView) findViewById(R.id.view2);
        webV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewLaporan.this, LacakLaporan.class);
                intent.putExtra("data", webp);
                startActivity(intent);
            }
        });

        nohpV = (TextView) findViewById(R.id.view3);
        nohpV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewLaporan.this, LacakLaporan.class);
                intent.putExtra("data", nohpp);
                startActivity(intent);
            }
        });


        titleV = (TextView) findViewById(R.id.view5);
        ketV = (TextView) findViewById(R.id.view6);
        //pictV = (TextView) findViewById(R.id.tmpilG);

        namap = getIntent().getStringExtra("pelakunya");
        emailp = getIntent().getStringExtra("emailp");
        webp = getIntent().getStringExtra("websitep");
        nohpp = getIntent().getStringExtra("nohpp");
        titlep = getIntent().getStringExtra("judulp");
        desp = getIntent().getStringExtra("ketp");
        statusp = getIntent().getStringExtra("statusp");
        pictp = getIntent().getStringExtra("pictnya");

        namaV.setText(namap);
        emailV.setText(emailp);
        webV.setText(webp);
        nohpV.setText(nohpp);
        titleV.setText(titlep);
        ketV.setText(desp);
        //pictV.setText(pictp);
        if (statusp.equals("proses")){
            spinner1.setSelection(0);
        } else {
            spinner1.setSelection(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_send, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_user1){

            setStatus((String) spinner1.getSelectedItem(),getIntent().getStringExtra("id"));
        }else {
            super.onBackPressed();
        }
        return true;
    }

    public void setStatus(final String status, final String idp){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.HttpUrl + "laporan/edit_status_lap.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Intent send = new Intent(ViewLaporan.this, LapProsesActivity.class);
                startActivity(send);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("id",idp);
                stringMap.put("edit_status",status);
                return stringMap;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
