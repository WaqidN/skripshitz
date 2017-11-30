package com.example.abnormal.crimereport.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.abnormal.crimereport.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by abnormal on 14/11/17.
 */

public class FcmServiceMessage extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "Form : "+remoteMessage.getFrom());
        if (remoteMessage.getData().size()>0){
            Log.d(TAG, "Message data payload: " + remoteMessage.getFrom());
        }
        if (remoteMessage.getNotification() != null){
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        String pesan = remoteMessage.getData().get("message");
        String judul = remoteMessage.getData().get("title");

        kirim(pesan,judul);
    }

    private void kirim(String pesan, String judul){

        Intent intent = null;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_logo);
        builder.setContentTitle(judul);
        builder.setContentText(pesan)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 ,builder.build());
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Log.d("Kirim", "Terkirim");
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);

        Log.d("gagal", e.getMessage());
    }
}
