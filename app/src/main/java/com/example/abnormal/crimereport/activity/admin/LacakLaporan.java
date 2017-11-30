package com.example.abnormal.crimereport.activity.admin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.model.Lacak;
import com.example.abnormal.crimereport.pojo.AdapterLacak;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abnormal on 15/06/17.
 */

public class LacakLaporan extends AppCompatActivity {

    private EditText lacak;
    private Button btnLacak;
    private TextView result;
    private RecyclerView recyclerView;
    private AdapterLacak mAdapter;


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lacak_laporan);

        lacak = (EditText)findViewById(R.id.lacak);

        btnLacak = (Button)findViewById(R.id.btnLacak);
        btnLacak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new JsonSearchTask().execute();
            }
        });
    }

    private class JsonSearchTask extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(LacakLaporan.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                String str = lacak.getText().toString();
                final String strNoSpaces = str.replace(" ", "+");

                url = new URL("https://www.googleapis.com/customsearch/v1" +
                        "?key=AIzaSyChD2ss_PQtSe0Iy7GA8cTO9h8rHzWfg-g" +
                        "&cx=016028823399849070585:7zeszkrwfsu" +
                        "&q="+strNoSpaces);


            }catch (MalformedURLException e){
                e.printStackTrace();
                return e.toString();
            }

            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            }catch (IOException e1){
                e1.printStackTrace();
                return e1.toString();
            }

            try {
                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == conn.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuffer result  = new StringBuffer();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }


                    // Pass data to onPostExecute method
                    return result.toString();

                } else {

                    return ("unsuccessful");
                }

            }catch (IOException e){
                e.printStackTrace();
                return e.toString();
            }
            finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<Lacak> data = new ArrayList<>();
            pdLoading.dismiss();
            try {

                //JSONObject jsonObject = new JSONObject(result);
                JSONArray jaray = new JSONArray(result);

                for (int i = 0; i<jaray.length(); i++){

                    JSONObject jsonObj = jaray.getJSONObject(i);
                    Lacak lacak = new Lacak();
                    lacak.Lacaktitle = jsonObj.getString("name");
                    lacak.Lacakwebsite = jsonObj.getString("link");
                    data.add(lacak);
                }

                // Setup and Handover data to recyclerview
                recyclerView = (RecyclerView)findViewById(R.id.listLacak);
                mAdapter = new AdapterLacak(LacakLaporan.this, data);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(LacakLaporan.this));

            } catch (JSONException e) {
                Toast.makeText(LacakLaporan.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }
}
