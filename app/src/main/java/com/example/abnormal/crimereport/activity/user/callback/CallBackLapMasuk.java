package com.example.abnormal.crimereport.activity.user.callback;

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
 * Created by abnormal on 15/11/17.
 */

public class CallBackLapMasuk {

    RequestQueue requestQueue;
    String url = HttpUrl+"laporan/user_lap.php?id=";

    public CallBackLapMasuk(Context context){

        requestQueue = Volley.newRequestQueue(context);
    }

    public void CallBackLapMasuk(String id, final LapMasukBack lapMasukBack){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    Log.e("data", response.toString());
                    lapMasukBack.hasil(response.toString());

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
    public interface LapMasukBack{
        void hasil (String hasil);
    }
}
