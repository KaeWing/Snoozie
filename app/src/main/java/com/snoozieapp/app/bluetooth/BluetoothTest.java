package com.snoozieapp.app.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.snoozieapp.app.R;
import com.snoozieapp.app.track.Track;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class BluetoothTest extends Activity {
    TextView myLabel;
    EditText myTextbox;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    TextView box;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_test);

        Button openButton = (Button)findViewById(R.id.openBtn);
        Button testButton = (Button)findViewById(R.id.loadTestBtn);
        Button closeButton = (Button)findViewById(R.id.closeBtn);
        //myLabel = (TextView)findViewById(R.id.label);
        box = (TextView) findViewById(R.id.box);
        Button backButton = (Button)findViewById(R.id.backBtn);

        //Open Button
        openButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    findBT();
                    openBT();
                }
                catch (IOException ex) { }
            }
        });

        //Load Test Data Button
        testButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                readData();
            }
        });

        //Close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    closeBT();
                }
                catch (IOException ex) { }
            }
        });

        //Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBluetoothPage();
            }
        });
    }

    public void openBluetoothPage() {
        Intent intent = new Intent(BluetoothTest.this, BluetoothPage.class);
        startActivity(intent);
    }

    void findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null) {
        }

        if(!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0) {
            for(BluetoothDevice device : pairedDevices) {

                if(device.getName().equals("ESP32Test")) {  // We need to change this to match the name of the device
                    mmDevice = device;
                    break;
                }
            }
        }
    }

    void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID
        if (mmDevice != null)
        {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
            beginListenForData();
        }
        else
        {
            box.setText("DK: null device... Testing using loop of vars");
            dataSimulation();
        }
    }

    void beginListenForData() {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024]; // This will need to be adjusted to our data specifications
        workerThread = new Thread(new Runnable() {
            public void run() {
                while(!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = mmInputStream.available();
                        if(bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++) {
                                byte b = packetBytes[i];
                                if(b == delimiter) { // This code has its delimiter to a newline, we might want to instead limit it to an amount of data received?
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        public void run() {
                                            box.setText("DK: " + data);

                                            String [] dataArray = data.split(": ");

                                            switch(dataArray[0])
                                            {
                                                case "Light Sensor value":
                                                    BluetoothDebug.printLightData(dataArray[1]);
                                                    Track.graphLight(Instant.now(), dataArray[1]);
                                                case "pressureValue":
                                                    BluetoothDebug.printPressureData(dataArray[1]);
                                                case "tempHumidValue":
                                                    BluetoothDebug.printTempData(dataArray[1]);
                                                case "accel":
                                                    BluetoothDebug.printGyroData(dataArray[1]);
                                                    Track.graphMotion(Instant.now(), dataArray[1]);
                                            }
                                        }
                                    });
                                }
                                else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    void sendData() throws IOException {
        String msg = myTextbox.getText().toString();
        msg += "\n";
        //mmOutputStream.write(msg.getBytes());
        mmOutputStream.write('A');
    }

    void closeBT() throws IOException { // Crashes on Emulator since it can't connect?  // java.lang.NullPointerException: Attempt to invoke virtual method 'void java.io.OutputStream.close()' on a null object reference
        stopWorker = true;
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
    }

    private void showMessage(String theMsg) {
        Toast msg = Toast.makeText(getBaseContext(),
                theMsg, (Toast.LENGTH_LONG)/160);
        msg.show();
    }

    private void dataSimulation() {
        workerThread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                while(!Thread.currentThread().isInterrupted()) {

                            for(int i = 0; i < Track.lightRead.size(); i++)
                            {
                                BluetoothDebug.printMicData("N/A");

                                BluetoothDebug.printPressureData(Track.pressureRead.get(i));
                                Track.graphMotion(Instant.now(), Track.pressureRead.get(i));

                                BluetoothDebug.printGyroData("X: " + Track.xRead.get(i) + "    Y: " + Track.yRead.get(i) + "    Z:" + Track.zRead.get(i));

                                BluetoothDebug.printLightData(Track.lightRead.get(i));
                                Track.graphLight(Instant.now(), Track.lightRead.get(i));

                                BluetoothDebug.printTempData("N/A");

                                try
                                {
                                    Thread.sleep(200);
                                }
                                catch (InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                }
            }
        });

        workerThread.start();
    }

    private void readData() {
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line  = "";

        try {
            while ( (line = reader.readLine()) != null) {
                //Log.d("MyActivity", "Line: " + line);
                String[] tokens = line.split(",");
                //System.out.println("Light: " + tokens[0] + " Pressure: " + tokens[1] + " X: " + tokens[2] + " Y: " + tokens[3] + " Z: " + tokens[4]);

                if (!tokens[0].equals("Light"))
                {
                    Track.lightRead.add(tokens[0]);
                    Track.pressureRead.add(tokens[1]);
                    Track.xRead.add(tokens[2]);
                    Track.yRead.add(tokens[3]);
                    Track.zRead.add(tokens[4]);
                }
            }
        } catch (IOException e) {
            Log.wtf("MyActivity,", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }
}