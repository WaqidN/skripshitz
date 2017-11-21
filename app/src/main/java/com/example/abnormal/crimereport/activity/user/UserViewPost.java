package com.example.abnormal.crimereport.activity.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.example.abnormal.crimereport.R;

public class UserViewPost extends AppCompatActivity {

    TextView judulP,isiP,dateP;
    private String titleP,contentP,datepostP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_view_post);

        getSupportActionBar().setTitle("View Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        judulP = (TextView) findViewById(R.id.viewTitle);
        isiP = (TextView) findViewById(R.id.viewIsi);
        dateP = (TextView) findViewById(R.id.viewDate);

        titleP = getIntent().getStringExtra("judulnya");
        contentP = getIntent().getStringExtra("isinya");
        datepostP = getIntent().getStringExtra("date");

        judulP.setText(titleP);
        isiP.setText(Html.fromHtml(contentP).toString().replace("\n","").trim());
        dateP.setText(datepostP);

    }
}
