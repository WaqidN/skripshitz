package com.example.abnormal.crimereport.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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
import com.example.abnormal.crimereport.pojo.Session;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import static android.content.Context.MODE_APPEND;

public class EditProfil extends Fragment {

    private EditText username,password,nik,nama_lengkap,alamat,no_tlpn,email,jk,tgl ;
    private RadioButton laki_laki,perempuan;
    private static EditText tempat_lahir;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private static String tempat,tanggal,bln,thn;
    private String id;
    private EditText tmpatLhir;
    private TextView tnggalLahir;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestQueue = Volley.newRequestQueue(getContext());
        sharedPreferences = getActivity().getSharedPreferences("akun",MODE_APPEND);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbarProfil);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        final RelativeLayout rl = (RelativeLayout) getActivity().findViewById(R.id.rl);

        Button btn = (Button) getActivity().findViewById(R.id.picDate);
        /*btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                *//*DialogFragment dFragment = new DatePickerFragment();
                dFragment.show(getActivity().getFragmentManager(), "Date Picker");*//*

                Toast.makeText(getContext(), "ini", Toast.LENGTH_SHORT).show();
            }
        });*/
        username = (EditText) getActivity().findViewById(R.id.editUser);
        password = (EditText) getActivity().findViewById(R.id.editPass);
        nik = (EditText) getActivity().findViewById(R.id.editNik);
        nama_lengkap = (EditText) getActivity().findViewById(R.id.editNamaL);
        alamat = (EditText) getActivity().findViewById(R.id.editAlamat);
        laki_laki = (RadioButton) getActivity().findViewById(R.id.jkL);
        perempuan = (RadioButton) getActivity().findViewById(R.id.jkLP);
        email = (EditText) getActivity().findViewById(R.id.editEmail);
        no_tlpn = (EditText) getActivity().findViewById(R.id.editNoHp);
        tmpatLhir = (EditText)getActivity().findViewById(R.id.editTmptLahir);
        tnggalLahir = (TextView) getActivity().findViewById(R.id.editTglLahir);


        try{
            JSONObject jsonObject = new JSONObject(sharedPreferences.getString("data",null));
            String[] split = jsonObject.getString("user_ttl").split(Pattern.quote("|"));
            tempat = split[0];
            tanggal = split[1];
            bln = split[2];
            thn = split[3];
            if(jsonObject.getString("user_jk").equals("perempuan")){
                perempuan.setChecked(true);
            }else{
                laki_laki.setChecked(true);
            }
            username.setText(jsonObject.getString("user_username"));
            password.setText(jsonObject.getString("user_password"));
            nik.setText(jsonObject.getString("user_nik"));
            id = jsonObject.getString("id");
            nama_lengkap.setText(jsonObject.getString("user_nlkp"));
            alamat.setText(jsonObject.getString("user_alamat"));
            String[] userttl = jsonObject.getString("user_ttl").split(Pattern.quote("|"));
            tmpatLhir.setText(userttl[0]);
            tnggalLahir.setText(userttl[1]+"-"+userttl[2]+"-"+userttl[3]);
            email.setText(jsonObject.getString("user_email"));
            no_tlpn.setText(jsonObject.getString("user_nohp"));
        }catch (Exception e){
            Toast.makeText(getContext(), "error bro "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        //tempat_lahir = (EditText) findViewById(R.id.editTmptLahir);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.icon_actionbar, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_user){
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Url.HttpUrl+"crimereport/user/editprofile.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                 try{
                     JSONObject jsonObject = new JSONObject(response);
                     Session session = new Session(getContext().getApplicationContext());
                     session.refreshData(jsonObject.getJSONObject("data").toString());
                     Toast.makeText(getContext(), "sukses", Toast.LENGTH_SHORT).show();

                 }catch (Exception e){
                     e.printStackTrace();
                 }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> stringMap = new HashMap<>();
                    stringMap.put("edit_username",username.getText().toString());
                    stringMap.put("edit_password",password.getText().toString());
                    stringMap.put("edit_nik",nik.getText().toString());
                    stringMap.put("id",id);
                    stringMap.put("edit_tempat",tempat);
                    stringMap.put("edit_tgl",tanggal);
                    stringMap.put("edit_thn",thn);
                    stringMap.put("edit_bln",bln);
                    stringMap.put("edit_alamat",alamat.getText().toString());
                    stringMap.put("edit_email",email.getText().toString());
                    stringMap.put("edit_nohp",no_tlpn.getText().toString());
                    stringMap.put("edit_nlkp",nama_lengkap.getText().toString());
                    if(laki_laki.isChecked()){
                        stringMap.put("edit_jk","laki-laki");
                    }else if(perempuan.isChecked()){
                        stringMap.put("edit_jk","perempuan");
                    }
                    return stringMap;
                }
            };
            requestQueue.add(stringRequest);
        }
        return super.onOptionsItemSelected(item);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT,this,year,month,day);

            return  dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){

            TextView tv = (TextView) getActivity().findViewById(R.id.editTglLahir);
            thn = String.valueOf(year);
            bln = String.valueOf(month);
            tanggal = String.valueOf(day);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
            String formattedDate = df.format(chosenDate);

            tv.setText(formattedDate);
        }
    }
}
