package com.example.abnormal.crimereport.pojo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

/**
 * Created by abnormal on 07/09/17.
 */

public class Session {
    private Context context;
    private SharedPreferences sharedPreferences;
    @SuppressLint("WrongConstant")
    public Session(Context context){
        this.context            = context;
        this.sharedPreferences  = context.getSharedPreferences("akun", Context.MODE_APPEND);
    }


    public void tambahDataLogin(String data,String role){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data"         ,data);
        editor.putString("role"         ,role);
        editor.putBoolean("sudahlogin"  , true);
        editor.commit();
    }
    public void refreshData(String data){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data"         ,data);
        editor.putBoolean("sudahlogin"  , true);
        editor.commit();
    }
    public String getid(){
        String json = sharedPreferences.getString("data",null);
        String id   = null;
        try{
            JSONObject jsonObject = new JSONObject(json);
            id                    = jsonObject.getString("id");

        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public boolean sudahLogin(){
        boolean sudahlogin = this.sharedPreferences.getBoolean("sudahlogin",false);
        return sudahlogin;
    }
    public String getRole(){
        return this.sharedPreferences.getString("role",null);
    }
    public void keluar(){
        this.sharedPreferences.edit().clear();
    }

}
