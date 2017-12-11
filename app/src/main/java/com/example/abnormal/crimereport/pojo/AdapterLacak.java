package com.example.abnormal.crimereport.pojo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.model.Lacak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by abnormal on 30/11/17.
 */

public class AdapterLacak extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mcontext;
    private LayoutInflater inflater;
    List<Lacak> data= Collections.emptyList();
    Lacak current;
    int currentPos=0;
    private SparseBooleanArray selectedItems;

    public class MyHolder extends RecyclerView.ViewHolder  {

        TextView judulnya,websitenya;
        CheckBox mChecked;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            judulnya= (TextView) itemView.findViewById(R.id.titleL);
            websitenya= (TextView) itemView.findViewById(R.id.websiteL);
            mChecked = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }

    public AdapterLacak(Context context, List<Lacak> data){
        this.mcontext=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
        selectedItems = new SparseBooleanArray();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_lacak, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        Lacak current = data.get(position);
        myHolder.judulnya.setText("Title : " + current.lacaktitle);
        myHolder.websitenya.setText("Link : " + current.lacakhost);

        myHolder.itemView.setActivated(selectedItems.get(position, false));

    }

    @Override
    public int getItemCount()    {
        return (null != data ? data.size() : 0);
    }
}