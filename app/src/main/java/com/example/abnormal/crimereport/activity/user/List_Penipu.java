package com.example.abnormal.crimereport.activity.user;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.activity.admin.DaftarWebSite;
import com.example.abnormal.crimereport.activity.admin.ViewWeb;
import com.example.abnormal.crimereport.activity.admin.callback.PenipuCallBack;
import com.example.abnormal.crimereport.activity.user.callback.CallBackPenipuan;
import com.example.abnormal.crimereport.helper.DividerItemDecoration;
import com.example.abnormal.crimereport.model.Web;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abnormal on 28/01/18.
 */

public class List_Penipu extends android.support.v4.app.Fragment {

    private Toolbar toolbarproses;
    RequestQueue requestQueue;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_penipu,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*toolbarproses = (Toolbar) getActivity().findViewById(R.id.toolbarProses);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarproses);
*/
        requestQueue = Volley.newRequestQueue(getContext());

        getActivity().setTitle("Web Penipu");

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclemana = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclemana);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        ambildatanya();
    }

    private void ambildatanya() {

        CallBackPenipuan penipuCallBack = new CallBackPenipuan(getContext());
        penipuCallBack.PenipuanCallBack(new CallBackPenipuan.PenipuanBack() {
            @Override
            public void hasil(String hasil) {
                List<Web> list = new ArrayList<>();

                try {

                    JSONObject jsonObject = new JSONObject(hasil);
                    JSONArray jsonArray = jsonObject.getJSONArray("hasil");

                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(a);
                        Web webb = new Web();
                        webb.webId = jsonObj.getString("id");
                        webb.webJudul = jsonObj.getString("si_title");
                        webb.webHost = jsonObj.getString("si_host");
                        webb.full_link = jsonObj.getString("si_full_url");
                        webb.webStatus = jsonObj.getString("si_status");
                        webb.webDate = jsonObj.getString("si_date");
                        list.add(webb);
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                List_Penipu.SetAdapter adapters = new SetAdapter(list);
                recyclerView.setAdapter(adapters);
            }
        });
    }



    class SetAdapter extends RecyclerView.Adapter<List_Penipu.SetAdapter.Holder>{

        List<Web> data;
        SetAdapter(List<Web> datanya){
            this.data = datanya;
        }

        public class Holder extends RecyclerView.ViewHolder {

            TextView title, host, idnya;

            public Holder(View itemView) {
                super(itemView);

                title = (TextView)itemView.findViewById(R.id.Title);
                host = (TextView) itemView.findViewById(R.id.Link);
                idnya = (TextView) itemView.findViewById(R.id.idweb);
            }
        }

        @Override
        public List_Penipu.SetAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_web, parent, false);
            List_Penipu.SetAdapter.Holder hol = new SetAdapter.Holder(v);
            return hol;
        }

        @Override
        public void onBindViewHolder(SetAdapter.Holder holder, final int position) {
            holder.title.setText(Html.fromHtml(data.get(position).webJudul).toString().replace("\n","").trim());
            holder.host.setText(Html.fromHtml(data.get(position).full_link).toString().replace("\n","").trim());
            holder.idnya.setText(data.get(position).webId);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),View_Penipu .class);
                    intent.putExtra("id", data.get(position).webId);
                    intent.putExtra("judul", data.get(position).webJudul);
                    intent.putExtra("host", data.get(position).webHost);
                    intent.putExtra("full_link", data.get(position).full_link);
                    intent.putExtra("status", data.get(position).webStatus);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }

    }


}
