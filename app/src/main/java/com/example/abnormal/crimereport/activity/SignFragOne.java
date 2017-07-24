package com.example.abnormal.crimereport.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.abnormal.crimereport.R;

public class SignFragOne extends Fragment {
    public static EditText username;
    public static EditText password;
    public static EditText repassword;
    public SignFragOne(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_sign_frag_one, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        username = (EditText)getActivity().findViewById(R.id.signUser);
        password = (EditText)getActivity().findViewById(R.id.signPass);
        repassword = (EditText)getActivity().findViewById(R.id.signRePass);
        ((Button)getActivity().findViewById(R.id.nextSign1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignActivity.viewPagerSign.setCurrentItem(1);
            }
        });
    }
    public static EditText getUsername(){
        return username;
    }
}
