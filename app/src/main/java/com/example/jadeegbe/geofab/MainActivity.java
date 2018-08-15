package com.example.jadeegbe.geofab;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.service.BeaconManager;

import java.sql.Time;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button;
    private BeaconManager mBeaconManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if bluetooth is on
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
        }

        if (!bluetoothAdapter.isEnabled()) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);
            Toast.makeText(getApplicationContext(), "Turn ON Bluetooth to continue using APP", Toast.LENGTH_SHORT).show();

        }


        mBeaconManager = new BeaconManager(getApplicationContext());
        mBeaconManager.setForegroundScanPeriod(500,0);
        mBeaconManager.setBackgroundScanPeriod(500,0);
        EstimoteSDK.initialize(this, "fablab-monitor-gxs", "83a80186bf2d8fd3204757256f889d46");

        mBeaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                mBeaconManager.startNearableDiscovery();
            }
        });


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReadings();
            }
        });





    }

    public void openReadings() {
        Intent intent = new Intent(this, EstimoteReadings.class);
        startActivity(intent);
    }




}