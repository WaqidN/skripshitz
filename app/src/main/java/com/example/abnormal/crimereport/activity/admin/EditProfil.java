package com.example.abnormal.crimereport.activity.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by abnormal on 16/11/17.
 */

public class EditProfil extends Fragment {

    private EditText username, password, nik, nama_lengkap, alamat, no_tlpn, email, jk, tgl;
    private RadioButton laki_laki, perempuan;
    private static EditText tempat_lahir;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private static String tempat, tanggal, bln, thn;
    private String id;
    private EditText tmpatLhir;
    private TextView tnggalLahir;

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_edit_profile, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        getActivity().setTitle("Edit Profil");

        sharedPreferences = getActivity().getSharedPreferences("akun", MODE_APPEND);

        final RelativeLayout rl = (RelativeLayout) getActivity().findViewById(R.id.rl);

        username = (EditText) root.findViewById(R.id.editUser);
        password = (EditText) root.findViewById(R.id.editPass);
        nik = (EditText) root.findViewById(R.id.editNik);
        nama_lengkap = (EditText) root.findViewById(R.id.editNamaL);
        alamat = (EditText) root.findViewById(R.id.editAlamat);
        laki_laki = (RadioButton) root.findViewById(R.id.jkL);
        perempuan = (RadioButton) root.findViewById(R.id.jkLP);
        email = (EditText) root.findViewById(R.id.editEmail);
        no_tlpn = (EditText) root.findViewById(R.id.editNoHp);
        tmpatLhir = (EditText) root.findViewById(R.id.editTmptLahir);
        tnggalLahir = (TextView) root.findViewById(R.id.editTglLahir);

        Button btn = (Button) root.findViewById(R.id.picDate);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DialogFragment dFragment = new DatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");

                //Toast.makeText(getContext(), "ini", Toast.LENGTH_SHORT).show();
            }
        });

        try {
            JSONObject jsonObject = new JSONObject(sharedPreferences.getString("data", null));
            String[] split = jsonObject.getString("user_ttl").split(Pattern.quote("|"));
            tempat = split[0];
            tanggal = split[1];
            bln = split[2];
            thn = split[3];
            if (jsonObject.getString("user_jk").equals("perempuan")) {
                perempuan.setChecked(true);
            } else {
                laki_laki.setChecked(true);
            }
            username.setText(jsonObject.getString("user_username"));
            //password.setText(jsonObject.getString("user_password"));
            nik.setText(jsonObject.getString("user_nik"));
            id = jsonObject.getString("id");
            nama_lengkap.setText(jsonObject.getString("user_nlkp"));
            alamat.setText(jsonObject.getString("user_alamat"));
            String[] userttl = jsonObject.getString("user_ttl").split(Pattern.quote("|"));
            tmpatLhir.setText(userttl[0]);
            tnggalLahir.setText(userttl[1] + "-" + userttl[2] + "-" + userttl[3]);
            email.setText(jsonObject.getString("user_email"));
            no_tlpn.setText(jsonObject.getString("user_nohp"));
        } catch (Exception e) {
            Toast.makeText(getContext(), "error bro " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_send, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_user1) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Url.HttpUrl + "crimereport/user/editprofile.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Session session = new Session(getContext().getApplicationContext());
                        session.refreshData(jsonObject.getJSONObject("data").toString());
                        Intent cklik = new Intent(getContext(), DrawerAdmin.class);
                        startActivity(cklik);
                        getActivity().finish();
                        Toast.makeText(getContext(), "sukses", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("edit_username", username.getText().toString());
                    stringMap.put("edit_password", password.getText().toString());
                    stringMap.put("edit_nik", nik.getText().toString());
                    stringMap.put("id", id);
                    stringMap.put("edit_tempat", tempat);
                    stringMap.put("edit_tgl", tanggal);
                    stringMap.put("edit_thn", thn);
                    stringMap.put("edit_bln", bln);
                    stringMap.put("edit_alamat", alamat.getText().toString());
                    stringMap.put("edit_email", email.getText().toString());
                    stringMap.put("edit_nohp", no_tlpn.getText().toString());
                    stringMap.put("edit_nlkp", nama_lengkap.getText().toString());
                    if (laki_laki.isChecked()) {
                        stringMap.put("edit_jk", "laki-laki");
                    } else if (perempuan.isChecked()) {
                        stringMap.put("edit_jk", "perempuan");
                    }
                    return stringMap;
                }
            };
            requestQueue.add(stringRequest);
        }
        return true;
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