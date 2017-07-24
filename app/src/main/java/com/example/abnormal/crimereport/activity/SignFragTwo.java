package com.example.abnormal.crimereport.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.abnormal.crimereport.R;

import java.util.ArrayList;
import java.util.List;


public class SignFragTwo extends Fragment  {

    public SignFragTwo() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_sign_frag_two, container, false);
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        namaBulan.add("Januari");
        namaBulan.add("Februari");
        namaBulan.add("Maret");
        namaBulan.add("April");
        namaBulan.add("Mei");
        namaBulan.add("Juni");
        namaBulan.add("Juli");
        namaBulan.add("Agustus");
        namaBulan.add("September");
        namaBulan.add("Oktober");
        namaBulan.add("November");
        namaBulan.add("Desember");
        ((Button)getActivity().findViewById(R.id.nextSign2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignActivity.viewPagerSign.setCurrentItem(2);
            }
        });
        nik = (EditText)getActivity().findViewById(R.id.signNik);
        nama = (EditText)getActivity().findViewById(R.id.signNamaL);
        alamat = (EditText)getActivity().findViewById(R.id.SignAlamat);
        ttl = (EditText)getActivity().findViewById(R.id.signTmptLahir);
        laki = (RadioButton)getActivity().findViewById(R.id.jkLk);
        perempuan = (RadioButton)getActivity().findViewById(R.id.jkLPm);
        ((Button)getActivity().findViewById(R.id.picDate1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment pickerFragment = new DatePickerFragment();
                pickerFragment.show(getActivity().getSupportFragmentManager(),"Pilih Tanggal");
                ((TextView)getActivity().findViewById(R.id.signTglLahir)).setText(
                        ttgl+"|"+tbulan+"|"+ttahbun
                );
            }
        });
    }

    public static EditText nik,nama,alamat,ttl;
    public static String tbulan=null,ttahbun=null,ttgl=null;
    public static RadioButton laki,perempuan;
    public static String  tahun_lahir;
    private static List<String> namaBulan = new ArrayList<>();
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
            ttahbun = String.valueOf(year);
            tbulan = String.valueOf(month);
            ttgl  = String.valueOf(day);
        }
    }
}
