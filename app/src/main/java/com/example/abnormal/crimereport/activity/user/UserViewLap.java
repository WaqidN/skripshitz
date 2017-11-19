package com.example.abnormal.crimereport.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserViewLap extends AppCompatActivity {


    EditText email1, web1,nohp1,title1,ket1,nama1,id1;
    TextView pict1;
    private RequestQueue requestQueue;

    private String email,nohp,web,title,des,status, nama, pict,idm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_view_lap);

        Toolbar toolbarEdit = (Toolbar)findViewById(R.id.toolbarProfil);
        setSupportActionBar(toolbarEdit);

        requestQueue = Volley.newRequestQueue(this);
        nama1 = (EditText)findViewById(R.id.editr1);
        email1 = (EditText) findViewById(R.id.editr2);
        web1= (EditText) findViewById(R.id.editr3);
        nohp1 = (EditText) findViewById(R.id.editr4);
        title1 = (EditText) findViewById(R.id.editr5);
        ket1 = (EditText) findViewById(R.id.editr6);
        pict1 = (TextView) findViewById(R.id.tmpilG);

        Bundle extras = getIntent().getExtras();
        idm = extras.getString("idnya");
        nama = extras.getString("namapelaku");
        email = extras.getString("email");
        web = extras.getString("website");
        nohp = extras.getString("nohp");
        title = extras.getString("title");
        des = extras.getString("des");
        pict = extras.getString("pict");

        nama1.setText(nama);
        email1.setText(email);
        web1.setText(web);
        nohp1.setText(nohp);
        title1.setText(title);
        ket1.setText(des);
        pict1.setText(pict);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_user1){
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Url.HttpUrl + "crimereport/laporan/user_editlap.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Intent send = new Intent(UserViewLap.this, DrawerUser.class);
                        startActivity(send);
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(UserViewLap.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(UserViewLap.this, response, Toast.LENGTH_SHORT).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UserViewLap.this, "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> stringMap = new HashMap<>();
                    stringMap.put("id",idm);
                    stringMap.put("edit_nama",nama1.getText().toString());
                    stringMap.put("edit_email",email1.getText().toString());
                    stringMap.put("edit_website",web1.getText().toString());
                    stringMap.put("edit_nohp",nohp1.getText().toString());
                    stringMap.put("edit_judul",title1.getText().toString());
                    stringMap.put("edit_ket",ket1.getText().toString());
                    return stringMap;
                }
            };
            requestQueue.add(stringRequest);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_send, menu);
        return true;
    }
}
