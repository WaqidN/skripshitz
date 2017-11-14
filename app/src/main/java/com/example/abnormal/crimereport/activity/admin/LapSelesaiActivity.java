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
import android.support.v7.widget.Toolbar;
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
import com.example.abnormal.crimereport.model.MSelesai;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.abnormal.crimereport.Url.HttpUrl;

/**
 * Created by abnormal on 17/06/17.
 */

public class LapSelesaiActivity extends Fragment {

    private Toolbar toolbarSelesai;
    RequestQueue requestQueue;
    private RecyclerView recyclerView2;
    private SwipeRefreshLayout sswipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_selesai,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


       /* toolbarSelesai = (Toolbar) getActivity().findViewById(R.id.toolbarSelesai);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarSelesai);*/

        requestQueue = Volley.newRequestQueue(getContext());

        getActivity().setTitle("Lap.Selesai");

        recyclerView2 = (RecyclerView)getActivity().findViewById(R.id.recycler_views);
        recyclerView2.setHasFixedSize(true);
        RecyclerView.LayoutManager recycleselesai = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(recycleselesai);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        sswipeRefreshLayout = (SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_refresh_layouts);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.newLap);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), NewReport.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        ambildatanya();

    }

    private void ambildatanya() {

        String Json = HttpUrl+"crimereport/laporan/lap_selesai.php";

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                List<MSelesai> lists = new ArrayList<>();

                try {
                    JSONArray jsonArray = response.getJSONArray("hasil");

                    for (int a = 0; a<jsonArray.length(); a++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(a);

                        MSelesai selesai = new MSelesai();
                        selesai.ids = jsonObject1.getString("id");
                        selesai.namas = jsonObject1.getString("user_username");
                        selesai.pelakus = jsonObject1.getString("l_nama");
                        selesai.titles = jsonObject1.getString("l_title");
                        selesai.desks = jsonObject1.getString("l_des");
                        selesai.getTimestamps = jsonObject1.getString("l_date");
                        selesai.nohs = jsonObject1.getString("l_nohp");
                        selesai.emails = jsonObject1.getString("l_email");
                        selesai.websites = jsonObject1.getString("l_website");
                        selesai.statuss = jsonObject1.getString("l_status");
                        selesai.picts = jsonObject1.getString("l_file");
                        lists.add(selesai);
                    }


                } catch (Exception e) {

                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                SetAdapter adapters = new SetAdapter(lists);
                recyclerView2.setAdapter(adapters);
            }


        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest2);
    }

    class SetAdapter extends RecyclerView.Adapter<SetAdapter.Holder>{

        List<MSelesai> dataSelesai;
        SetAdapter(List<MSelesai> datanya){
            this.dataSelesai = datanya;
        }

        class Holder extends RecyclerView.ViewHolder{

            TextView namas, juduls, deskripsis,ids, times;

            public Holder(View itemView) {
                super(itemView);

                namas = (TextView)itemView.findViewById(R.id.froms);
                juduls= (TextView)itemView.findViewById(R.id.txt_primarys);
                deskripsis = (TextView)itemView.findViewById(R.id.txt_secondarys);
                times = (TextView) itemView.findViewById(R.id.timestamps);
                ids = (TextView)itemView.findViewById(R.id.ids);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }

        }

        @Override
        public SetAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_selesai, parent, false);
            SetAdapter.Holder hol = new SetAdapter.Holder(v);
            return hol;

        }

        @Override
        public void onBindViewHolder(SetAdapter.Holder holder, final int position) {

            holder.namas.setText(dataSelesai.get(position).namas);
            holder.juduls.setText(dataSelesai.get(position).titles);
            holder.deskripsis.setText(dataSelesai.get(position).desks);
            holder.ids.setText(dataSelesai.get(position).ids);
            holder.times.setText(dataSelesai.get(position).getTimestamps);
            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    Intent views = new Intent(getContext(), ViewLapSelesai.class);

                    views.putExtra("id", dataSelesai.get(position).ids);
                    views.putExtra("pelakunya", dataSelesai.get(position).pelakus);
                    views.putExtra("emails", dataSelesai.get(position).emails);
                    views.putExtra("websites", dataSelesai.get(position).websites);
                    views.putExtra("nohps", dataSelesai.get(position).nohs);
                    views.putExtra("juduls", dataSelesai.get(position).titles);
                    views.putExtra("kets", dataSelesai.get(position).desks);
                    views.putExtra("statuss", dataSelesai.get(position).statuss);
                    views.putExtra("picts",dataSelesai.get(position).picts);
                    startActivity(views);
                }
            });

        }

        @Override
        public int getItemCount() {
            return this.dataSelesai.size();
        }
    }
}