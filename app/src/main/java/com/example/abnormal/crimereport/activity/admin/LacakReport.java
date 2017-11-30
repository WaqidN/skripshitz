package com.example.abnormal.crimereport.activity.admin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.abnormal.crimereport.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by abnormal on 15/06/17.
 */

public class LacakReport extends AppCompatActivity {

    private EditText lacak;
    private Button btnLacak;
    private ListView listView;
    private  ArrayAdapter arrayAdapter;

    private String[] blogTitles;
    private static final String TAG = "Http Connection";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lacak_laporan);

        listView = (ListView)findViewById(R.id.listLacak);
        //result = (TextView) findViewById(R.id.result);
        lacak = (EditText)findViewById(R.id.lacak);


        btnLacak = (Button)findViewById(R.id.btnLacak);
        btnLacak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new JsonSearchTask().execute();
            }
        });
    }


    private class JsonSearchTask extends AsyncTask<String, Void, Integer>{
        //ProgressDialog pdLoading = new ProgressDialog(LacakReport.this);

        @Override
        protected Integer doInBackground(String... params) {

            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;

            try {

                String str = lacak.getText().toString();
                final String strNoSpaces = str.replace(" ", "+");

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

                arrayAdapter = new ArrayAdapter(LacakReport.this, android.R.layout.simple_list_item_1, blogTitles);
                listView.setAdapter(arrayAdapter);
            }else{
                Log.e(TAG, "Failed to fetch data!");
            }
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

        try{
            JSONObject response = new JSONObject(result);

            JSONArray posts = response.optJSONArray("items");

            blogTitles = new String[posts.length()];

            for(int i=0; i< posts.length();i++ ){
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("title");
                String link = post.optString("link");

                blogTitles[i] = title;
                blogTitles[i]=link;
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
