package com.example.abnormal.crimereport.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.abnormal.crimereport.R;

public class Bantuan extends Fragment {

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_bantuan, container, false);

        getActivity().setTitle("Bantuan");

        final RelativeLayout rl = (RelativeLayout) getActivity().findViewById(R.id.rl);

        ImageView imageView = (ImageView)root.findViewById(R.id.imageB);

        return root;
    }
}
