package com.example.abnormal.crimereport.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.abnormal.crimereport.activity.admin.callback.PostCallBack;
import com.example.abnormal.crimereport.model.PostView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends Fragment {

    private RecyclerView mRecyclerView;
    private static String LOG_TAG = "PostActivity";
    RequestQueue requestQueue;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_post,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getContext());

        getActivity().setTitle("Berita");

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclemana = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(recyclemana);
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layoutP);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.newPost);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), NewPOst.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        ambildatanya();
    }

    private void ambildatanya() {

        PostCallBack pc = new PostCallBack(getContext());

        pc.PostCallBack(new PostCallBack.PostBack() {
            @Override
            public void hasil(String hasil) {

                List<PostView> list = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(hasil);
                    JSONArray jsonArray = jsonObject.getJSONArray("hasil");


                    for (int a = 0; a<jsonArray.length(); a++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(a);
                        PostView post = new PostView();
                        post.idPost = jsonObject1.getString("id");
                        post.titlePost = jsonObject1.getString("post_title");
                        post.contetPost = jsonObject1.getString("post_content");
                        post.datePost = jsonObject1.getString("post_date");
                        list.add(post);
                    }


                } catch (Exception e) {

                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                SetAdapter adapter = new SetAdapter(list);
                mRecyclerView.setAdapter(adapter);
            }
        });
    }

    class SetAdapter extends RecyclerView.Adapter<SetAdapter.Holder>{

        List<PostView> datanya;
        SetAdapter(List<PostView> datanya){
            this.datanya = datanya;
        }

        class Holder extends RecyclerView.ViewHolder{

            TextView judulnya, deskripsi,time, idpotnya;

            public Holder(View itemView) {
                super(itemView);

                judulnya = (TextView)itemView.findViewById(R.id.judulP);
                deskripsi = (TextView)itemView.findViewById(R.id.isiB);
                idpotnya = (TextView)itemView.findViewById(R.id.idPost);

            }

        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row, parent, false);
            SetAdapter.Holder hol = new Holder(v);
            return hol;

        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {

            holder.judulnya.setText(datanya.get(position).titlePost);
            holder.deskripsi.setText(datanya.get(position).contetPost);
            //holder.time.setText(datanya.get(position).datePost);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), ViewPost.class);
                    intent.putExtra("idnya", datanya.get(position).idPost);
                    intent.putExtra("judulnya",datanya.get(position).titlePost);
                    intent.putExtra("isinya", datanya.get(position).contetPost);
                    intent.putExtra("date", datanya.get(position).datePost);
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
