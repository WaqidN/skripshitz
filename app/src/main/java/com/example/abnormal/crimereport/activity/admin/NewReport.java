package com.example.abnormal.crimereport.activity.admin;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.abnormal.crimereport.R;
import com.example.abnormal.crimereport.Url;
import com.example.abnormal.crimereport.pojo.Session;

public class NewReport extends ActionBarActivity {

    private Toolbar toolbar;
    private EditText email,web,no,judul,keteragan, nama;
    private Bitmap bitmap;
    private String namaGambar;
    private Uri dataGambar;
    private TextView tmplPict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        getSupportActionBar().setTitle("New Laporan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tmplPict = (TextView)findViewById(R.id.tmplG);
        nama    = (EditText)findViewById(R.id.nr1);
        email   = (EditText)findViewById(R.id.nr2);
        web     = (EditText)findViewById(R.id.nr3);
        no      = (EditText)findViewById(R.id.nr4);
        judul   = (EditText)findViewById(R.id.nr5);
        keteragan= (EditText)findViewById(R.id.nr6);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.icon_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addfile){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"),
                    1);
        }else if(item.getItemId() == R.id.action_user){
            kirimData();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            bitmap      = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
            //Toast.makeText(this, getFileName(data.getData()), Toast.LENGTH_SHORT).show();
            tmplPict.setText(getFileName(data.getData()));
            namaGambar  = getFileName(data.getData());
            dataGambar  = data.getData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getFileName(Uri uri){
        String[] proj = { MediaStore.Images.Media.TITLE };
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        String filename = null;
        if (cursor != null && cursor.getCount() != 0) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
            cursor.moveToFirst();
            filename = cursor.getString(columnIndex);
        }
        return filename;
    }


    public void kirimData(){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                Toast.makeText(getApplicationContext(), "upload gambar", Toast.LENGTH_SHORT).show();

            }

            @Override
            protected String doInBackground(Void... params) {
                String iduser = new Session(getApplicationContext()).getid();
                if(dataGambar != null){
                    return new Url().uploadDocument(dataGambar.toString(),
                            getApplicationContext(),dataGambar,iduser,nama.getText().toString(),email.getText().toString(),
                            web.getText().toString(),no.getText().toString(),namaGambar,
                            judul.getText().toString(),keteragan.getText().toString());

                }else{
                    return new Url().uploadDocument("",
                            getApplicationContext(),null,iduser,nama.getText().toString(),email.getText().toString(),
                            web.getText().toString(),no.getText().toString(),namaGambar,
                            judul.getText().toString(),keteragan.getText().toString());

                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(NewReport.this, "Pesan Terkirim", Toast.LENGTH_SHORT).show();
                Intent hasil = new Intent(NewReport.this, DrawerAdmin.class);
                startActivity(hasil);
                finish();
            }
        }.execute();
    }
}

