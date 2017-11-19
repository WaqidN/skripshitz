package com.example.abnormal.crimereport.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.activity.admin.callback.LapMasukCallBack;
import com.example.abnormal.crimereport.helper.DividerItemDecoration;
import com.example.abnormal.crimereport.model.Message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LapMasuk extends Fragment{

    private List<Message> messageList;
    private RecyclerView recyclerView;
    RequestQueue requestQueue;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          return inflater.inflate(R.layout.content_masuk, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        requestQueue = Volley.newRequestQueue(getContext());

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        getActivity().setTitle("Lap.Masuk");

        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.newLap);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), NewReport.class);
                startActivity(i);
                getActivity().finish();
            }
        });


        ambildata();
    }

    @Override
    public void onResume() {
        super.onResume();
        ambildata();
    }

    public void ambildata(){

            LapMasukCallBack lc = new LapMasukCallBack(getContext());

            lc.LapMasukCallBack(new LapMasukCallBack.LapMasukBack() {
            @Override
            public void hasil(String hasil) {

                List<Message> list = new ArrayList<>();

                try {

                    JSONObject jsonObject = new JSONObject(hasil);
                    JSONArray jaray = jsonObject.getJSONArray("hasil");

                    for (int i = 0; i<jaray.length(); i++){

                        JSONObject jsonObj = jaray.getJSONObject(i);
                        Message ms = new Message();
                        ms.idnya = jsonObj.getString("id");
                        ms.pelaku = jsonObj.getString("l_nama");
                        ms.nama  = jsonObj.getString("user_username");
                        ms.title = jsonObj.getString("l_title");
                        ms.desk  = jsonObj.getString("l_des");
                        ms.getTimestamp = jsonObj.getString("l_date");
                        ms.nomor_telpon = jsonObj.getString("l_nohp");
                        ms.email = jsonObj.getString("l_email");
                        ms.website = jsonObj.getString("l_website");
                        ms.pict = jsonObj.getString("l_file");
                        ms.status = jsonObj.getString("l_status");
                        list.add(ms);
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                SetAdapter adapter = new SetAdapter(list);
                recyclerView.setAdapter(adapter);

            }
        });
    }

    class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder>{

        List<Message> datanya;

        SetAdapter(List<Message> datanya){
            this.datanya = datanya;
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView desknya,namanya,titlenya,timenya, idnya;

            public ViewHolder(View itemView) {
                super(itemView);

                namanya = (TextView)itemView.findViewById(R.id.from);
                titlenya = (TextView)itemView.findViewById(R.id.txt_primary);
                desknya = (TextView)itemView.findViewById(R.id.txt_secondary);
                timenya = (TextView)itemView.findViewById(R.id.timestamp);

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_row, parent, false);
            SetAdapter.ViewHolder hol = new SetAdapter.ViewHolder(view);
            return hol;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.namanya.setText(datanya.get(position).nama);
            holder.titlenya.setText(datanya.get(position).title);
            holder.desknya.setText(datanya.get(position).desk);
            holder.timenya.setText(datanya.get(position).getTimestamp);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   Intent cklik =  new Intent(getActivity(), EditLaporan.class);
                    Bundle extras = new Bundle();
                    extras.putString("idnya", datanya.get(position).idnya);
                    extras.putString("namapelaku",datanya.get(position).pelaku);
                    extras.putString("email", datanya.get(position).email);
                    extras.putString("website", datanya.get(position).website);
                    extras.putString("nohp", datanya.get(position).nomor_telpon);
                    extras.putString("judul", datanya.get(position).title);
                    extras.putString("ket", datanya.get(position).desk);
                    extras.putString("status", datanya.get(position).status);
                    extras.putString("pict", datanya.get(position).pict);
                    cklik.putExtras(extras);
                    startActivity(cklik);
                }
            });

        }

        @Override
        public int getItemCount() {
            return this.datanya.size();
        }


    }
}
