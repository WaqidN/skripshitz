package com.example.abnormal.crimereport.activity.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abnormal on 19/12/17.
 */

public class ViewLacak extends AppCompatActivity {
    private TextView title, host, link, dest;
    private Button saveLink;
    private String webTitle, webHost, webLink,webHost1, webId;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_lacak);

        requestQueue = Volley.newRequestQueue(this);

        getSupportActionBar().setTitle("View Hasil Lacak");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView)findViewById(R.id.titleWeb);
        host = (TextView) findViewById(R.id.hostWeb);
        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+webHost1));
                startActivity(browserIntent);
            }
        });
        link = (TextView) findViewById(R.id.linkWeb);
        saveLink = (Button) findViewById(R.id.saveaLink);

        webTitle = getIntent().getStringExtra("title");
        webHost = getIntent().getStringExtra("host");
        webLink = getIntent().getStringExtra("link");
        webHost1 = webHost.replace("https://", "").replace("http://", "").replace("www.","");

        title.setText(Html.fromHtml(webTitle).toString().replace("\n","").trim());
        host.setText(Html.fromHtml(webHost1).toString().replace("\n","").trim());
        link.setText(Html.fromHtml(webLink).toString().replace("\n","").trim());

        saveLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Url.HttpUrl + "website/lacak.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object = new JSONObject(response);
                            String hasil = object.getString("hasil");
                            if (hasil.equals("sukses")){
                                Toast.makeText(ViewLacak.this, "sukses", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(ViewLacak.this, object.getString("pesan"), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(ViewLacak.this, "Pastikan kolom terisi semua dan pilih category", Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewLacak.this, "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> stringMap = new HashMap<>();

                        stringMap.put("i_host",host.getText().toString());
                        stringMap.put("i_link",link.getText().toString());
                        stringMap.put("i_title",title.getText().toString());
                        return stringMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
