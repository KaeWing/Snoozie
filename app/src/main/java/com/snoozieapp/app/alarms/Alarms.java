package com.snoozieapp.app.alarms;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.snoozieapp.app.R;

import java.util.Calendar;

public class Alarms extends Fragment {

    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private View view;
    private TextView time;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.alarms_page, container, false);
        super.onCreate(savedInstanceState);
        createNotificationChannel();

        Button selectTime = (Button) view.findViewById(R.id.selectTimeBtn);
        Button setAlarm = (Button) view.findViewById(R.id.setAlarmBtn);
        Button cancelAlarm = (Button) view.findViewById(R.id.cancelAlarmBtn);
        time = (TextView) view.findViewById(R.id.selectedTime);


        view.findViewById(R.id.selectTimeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        view.findViewById(R.id.setAlarmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

        view.findViewById(R.id.cancelAlarmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        return view;
    }

    // Used for BEFORE view is created
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void cancelAlarm() {
        Intent intent = new Intent(getView().getContext(), AlarmReceiver.class);
        pendingIntent = pendingIntent.getBroadcast(getView().getContext(), 0, intent, 0);

        if (alarmManager == null) {
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(getView().getContext(), "Alarm Cancelled", Toast.LENGTH_SHORT).show();

    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getView().getContext(), AlarmReceiver.class);
        pendingIntent = pendingIntent.getBroadcast(getView().getContext(), 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(getView().getContext(), "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    private void showTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();

        picker.show(getActivity().getSupportFragmentManager(), "SnoozieApp");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(picker.getHour() > 12) {
                    time.setText(
                            String.format("%02d",(picker.getHour()-12))+" : "+String.format("%02d",picker.getMinute())+" PM"
                    );
                } else {

                    time.setText(
                            String.format("%02d", (picker.getHour())) + " : " + String.format("%02d", picker.getMinute()) + " AM"
                    );
                }

                // Point calendar to time that user selected from time picker
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            }
        });

    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "snoozieReminderChannel";
            String description = "Channel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("SnoozieApp",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Used for AFTER view is created (accessing view elements directly)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}