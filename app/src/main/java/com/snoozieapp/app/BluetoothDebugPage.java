package com.snoozieapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class BluetoothDebugPage extends AppCompatActivity {

    // UI elements
    private TextView title, micTitle, micData, pressureTitle, pressureData, gyroTitle, gyroData, lightTitle, lightData, tempTitle, tempData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_debug_page);

        title = findViewById(R.id.title);
        micTitle = findViewById(R.id.mic_title);
        micData = findViewById(R.id.mic_data);
        pressureTitle = findViewById(R.id.pressure_title);
        pressureData = findViewById(R.id.pressure_data);
        gyroTitle = findViewById(R.id.gyro_title);
        gyroData = findViewById(R.id.gyro_data);
        lightTitle = findViewById(R.id.light_title);
        lightData = findViewById(R.id.light_data);
        tempTitle = findViewById(R.id.temp_title);
        tempData = findViewById(R.id.temp_data);
    }
}