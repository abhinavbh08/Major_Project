package com.example.abhinav.notification_system;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class msg_service extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
/*
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(intent)
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(123, notification);



        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, notification);

        String myCustomKey = remoteMessage.getData().get("my_custom_key");
        Log.d("LOL","What is going on here!");
        Log.d("LOL",remoteMessage.getData().get("click_action"));
        Log.d("LOL",myCustomKey);
*/

        Intent notificationIntent = new Intent(getApplicationContext(), MapsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("lat",remoteMessage.getData().get("lat"));
        bundle.putString("lon",remoteMessage.getData().get("lon"));
        notificationIntent.putExtras(bundle);

/*
        notificationIntent.putExtra("Latitude", remoteMessage.getData().get("lat"));
        notificationIntent.putExtra("Longitude",remoteMessage.getData().get("lon"));
*/
        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentIntent(intent)
                .setAutoCancel(true);

        Log.d("LOL",remoteMessage.getData().get("body"));


        Notification notification = builder.getNotification();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(R.drawable.notification_template_icon_bg, notification);

        /*
        if(remoteMessage.getData().containsKey("click_action")){

              ClickActionHelper.startActivity(remoteMessage.getData().get("click_action"), null, this);
        }

        */

//        Log.d("LOL",remoteMessage.getData().get("title"));
  //      Log.d("LOL",remoteMessage.getData().get("body"));
    }
}
