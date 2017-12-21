package com.example.abnormal.crimereport.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignFragThree extends Fragment {
    private EditText email,nomor;
    private CheckBox box;
    private RequestQueue queue;
    public SignFragThree(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_sign_frag_three, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queue = Volley.newRequestQueue(getContext());
        email = (EditText)getActivity().findViewById(R.id.signEmail);
        nomor = (EditText)getActivity().findViewById(R.id.signNoHp);
        box   = (CheckBox)getActivity().findViewById(R.id.cekbox);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        ((Button)getActivity().findViewById(R.id.Register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(box.isChecked()){
                    progressDialog.setMessage("Mohon Tunggu ...");
                    progressDialog.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            Url.HttpUrl+"user/register.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try{
                                JSONObject object = new JSONObject(response);
                                String hasil = object.getString("hasil");
                                if (hasil.equals("sukses")){
                                    Toast.makeText(getContext(), "sukses", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }else{
                                    Toast.makeText(getContext(), object.getString("pesan"), Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                Toast.makeText(getContext(), e.getMessage()+" "+response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "ada error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> stringMap = new HashMap<String, String>();
                            stringMap.put("user_username",SignFragOne.getUsername().getText().toString());
                            stringMap.put("user_password",SignFragOne.password.getText().toString());
                            stringMap.put("user_repassword",SignFragOne.repassword.getText().toString());
                            stringMap.put("user_nik",SignFragTwo.nik.getText().toString());
                            stringMap.put("user_nlkp",SignFragTwo.nama.getText().toString());
                            stringMap.put("user_alamat",SignFragTwo.alamat.getText().toString());
                            stringMap.put("ttl_tempat",SignFragTwo.ttl.getText().toString());
                            stringMap.put("ttl_tgl",SignFragTwo.ttgl);
                            stringMap.put("ttl_bln",SignFragTwo.tbulan);
                            stringMap.put("ttl_thn",SignFragTwo.ttahbun);
                            if(SignFragTwo.laki.isChecked()){
                                stringMap.put("user_jk","laki-laki");
                            }else if(SignFragTwo.perempuan.isChecked()){
                                stringMap.put("user_jk","perempuan");
                            }
                            stringMap.put("user_nohp",nomor.getText().toString());
                            stringMap.put("user_email",email.getText().toString());

                            return stringMap;
                        }
                    };

                    queue.add(stringRequest);
                }
            }
        });
    }
}
