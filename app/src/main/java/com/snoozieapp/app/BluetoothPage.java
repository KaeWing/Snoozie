package com.snoozieapp.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class BluetoothPage extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueIv;
    Button mOnBtn, mOffBtn, mDiscoverBtn, mPairedBtn;

    BluetoothAdapter mBlueAdapter;


    // Debug UI elements
    private TextView debug;

    // Bluetooth Service
    private BluetoothService mBluetoothService = null;
    private Button bluetoothDebugBtn;
    private Button bluetoothTestBtn;
    boolean debugEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_page);


        bluetoothDebugBtn = (Button) findViewById(R.id.debugBtn);
        bluetoothDebugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDebugMenu();
            }
        });

        bluetoothTestBtn = (Button) findViewById(R.id.testBtn);
        bluetoothTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTestPage();
            }
        });

        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mPairedTv = findViewById(R.id.pairedTv);
        mBlueIv = findViewById(R.id.bluetoothIv);
        mOnBtn = findViewById(R.id.onBtn);
        mOffBtn = findViewById(R.id.offBtn);
        mDiscoverBtn = findViewById(R.id.discoverableBtn);
        mPairedBtn = findViewById(R.id.pairedBtn);
        debug = findViewById(R.id.debug_menu);


        if (!debugEnabled)
        {
            debug.setVisibility(View.INVISIBLE);
        }

        debug.setText("butt soup! -Troy Barnes, star quarterback at riverside high. haha im so funny and quirky please help im dying inside this is not a joke");

        //adapter
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();


//        // Check if Bluetooth is available
//        if (mBlueAdapter == null) {
//            mStatusBlueTv.setText("Bluetooth is not available");
//        }
//        else {
//            mStatusBlueTv.setText("Bluetooth is available");
//        }

        // set image according to Bluetooth Status
        if(mBlueAdapter.isEnabled()) {
            mBlueIv.setImageResource(R.drawable.ic_action_on);
        }
        else {
            mBlueIv.setImageResource(R.drawable.ic_action_off);
        }

        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mBlueAdapter.isEnabled()) {
                    showToast("Turning on Bluetooth...");
                    // Intent to on Bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                }
                else {
                    showToast("Bluetooth is on");
                }
            }
        });

        mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mBlueAdapter.isDiscovering()) {
                    showToast("Making Devices Discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });

        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBlueAdapter.isEnabled()) {
                    mBlueAdapter.disable();
                    showToast("Turning Bluetooth Off");
                    mBlueIv.setImageResource(R.drawable.ic_action_off);
                }
                else {
                    showToast("Bluetooth is off");
                }
            }
        });

        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBlueAdapter.isEnabled()) {
                    mPairedTv.setText("Paired Devices");
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                    for (BluetoothDevice device: devices) {
                        mPairedTv.append("\nDevice: " + device.getName() + "," + device);
                    }
                }
                else {
                    // Bluetooth is off so we can't get paired devices
                    showToast("Turn on Bluetooth to get paired devices");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK) {
                    //Bluetooth is on
                    mBlueIv.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is on");
                }
                else {
                    // User denied to turn Bluetooth on
                    showToast("Could not connect to Bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void openDebugMenu() {
        Intent intent = new Intent(this, BluetoothDebugPage.class);
        startActivity(intent);
    }

    public void openTestPage() {
        Intent intent = new Intent(this, BluetoothTest.class);
        startActivity(intent);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
