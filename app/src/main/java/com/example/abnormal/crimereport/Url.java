package com.example.abnormal.crimereport;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by abnormal on 20/07/17.
 */

public class Url {
    public static String HttpUrl = "https://onlinecrimereports.000webhostapp.com/";
    public String uploadDocument(String dataGambar, Context context, Uri uri,
                                 String namauser,String nama,String email,String web,String nohp,
                                 String namaGambar,String title,String keterangan){
        HttpURLConnection conn = null;
        String tipefile = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String respon = null;
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 8024 * 8024;
        try {
            URL url = new URL(HttpUrl+"laporan/newlaporan.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            dos = new DataOutputStream(conn.getOutputStream());
            FileInputStream fileInputStream=null;
            File sourceFile = new File(Environment.getExternalStorageDirectory()+dataGambar);
            fileInputStream = (FileInputStream)
                    context.getContentResolver().openInputStream(uri);
            MimeTypeMap map = MimeTypeMap.getSingleton();
            tipefile = map.getExtensionFromMimeType(context.getContentResolver().getType(uri));
            String param = "type="+tipefile;
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"a_file\";name=\""
                    +dataGambar+"\""+lineEnd+"");

            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            
            dos.writeBytes("Content-Disposition: form-data; name=\"a_nama\""+lineEnd+"");
            dos.writeBytes(lineEnd);
            dos.writeBytes(nama);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"a_website\""+lineEnd+"");
            dos.writeBytes(lineEnd);
            dos.writeBytes(web);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);


            dos.writeBytes("Content-Disposition: form-data; name=\"a_nohp\""+lineEnd+"");
            dos.writeBytes(lineEnd);
            dos.writeBytes(nohp);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);


            dos.writeBytes("Content-Disposition: form-data; name=\"a_title\""+lineEnd+"");
            dos.writeBytes(lineEnd);
            dos.writeBytes(title);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);


            dos.writeBytes("Content-Disposition: form-data; name=\"a_des\""+lineEnd+"");
            dos.writeBytes(lineEnd);
            dos.writeBytes(keterangan);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"a_file\""+lineEnd+"");
            dos.writeBytes(lineEnd);
            dos.writeBytes(namaGambar);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"a_user\""+lineEnd+"");
            dos.writeBytes(lineEnd);
            dos.writeBytes(namauser);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);


            dos.writeBytes("Content-Disposition: form-data; name=\"a_email\""+lineEnd+"");
            dos.writeBytes(lineEnd);
            dos.writeBytes(email);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens);
            fileInputStream.close();
            dos.flush();
            dos.close();
            BufferedReader buf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = buf.readLine()) != null) {
                sb.append(line);
            }
            buf.close();;
            respon = sb.toString();
        }catch (Exception e){
            respon = e.getMessage();
        }
        return respon;
    }


}
