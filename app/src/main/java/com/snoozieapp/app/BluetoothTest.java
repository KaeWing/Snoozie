package com.snoozieapp.app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        //Button sendButton = (Button)findViewById(R.id.send);
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

        //Send Button
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                try {
//                    sendData();
//                }
//                catch (IOException ex) {
//                    showMessage("SEND FAILED");
//                }
//            }
//        });

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
                                        public void run() {
                                            box.setText("DK: " + data);

                                            BluetoothDebugPage.printMicData(data);

                                            try
                                            {
                                                Thread.sleep(500);
                                            }
                                            catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                            // parse the data
                                            // string -> char array
                                            // char a = array[0]
                                            // switch
                                            // a == 'p'
                                            // pressure
                                            //  ..
                                            //  ..
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
            public void run() {
                while(!Thread.currentThread().isInterrupted()) {

                            for(int i = 0; i < 100; i++)
                            {
                                BluetoothDebugPage.printMicData(Integer.toString(i));
                                BluetoothDebugPage.printPressureData(Integer.toString(i + 1));
                                BluetoothDebugPage.printGyroData(Integer.toString(i + 2));
                                BluetoothDebugPage.printLightData(Integer.toString(i + 3));
                                BluetoothDebugPage.printTempData(Integer.toString(i + 4));

                                try
                                {
                                    Thread.sleep(1000);
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
}