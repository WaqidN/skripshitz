package com.example.abnormal.crimereport.activity.admin.callback;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static com.example.abnormal.crimereport.Url.HttpUrl;

/**
 * Created by abnormal on 20/12/17.
 */

public class PenipuCallBack {
    RequestQueue requestQueue;
    String url = HttpUrl+"website/penipu.php";

    public PenipuCallBack(Context context){

        requestQueue = Volley.newRequestQueue(context);
    }

    public void PenipuCallBack (final PenipuCallBack.PenipuBack webBack){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    Log.e("data", response.toString());
                    webBack.hasil(response.toString());

                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }
    public interface PenipuBack{
        void hasil (String hasil);
    }
}
