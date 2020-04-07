package com.project.designeng.e_healthcare.broadcast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.project.designeng.e_healthcare.services.TimerIntentService;

public class TimerBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, TimerIntentService.class);
        Toast.makeText(context, "Service called for timer", Toast.LENGTH_LONG).show();
        Log.e("Service status","Started");
        context.startService(intent1);
    }
}
