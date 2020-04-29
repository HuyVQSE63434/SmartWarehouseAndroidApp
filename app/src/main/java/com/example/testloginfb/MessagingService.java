package com.example.testloginfb;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.testloginfb.activities.ShowNotificationDataActivity;
import com.example.testloginfb.models.Detail;
import com.example.testloginfb.models.Transaction;
import com.example.testloginfb.models.TransactionMaterialAmount;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class MessagingService extends FirebaseMessagingService {
    public MessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent intent = new Intent(this, ShowNotificationDataActivity.class);
        Map<String,String> jsonData = remoteMessage.getData();
        Gson gson = new Gson();
        Type mType = new TypeToken<List<TransactionMaterialAmount>>(){}.getType();
        Transaction notiTransaction = new Transaction();
        try{
            notiTransaction = new Transaction(Integer.parseInt(jsonData.get("id")),gson.fromJson(jsonData.get("exchangeStore"), Detail.class)
                    ,gson.fromJson(jsonData.get("store"), Detail.class),jsonData.get("time"),gson.fromJson(jsonData.get("staff"), Detail.class)
                    ,gson.fromJson(jsonData.get("transactionType"), Detail.class)
                    ,gson.fromJson(jsonData.get("status"), Detail.class), (List<TransactionMaterialAmount>) gson.fromJson(jsonData.get("detail"), mType));
        }catch (Exception e){
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            notiTransaction.setDateTime(dateFormat.parse(notiTransaction.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        intent.putExtra("data", (Serializable) notiTransaction);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String chanelId = "chanel1";

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    chanelId,
                    "Chanel 1",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,chanelId)
                .setSmallIcon(R.mipmap.ic_alert)
                .setContentTitle("Có giao dịch mới")
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT))
                /*.addAction(new NotificationCompat.Action(
                        R.mipmap.ic_alert,"Xem thêm", PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
                ))*/;

        notificationManager.notify(0, builder.build());

    }
}
