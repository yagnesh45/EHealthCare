package com.project.designeng.e_healthcare;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.project.designeng.e_healthcare.broadcast_receivers.TimerBroadcastReceiver;

import java.util.Calendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class TimerDialog extends DialogFragment {
    private static final String TAG = "Timer Dialog";
    TimePicker timePicker;
    int hour, minute;
    String message;
    TextInputEditText messageEditText;
    private Toolbar toolbar;

    static void display(FragmentManager fragmentManager) {
        TimerDialog timerDialog = new TimerDialog();
        timerDialog.show(fragmentManager, TAG);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_timer, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        timePicker = view.findViewById(R.id.time_picker);
        messageEditText = view.findViewById(R.id.message);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i; minute = i1;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerDialog.this.dismiss();
                Toast.makeText(getContext(), "Timer Discarded",Toast.LENGTH_LONG).show();
            }
        });
        toolbar.setTitle("Pick a Time");
        toolbar.inflateMenu(R.menu.timer_dialog);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                message = messageEditText.getText().toString();
                Calendar alarmStartTime = Calendar.getInstance();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                }
                alarmStartTime.set(Calendar.HOUR_OF_DAY, hour);
                alarmStartTime.set(Calendar.MINUTE, minute);
                alarmStartTime.set(Calendar.SECOND, 1);
                Intent notifyIntent = new Intent(getContext(), TimerBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast
                        (getContext(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                if (alarmManager != null)
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  alarmStartTime.getTimeInMillis(),
                            1000 * 60 * 60 * 24, pendingIntent);
                Toast.makeText(getContext(), "Time selected:" + hour + ":" + minute + "\n Message with timer:" + message, Toast.LENGTH_LONG).show();
                // for demo
                // createNotification();
                // one code remaining to add here --- add this timer to grid view of currently running timers
                TimerDialog.this.dismiss();
                return true;
            }
        });
    }

    private void createNotification() {
        Intent intent = new Intent(getContext(), ForPatientActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "TimerNotification")
                .setSmallIcon(R.drawable.round_face_black_18)
                .setContentTitle("Timer Notification")
                .setContentText("This is notification that your timer has expired")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);
        Toast.makeText(getContext(), "Notification created", Toast.LENGTH_LONG).show();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(3, builder.build());
    }
}