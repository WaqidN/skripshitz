package com.example.abnormal.crimereport.activity.user.callback;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abnormal.crimereport.activity.admin.callback.PenipuCallBack;

import org.json.JSONObject;

import static com.example.abnormal.crimereport.Url.HttpUrl;

/**
 * Created by abnormal on 28/01/18.
 */

public class CallBackPenipuan {

    RequestQueue requestQueue;
    String url = HttpUrl+"website/penipu.php";

    public CallBackPenipuan(Context context){

        requestQueue = Volley.newRequestQueue(context);
    }

    public void PenipuanCallBack (final CallBackPenipuan.PenipuanBack webBack){

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
    public interface PenipuanBack{
        void hasil (String hasil);
    }
}
