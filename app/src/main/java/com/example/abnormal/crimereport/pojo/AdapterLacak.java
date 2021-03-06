package com.example.abnormal.crimereport.pojo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.activity.admin.LacakLaporan;
import com.example.abnormal.crimereport.activity.admin.ViewLacak;
import com.example.abnormal.crimereport.activity.admin.ViewWeb;
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
            //mChecked = (CheckBox) itemView.findViewById(R.id.checkBox);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }

    public AdapterLacak(Context context, List<Lacak> data){
        this.mcontext=context;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyHolder myHolder = (MyHolder) holder;
        final Lacak current = data.get(position);
        final int pos = position;

        myHolder.judulnya.setText("Title : " + current.lacaktitle);
        myHolder.websitenya.setText("Link  : " + current.lacakFullLink);

        /*myHolder.mChecked.setChecked(data.get(position).isSelected());
        myHolder.mChecked.setTag(data.get(position));*/
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getLacakhost()));
                v.getContext().startActivity(browserIntent);*/
                Intent intent = new Intent(mcontext, ViewLacak.class);
                intent.putExtra("title",data.get(position).lacaktitle);
                intent.putExtra("host", data.get(position).lacakhost);
                intent.putExtra("link", data.get(position).lacakFullLink);
                mcontext.startActivity(intent);

            }
        });

        /*myHolder.mChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Lacak lacak = (Lacak) cb.getTag();

                lacak.setSelected(cb.isChecked());
                data.get(pos).setSelected(cb.isChecked());
            }
        });*/

    }

    @Override
    public int getItemCount()    {
        return (null != data ? data.size() : 0);
    }

    public List<Lacak> getLacakist() {
        return data;
    }
}
