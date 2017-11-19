package com.example.abnormal.crimereport.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.abnormal.crimereport.activity.admin.DrawerAdmin;
import com.example.abnormal.crimereport.activity.user.DrawerUser;
import com.example.abnormal.crimereport.pojo.Session;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn;
    TextView SignUp;
    EditText username,password;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("akun",MODE_APPEND);
        btnSignIn=(Button)findViewById(R.id.btnLogin);

        requestQueue = Volley.newRequestQueue(this);

        username = (EditText)findViewById(R.id.username);

        password = (EditText)findViewById(R.id.pass);
        final String token = FirebaseInstanceId.getInstance().getToken();

        Session session = new Session(this);
        if(session.sudahLogin() == true){
            if(session.getRole().equals("admin")){
                startActivity(new Intent(this,DrawerAdmin.class));
            }else{
                startActivity(new Intent(this,DrawerUser.class));
            }
            finish();
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Mohong tunggu ...");
                progressDialog.show();;
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Url.HttpUrl+"crimereport/user/login.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("hasil").equals("sukses")){
                                String role = jsonObject.getJSONObject("data").getString("user_status");
                                if(role.equals("user")){
                                    Session session = new Session(getApplicationContext());
                                    session.tambahDataLogin(jsonObject.getJSONObject("data").toString()
                                    ,role);
                                    Intent intent = new Intent(getApplicationContext(),DrawerUser.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Session session = new Session(getApplicationContext());
                                    session.tambahDataLogin(jsonObject.getJSONObject("data").toString(),
                                            role);
                                    Intent intent = new Intent(getApplicationContext(),DrawerAdmin.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }else{
                                Toast.makeText(LoginActivity.this, "login gagal", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "kolom tidak boleh kosong "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        };

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> stringMap = new HashMap<String, String>();
                        stringMap.put("username",username.getText().toString());
                        stringMap.put("password",password.getText().toString());
                        stringMap.put("token",token);
                        return stringMap;
                    }
                };
            requestQueue.add(stringRequest);
            }
        });

        SignUp=(TextView)findViewById(R.id.sign_up);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(signUp);

            }
        });
    }

}
