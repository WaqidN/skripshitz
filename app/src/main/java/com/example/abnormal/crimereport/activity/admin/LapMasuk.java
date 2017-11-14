package com.example.abnormal.crimereport.activity.admin;

import android.content.Context;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.helper.DividerItemDecoration;
import com.example.abnormal.crimereport.model.Message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.abnormal.crimereport.Url.HttpUrl;

public class LapMasuk extends Fragment{

    private List<Message> messageList;
    private RecyclerView recyclerView;
    RequestQueue requestQueue;
    private static Context context;
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
        String Json = HttpUrl+"crimereport/laporan/alllaporan.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                List<Message> list = new ArrayList<>();
                try {

                    JSONArray jsonArray = response.getJSONArray("hasil");
                    for (int i = 0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Message ms = new Message();
                        ms.idnya = jsonObject.getString("id");
                        ms.pelaku = jsonObject.getString("l_nama");
                        ms.nama  = jsonObject.getString("user_username");
                        ms.title = jsonObject.getString("l_title");
                        ms.desk  = jsonObject.getString("l_des");
                        ms.getTimestamp = jsonObject.getString("l_date");
                        ms.nomor_telpon = jsonObject.getString("l_nohp");
                        ms.email = jsonObject.getString("l_email");
                        ms.website = jsonObject.getString("l_website");
                        ms.pict = jsonObject.getString("l_file");
                        ms.status = jsonObject.getString("l_status");
                        list.add(ms);
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                SetAdapter adapter = new SetAdapter(list);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    class SetAdapter extends RecyclerView.Adapter<SetAdapter.Holder>{

        List<Message> datanya;

        SetAdapter(List<Message> datanya){
            this.datanya = datanya;
        }

        class Holder extends RecyclerView.ViewHolder{

            TextView desknya,namanya,titlenya,timenya, idnya;

            public Holder(View itemView) {
                super(itemView);

                namanya = (TextView)itemView.findViewById(R.id.from);
                titlenya = (TextView)itemView.findViewById(R.id.txt_primary);
                desknya = (TextView)itemView.findViewById(R.id.txt_secondary);
                timenya = (TextView)itemView.findViewById(R.id.timestamp);

            }
        }

        @Override
        public SetAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_row, parent, false);
            SetAdapter.Holder hol = new SetAdapter.Holder(v);
            return hol;
        }

        @Override
        public void onBindViewHolder(SetAdapter.Holder holder, final int position) {

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
