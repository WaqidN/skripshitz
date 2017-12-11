package com.example.abnormal.crimereport.activity.admin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

public class ViewWeb extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String webTitle, webHost, webLink,webStatus, webId;
    private TextView wjudul, whost, wlink;
    Spinner spinner;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_web);

        requestQueue = Volley.newRequestQueue(this);

        getSupportActionBar().setTitle("Daftar Website");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wjudul = (TextView) findViewById(R.id.webT);
        whost = (TextView) findViewById(R.id.webH);
        whost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(webHost)));
            }
        });
        wlink = (TextView) findViewById(R.id.webL);
        spinner = (Spinner) findViewById(R.id.spinnerWeb);

        spinner.setOnItemSelectedListener(this);
        final List<String> categories = new ArrayList<String>();
        categories.add("disimpan");
        categories.add("diperiksa");
        categories.add("penipu");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        webId = getIntent().getStringExtra("id");
        webTitle = getIntent().getStringExtra("judul");
        webHost = getIntent().getStringExtra("host");
        webLink = getIntent().getStringExtra("full_link");
        webStatus = getIntent().getStringExtra("status");

        wjudul.setText(webTitle);
        whost.setText(webHost);
        wlink.setText(webLink);

        if (webStatus.equals("disimpan")){
            spinner.setSelection(0);
        }else if (webStatus.equals("diperiksa")){
            spinner.setSelection(1);
        }else {
            spinner.setSelection(2);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_send, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_user1){

            webStatus = spinner.getSelectedItem().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.HttpUrl + "/crimereport/website/edit_situs.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(ViewWeb.this, "Sukses", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> stringMap = new HashMap<>();
                    stringMap.put("id",webId);
                    stringMap.put("i_status",webStatus);
                    return stringMap;
                }
            };
            requestQueue.add(stringRequest);
        }else {
            super.onBackPressed();
        }
        return true;
    }
}
