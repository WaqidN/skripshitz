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
 * Created by abnormal on 16/11/17.
 */

public class PostCallBack {

    RequestQueue requestQueue;
    String url = HttpUrl+"post/viewpost.php";

    public PostCallBack(Context context){

        requestQueue = Volley.newRequestQueue(context);
    }

    public void PostCallBack (final PostCallBack.PostBack postBack){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    Log.e("data", response.toString());
                    postBack.hasil(response.toString());

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
    public interface PostBack{
        void hasil (String hasil);
    }
}
