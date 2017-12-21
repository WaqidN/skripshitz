package com.example.abnormal.crimereport.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class EditPost extends AppCompatActivity {

    private String judulpost,contentpost, idnya;
    private EditText iniJudul, iniContent;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        getSupportActionBar().setTitle("Edit Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(this);

        iniJudul = (EditText) findViewById(R.id.editp1);
        iniContent = (EditText)findViewById(R.id.editp2);

        Bundle extras = getIntent().getExtras();
        idnya = extras.getString("iniid");
        judulpost = extras.getString("titlenya");
        contentpost= extras.getString("contentnya");

        iniJudul.setText(judulpost);
        iniContent.setText(Html.fromHtml(contentpost).toString().replace("\n","").trim());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_user1){
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Url.HttpUrl + "post/edit_post.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Intent send = new Intent(EditPost.this,ViewPost.class);
                    startActivity(send);
                    finish();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EditPost.this, "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> stringMap = new HashMap<>();
                    stringMap.put("id",idnya);
                    stringMap.put("edit_title",iniJudul.getText().toString());
                    stringMap.put("edit_content",iniContent.getText().toString());
                    return stringMap;
                }
            };
            requestQueue.add(stringRequest);
        }else {
            super.onBackPressed();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_send, menu);
        return true;
    }
}
