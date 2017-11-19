package com.example.abnormal.crimereport.activity.user;

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
import com.example.abnormal.crimereport.model.userM.Lap_View;
import com.example.abnormal.crimereport.pojo.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.abnormal.crimereport.Url.HttpUrl;

public class        LaporanUser extends Fragment {

    private List<Lap_View> messageList;
    private RecyclerView recyclerView;
    RequestQueue requestQueue;
    private static Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.user_content_lap, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Session session = new Session(getContext());
        final String data = session.getid();

        getActivity().setTitle("Laporan");

        requestQueue = Volley.newRequestQueue(getContext());

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_UL);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.newLapUL);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), NewLapUser.class);
                startActivity(i);
            }
        });
        
        ambildata(data);
    }

    public void onResume(){
        super.onResume();
        ambildata(new Session(getContext()).getid());
    }

    private void ambildata(String id) {

        String Json = HttpUrl+"crimereport/laporan/user_lap.php?id="+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<Lap_View> list = new ArrayList<>();

                try {
                    JSONArray jsonArray = response.getJSONArray("hasil");
                    for (int a = 0; a < jsonArray.length(); a++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(a);
                        Lap_View lap = new Lap_View();
                        lap.idUser = jsonObject.getString("id");
                        lap.namapelaku = jsonObject.getString("l_nama");
                        lap.namaUser = jsonObject.getString("user_username");
                        lap.titleUser = jsonObject.getString("l_title");
                        lap.deskUser = jsonObject.getString("l_des");
                        lap.getTimestampUser = jsonObject.getString("l_date");
                        lap.nohpUser = jsonObject.getString("l_nohp");
                        lap.emailUser = jsonObject.getString("l_email");
                        lap.websiteUser = jsonObject.getString("l_website");
                        lap.pictUser = jsonObject.getString("l_file");
                        list.add(lap);


                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                SetAdapter adpter = new SetAdapter(list);
                recyclerView.setAdapter(adpter);
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

        private List<Lap_View> datanya;

        SetAdapter(List<Lap_View> datanya){
            this.datanya = datanya;
        }

        public SetAdapter(LaporanUser laporanUser, List<Lap_View> messageList){

        }

        class Holder extends RecyclerView.ViewHolder{

            TextView deskini,namaini,titleini,timeini, idini;

            public Holder(View itemView) {
                super(itemView);

                namaini = (TextView)itemView.findViewById(R.id.fromUL);
                titleini = (TextView)itemView.findViewById(R.id.txt_primaryUL);
                deskini = (TextView)itemView.findViewById(R.id.txt_secondaryUL);
                timeini = (TextView)itemView.findViewById(R.id.timestampUL);
                idini = (TextView)itemView.findViewById(R.id.idUL);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }

        @Override
        public SetAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_lap, parent, false);
            SetAdapter.Holder hol = new SetAdapter.Holder(v);
            return hol;
        }

        @Override
        public void onBindViewHolder(SetAdapter.Holder holder, final int position) {

            holder.namaini.setText(datanya.get(position).namaUser);
            holder.titleini.setText(datanya.get(position).titleUser);
            holder.deskini.setText(datanya.get(position).deskUser);
            holder.timeini.setText(datanya.get(position).getTimestampUser);
            holder.idini.setText(datanya.get(position).idUser);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), UserViewLap.class);
                    intent.putExtra("idnya", datanya.get(position).idUser);
                    intent.putExtra("namapelaku", datanya.get(position).namapelaku);
                    intent.putExtra("email", datanya.get(position).emailUser);
                    intent.putExtra("website", datanya.get(position).websiteUser);
                    intent.putExtra("nohp", datanya.get(position).nohpUser);
                    intent.putExtra("title", datanya.get(position).titleUser);
                    intent.putExtra("des", datanya.get(position).deskUser);
                    intent.putExtra("pict", datanya.get(position).pictUser);
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return this.datanya.size();
        }


    }

}
