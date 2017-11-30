package com.example.abnormal.crimereport.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.abnormal.crimereport.R;

public class TentangAplikasi extends Fragment {

    private TextView tntAplikasi;

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_tentang_aplikasi, container, false);

        getActivity().setTitle("Tentang Aplikasi");

        final RelativeLayout rl = (RelativeLayout) getActivity().findViewById(R.id.rl);

        tntAplikasi = (TextView) root.findViewById(R.id.tntngAplikasi);
        tntAplikasi.setText("CrimeReport merupakan aplikasi ang digunakan untuk melakukan pelaporan kejahatan online");

        return root;
    }
}