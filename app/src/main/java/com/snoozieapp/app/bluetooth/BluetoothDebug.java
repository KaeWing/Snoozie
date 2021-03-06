package com.snoozieapp.app.bluetooth;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.snoozieapp.app.R;


public class BluetoothDebug extends AppCompatActivity {

    // UI elements
    private static TextView title, micTitle, micData, pressureTitle, pressureData, gyroTitle, gyroData, lightTitle, lightData, tempTitle, tempData;
    private static char c1, c2, c3, c4, c5 = c4 = c3 = c2 = c1 = 'x';

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

    public static void printMicData(String data) {
        if(micData != null)
        {
            StringBuilder sb = new StringBuilder();

            sb.append(c1);
            sb.append("\t");
            sb.append(data);

            micData.setText(sb.toString());
        }

        if (c1 == 'x')
        {
            c1 = 'o';
        }
        else
        {
            c1 = 'x';
        }
    }

    public static void printPressureData(String data) {
        if(pressureData != null)
        {
            StringBuilder sb = new StringBuilder();

            sb.append(c2);
            sb.append("\t");
            sb.append(data);

            pressureData.setText(sb.toString());
        }

        if (c2 == 'x')
        {
            c2 = 'o';
        }
        else
        {
            c2 = 'x';
        }
    }

    public static void printGyroData(String data) {
        if(gyroData != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(c3);
            sb.append("\t");

            // Split multiple tokens
            String [] dataValues = data.split(",");

            for (int i = 0; i < dataValues.length; i++)
            {
                sb.append((char) ('X' + i));
                sb.append(": ");
                sb.append(dataValues[i]);
                sb.append("\t\t");
            }

            gyroData.setText(sb.toString());
        }

        if (c3 == 'x')
        {
            c3 = 'o';
        }
        else
        {
            c3 = 'x';
        }
    }

    public static void printLightData(String data) {
        if(lightData != null)
        {
            StringBuilder sb = new StringBuilder();

            sb.append(c4);
            sb.append("\t");
            sb.append(data);

            lightData.setText(sb.toString());
        }

        if (c4 == 'x')
        {
            c4 = 'o';
        }
        else
        {
            c4 = 'x';
        }
    }

    public static void printTempData(String data) {
        if(tempData != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(c5);
            sb.append("\t");

            // Split multiple tokens
            String [] dataValues = data.split(",");

            for (int i = 0; i < dataValues.length; i++)
            {
                sb.append((char) ('T' - (i * 12)));
                sb.append(": ");
                sb.append(dataValues[i]);
                sb.append("\t\t");
            }

            tempData.setText(sb.toString());
        }

        if (c5 == 'x')
        {
            c5 = 'o';
        }
        else
        {
            c5 = 'x';
        }
    }
}