package com.example.abnormal.crimereport.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.example.abnormal.crimereport.pojo.Session;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewPOst extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView title, content;
    private Spinner spinnerP;
    private String category, iduser;
    private RequestQueue requestQueue;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        requestQueue = Volley.newRequestQueue(this);

        getSupportActionBar().setTitle("New Berita");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView)findViewById(R.id.newp1);
        content = (TextView)findViewById(R.id.newp2);

        spinnerP = (Spinner) findViewById(R.id.spinnerPost);

        spinnerP.setOnItemSelectedListener(this);
        final List<String> categories = new ArrayList<String>();
        categories.add("uncategories");
        categories.add("Berita");
        categories.add("Tips");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerP.setAdapter(dataAdapter);

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(NewPOst.this, DrawerAdmin.class);
        startActivity(intent);

        super.onBackPressed();

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
        if(item.getItemId() == R.id.action_user1) {

            iduser = new Session(getApplicationContext()).getid();
            category = spinnerP.getSelectedItem().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Url.HttpUrl + "post/newpost.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject object = new JSONObject(response);
                        String hasil = object.getString("hasil");
                        if (hasil.equals("sukses")){
                            Toast.makeText(NewPOst.this, "sukses", Toast.LENGTH_SHORT).show();
                            Intent send = new Intent(NewPOst.this, DrawerAdmin.class);
                            startActivity(send);
                            finish();
                        }else{
                            Toast.makeText(NewPOst.this, object.getString("pesan"), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(NewPOst.this, "Pastikan kolom terisi semua dan pilih category", Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(NewPOst.this, "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> stringMap = new HashMap<>();
                    stringMap.put("p_author",iduser);
                    stringMap.put("p_title",title.getText().toString());
                    stringMap.put("p_content",content.getText().toString());
                    stringMap.put("p_category",category);
                    return stringMap;
                }
            };
        requestQueue.add(stringRequest);
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
