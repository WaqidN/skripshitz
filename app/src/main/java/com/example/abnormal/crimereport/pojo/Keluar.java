package com.example.abnormal.crimereport.pojo;

import com.example.abnormal.crimereport.Url;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by abnormal on 19/11/17.
 */

public class Keluar {

    public Keluar() {
    }

    public String hapusToken(String token){

        try {

            String kon = Url.HttpUrl+"crimereport/user/keluar.php";
            URL obj = new URL(kon);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("token="+token);
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(stringBuilder.toString());
            outputStream.flush();
            outputStream.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            bufferedReader.close();
            return sb.toString();

        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String removeToken (String token){
        try {
            String url = Url.HttpUrl+"crimereport/user/keluar.php";

            URL ob = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) ob.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("token="+token);
            connection.setDoOutput(true);
            DataOutputStream outputStream= new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(stringBuilder.toString());
            outputStream.flush();
            outputStream.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) !=null ){
                sb.append(line);
            }

            bufferedReader.close();
            return  sb.toString();

        }catch (Exception e) {
            return e.getMessage();
        }
    }
}
