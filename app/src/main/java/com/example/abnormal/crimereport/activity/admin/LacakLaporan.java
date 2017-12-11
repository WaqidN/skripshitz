package com.example.abnormal.crimereport.activity.admin;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.Url;
import com.example.abnormal.crimereport.helper.DividerItemDecoration;
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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by abnormal on 15/06/17.
 */

public class LacakLaporan extends AppCompatActivity {

    RequestQueue requestQueue;
    private EditText lacak;
    private Button btnLacak,btnSimpan;
    private RadioGroup radioGrup;
    private static RadioButton rb1,rb2,rb3;
    private RecyclerView recyclerView;
    private AdapterLacak mAdapter;
    private static final String TAG = "Http Connection";
    public static final int CONNECTION_TIMEOUT = 5000;
    public static final int READ_TIMEOUT = 15000;

    int itemCount;
    GestureDetectorCompat gestureDetector;
    android.view.ActionMode actionMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lacak_laporan);

        getSupportActionBar().setTitle("Lacak Laporan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(this);

        lacak = (EditText)findViewById(R.id.lacak);
        String isi = getIntent().getStringExtra("data");
        lacak.setText(isi);

        recyclerView = (RecyclerView)findViewById(R.id.listLacak);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        rb1 = (RadioButton) findViewById(R.id.keyword);
        rb2 = (RadioButton) findViewById(R.id.web);
        rb3 = (RadioButton) findViewById(R.id.noTlphn);

        btnLacak = (Button)findViewById(R.id.btnLacak);
        btnLacak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb1.isChecked()){

                    new JsonSearchTask().execute();

                }else if (rb2.isChecked()){

                    new JsonSearchTask().execute();

                }else {

                    new JsonSearch().execute();

                }
            }
        });
    }

    private class JsonSearchTask extends AsyncTask<String, Void, Integer> {

        ProgressDialog pdLoading = new ProgressDialog(LacakLaporan.this);
        List<Lacak> data = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected Integer doInBackground(String... params) {

            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            Integer result = 0;

            try {

                String str = lacak.getText().toString();
                String a="+";
                final String strNoSpaces = str.toLowerCase().replaceAll(" ", a).replaceAll("-", a).replaceAll("!", a).replaceAll(",", a)
                        .replaceAll("&", a).replaceAll(":", a).replaceAll("=", a);

                URL url = new URL("https://www.googleapis.com/customsearch/v1" +
                        "?key=AIzaSyChD2ss_PQtSe0Iy7GA8cTO9h8rHzWfg-g" +
                        "&cx=016028823399849070585:7zeszkrwfsu" +
                        "&q="+strNoSpaces);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();

                if (statusCode ==  200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    String response = convertInputStreamToString(inputStream);

                    parseResult(response);

                    result = 1; // Successful

                }else{
                    result = 0; //"Failed to fetch data!";
                }
            }catch (Exception e){
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */

            if(result == 1){

                mAdapter = new AdapterLacak(LacakLaporan.this, data);
                recyclerView.setAdapter(mAdapter);


            }else{
                Log.e(TAG, "Failed to fetch data!");
            }
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {

            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

            String line = "";
            String result = "";

            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            /* Close Stream */
            if(null!=inputStream){
                inputStream.close();
            }

            return result;
        }

        private void parseResult(String result) {


            pdLoading.dismiss();

            try{
                JSONObject response = new JSONObject(result);
                JSONArray posts = response.optJSONArray("items");

                data = new ArrayList<>();

                for(int i=0; i< posts.length();i++ ){
                    JSONObject jsonObj = posts.optJSONObject(i);
                    Lacak lacak = new Lacak();
                    lacak.lacaktitle = jsonObj.getString("title");
                    lacak.lacakhost = jsonObj.getString("displayLink");
                    lacak.lacakFullLink = jsonObj.getString("link");
                    data.add(lacak);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    private class JsonSearch extends AsyncTask<String, Void, Integer> {

        ProgressDialog pdLoading = new ProgressDialog(LacakLaporan.this);
        List<Lacak> data = new ArrayList<>();
        URL url;

        String strSplit = null;
        String strSplit1 = null;
        String strSplit2 = null;
        String strTambah = null;

        String[] strArray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected Integer doInBackground(String... params) {

            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            Integer result = 0;

            try {



                String str = lacak.getText().toString();

                String abc = "";
                final String strReplace = str.toLowerCase().replaceAll(" ", abc).replaceAll("-", abc);
                final String strClean = strReplace.replace("+62", "0");

                if (strClean.length() == 12){
                    //membagi string
                    Pattern pattern = Pattern.compile("^(\\d{4})(\\d{4})(\\d{4})$");
                    Matcher matcher = pattern.matcher(strClean);
                    if (matcher.matches()) {
                        strSplit = (matcher.group(1))+"-"+(matcher.group(2))+"-"+(matcher.group(3));
                    }

                    //mengmabil string diawal string
                    final String strSubstring = strClean.substring(1);
                    strTambah = "+62"+strSubstring;

                    //Split nohp +62 bagian pertama
                    Pattern pattern1 = Pattern.compile("^(\\d{3})(\\d{4})(\\d{4})$");
                    Matcher matcher1 = pattern1.matcher(strSubstring);
                    if (matcher1.matches()) {
                        strSplit1 = "+62-"+(matcher1.group(1))+"-"+(matcher1.group(2))+"-"+(matcher1.group(3));
                    }

                    //Split nohp +62 bagian kedua
                    Pattern pattern2 = Pattern.compile("^(\\d{3})(\\d{4})(\\d{4})$");
                    Matcher matcher2 = pattern2.matcher(strSubstring);
                    if (matcher2.matches()) {
                        strSplit2 = "+62"+(matcher2.group(1))+"-"+(matcher2.group(2))+"-"+(matcher2.group(3));
                    }

                } else if (strClean.length() == 11){
                    //membagi string 08
                    Pattern pattern = Pattern.compile("^(\\d{4})(\\d{4})(\\d{3})$");
                    Matcher matcher = pattern.matcher(strClean);
                    if (matcher.matches()) {

                        strSplit = (matcher.group(1))+"-"+(matcher.group(2))+"-"+(matcher.group(3));

                    }

                    //mengmabil string diawal string
                    final String strSubstring = strClean.substring(1);
                    strTambah = "+62"+strSubstring;

                    //Split nohp +62
                    Pattern pattern1 = Pattern.compile("^(\\d{3})(\\d{4})(\\d{3})$");
                    Matcher matcher1 = pattern1.matcher(strSubstring);
                    if (matcher1.matches()) {
                        strSplit1 = "+62-"+(matcher1.group(1))+"-"+(matcher1.group(2))+"-"+(matcher1.group(3));
                    }

                    //Split nohp +62 bagian kedua
                    Pattern pattern2 = Pattern.compile("^(\\d{3})(\\d{4})(\\d{3})$");
                    Matcher matcher2 = pattern2.matcher(strSubstring);
                    if (matcher2.matches()) {
                        strSplit2 = "+62"+(matcher2.group(1))+"-"+(matcher2.group(2))+"-"+(matcher2.group(3));
                    }

                }else {
                    Toast.makeText(LacakLaporan.this, "Cek kembali nomer telphone yang anda masukkan", Toast.LENGTH_SHORT).show();
                }

                strArray = new String[]{strClean,strSplit,strTambah,strSplit1,strSplit2};

                for (int i=0; i < strArray.length; i++){
                    url = new URL("https://www.googleapis.com/customsearch/v1" +
                            "?key=AIzaSyChD2ss_PQtSe0Iy7GA8cTO9h8rHzWfg-g" +
                            "&cx=016028823399849070585:7zeszkrwfsu" +
                            "&q="+strArray[0]);
                }

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();

                if (statusCode ==  200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    String response = convertInputStreamToString(inputStream);

                    parseResult(response);

                    result = 1; // Successful

                }else{
                    result = 0; //"Failed to fetch data!";
                }
            }catch (Exception e){
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */

            if(result == 1){

                mAdapter = new AdapterLacak(LacakLaporan.this, data);
                recyclerView.setAdapter(mAdapter);


            }else{
                Log.e(TAG, "Failed to fetch data!");
            }
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {

            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

            String line = "";
            String result = "";

            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            /* Close Stream */
            if(null!=inputStream){
                inputStream.close();
            }

            return result;
        }

        private void parseResult(String result) {

            pdLoading.dismiss();

            try{



                JSONObject response = new JSONObject(result);
                JSONArray posts = response.optJSONArray("items");

                data = new ArrayList<>();

                for(int i=0; i< posts.length();i++ ){
                    JSONObject jsonObj = posts.optJSONObject(i);
                    Lacak lacak = new Lacak();
                    lacak.lacaktitle = jsonObj.getString("title");
                    lacak.lacakhost = jsonObj.getString("displayLink");
                    lacak.lacakFullLink = jsonObj.getString("link");
                    data.add(lacak);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
