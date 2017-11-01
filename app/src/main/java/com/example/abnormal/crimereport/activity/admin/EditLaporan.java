package com.example.abnormal.crimereport.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

/**
 * Created by abnormal on 02/10/17.
 */

public class EditLaporan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText email1, web1,nohp1,title1,ket1;

    private String email,nohp,web,title,des,status;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_report);

        Toolbar toolbarEdit = (Toolbar)findViewById(R.id.toolbarProfil);
        setSupportActionBar(toolbarEdit);


        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        final List<String> categories = new ArrayList<String>();
        categories.add("masuk");
        categories.add("proses");
        categories.add("selesai");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        email1 = (EditText) findViewById(R.id.editr2);
        web1= (EditText) findViewById(R.id.editr3);
        nohp1 = (EditText) findViewById(R.id.editr4);
        title1 = (EditText) findViewById(R.id.editr5);
        ket1 = (EditText) findViewById(R.id.editr6);


        email = getIntent().getStringExtra("email");
        web = getIntent().getStringExtra("website");
        nohp = getIntent().getStringExtra("nohp");
        title = getIntent().getStringExtra("judul");
        des = getIntent().getStringExtra("ket");
        status = getIntent().getStringExtra("status");

        email1.setText(email);
        web1.setText(web);
        nohp1.setText(nohp);
        title1.setText(title);
        ket1.setText(des);
        if(status.equals("masuk")){
            spinner.setSelection(0);
        }else if(status.equals("proses")){
            spinner.setSelection(1);
        }else{
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_user1){
            setStatus((String) spinner.getSelectedItem(),getIntent().getStringExtra("idnya"));
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.icon_actoinbarlap, menu);
        return true;
    }
    public void setStatus(final String status, final String idp){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Url.HttpUrl + "/crimereport/laporan/editlaporan.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(EditLaporan.this, response, Toast.LENGTH_SHORT).show();
                Intent send = new Intent(EditLaporan.this, DrawerAdmin.class);
                startActivity(send);
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
}