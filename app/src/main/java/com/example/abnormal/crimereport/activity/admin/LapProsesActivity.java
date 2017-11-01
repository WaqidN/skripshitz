package com.example.abnormal.crimereport.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.abnormal.crimereport.model.MProses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.abnormal.crimereport.Url.HttpUrl;

/**
 * Created by abnormal on 17/06/17.
 */

public class LapProsesActivity extends Fragment {

    //private List<Message> messageList1;
    private Toolbar toolbarproses;
    //private static Context context;
    RequestQueue requestQueue;
    private RecyclerView recyclerView1;
    private SwipeRefreshLayout mswipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_proses,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbarproses = (Toolbar) getActivity().findViewById(R.id.toolbarProses);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarproses);

        requestQueue = Volley.newRequestQueue(getContext());

        recyclerView1 = (RecyclerView)getActivity().findViewById(R.id.recycler_viewp);
        recyclerView1.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclemana = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(recyclemana);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        mswipeRefreshLayout = (SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_refresh_layoutp);


        ambildatanya();

    }


    private void ambildatanya() {

        String Json = HttpUrl+"crimereport/laporan/lap_proses.php";

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                List<MProses> list = new ArrayList<>();

                Log.e("data", response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("hasil");

                    for (int a = 0; a<jsonArray.length(); a++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(a);
                        MProses proses = new MProses();
                        proses.id = jsonObject1.getString("id");
                        proses.namap = jsonObject1.getString("l_nama");
                        proses.titlep = jsonObject1.getString("l_title");
                        proses.deskp = jsonObject1.getString("l_des");
                        proses.getTimestampp = jsonObject1.getString("l_date");
                        proses.nohp = jsonObject1.getString("l_nohp");
                        proses.emailp = jsonObject1.getString("l_email");
                        proses.websitep = jsonObject1.getString("l_website");
                        proses.statusp = jsonObject1.getString("l_status");
                        list.add(proses);
                    }


                } catch (Exception e) {

                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                SetAdapter adapter = new SetAdapter(list);
                recyclerView1.setAdapter(adapter);
            }


        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest1);
    }

    class SetAdapter extends RecyclerView.Adapter<SetAdapter.Holder>{

        List<MProses> datanya;
        SetAdapter(List<MProses> datanya){
            this.datanya = datanya;
        }

        class Holder extends RecyclerView.ViewHolder{

            TextView namauser, judulnya, deskripsi,idp,timep;

            public Holder(View itemView) {
                super(itemView);

                namauser = (TextView)itemView.findViewById(R.id.fromp);
                judulnya = (TextView)itemView.findViewById(R.id.txt_primaryp);
                deskripsi = (TextView)itemView.findViewById(R.id.txt_secondaryp);
                timep = (TextView)itemView.findViewById(R.id.timestampp);
                idp = (TextView)itemView.findViewById(R.id.idp);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }

        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_proses, parent, false);
            Holder hol = new Holder(v);
            return hol;

        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {

            holder.namauser.setText(datanya.get(position).namap);
            holder.judulnya.setText(datanya.get(position).titlep);
            holder.deskripsi.setText(datanya.get(position).deskp);
            holder.timep.setText(datanya.get(position).getTimestampp);
            holder.idp.setText(datanya.get(position).id);

            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent clik = new Intent(getContext(), ViewLaporan.class);

                    clik.putExtra("id", datanya.get(position).id);
                    clik.putExtra("emailp", datanya.get(position).emailp);
                    clik.putExtra("websitep", datanya.get(position).websitep);
                    clik.putExtra("nohpp", datanya.get(position).nohp);
                    clik.putExtra("judulp", datanya.get(position).titlep);
                    clik.putExtra("ketp", datanya.get(position).deskp);
                    clik.putExtra("statusp", datanya.get(position).statusp);
                    startActivity(clik);
                }
            });

        }

        @Override
        public int getItemCount() {
            return this.datanya.size();
        }
    }
}