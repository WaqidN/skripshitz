package com.example.abnormal.crimereport.pojo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.model.Lacak;

import java.util.Collections;
import java.util.List;

/**
 * Created by abnormal on 30/11/17.
 */

public class AdapterLacak extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Lacak> data= Collections.emptyList();
    Lacak current;
    int currentPos=0;

    public AdapterLacak(Context context, List<Lacak> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_lacak, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        Lacak current=data.get(position);
        myHolder.judulnya.setText(current.Lacaktitle);
        myHolder.websitenya.setText(current.Lacakwebsite);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView judulnya,websitenya;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            judulnya= (TextView) itemView.findViewById(R.id.titleL);
            websitenya= (TextView) itemView.findViewById(R.id.websiteL);
        }

    }
}
