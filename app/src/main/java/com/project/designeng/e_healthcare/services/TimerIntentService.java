package com.project.designeng.e_healthcare.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.project.designeng.e_healthcare.ForPatientActivity;
import com.project.designeng.e_healthcare.R;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TimerIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;
    public TimerIntentService() {
        super("TimerIntentService");
        Log.e("ServiceStatus","Running");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent1) {
        Intent intent = new Intent(this, ForPatientActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "TimerNotification")
                .setSmallIcon(R.drawable.round_face_black_18)
                .setContentTitle("Medicine Reminder")
                .setContentText("You should take your Medicine on scheduled time")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ServiceStatus","Destroyed");
    }
}