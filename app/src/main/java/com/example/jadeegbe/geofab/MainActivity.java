package com.example.jadeegbe.geofab;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.recognition.packets.Nearable;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private String scanId;
    private static final String TAG = "BeaconID";
    public TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.nextActivity);

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setForegroundScanPeriod(500,0);
        beaconManager.setBackgroundScanPeriod(500,0);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startNearableDiscovery();
            }
        });

        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> nearables) {
                for (Nearable nearable : nearables){
                    if (nearable.identifier.equals("6cf71fa481a5ae42")) {
                        scanId = String.valueOf(nearable.rssi);
                        textView.setText(scanId);
                        Log.i(TAG, String.valueOf(scanId));
                    }
                }
            }
        });

        EstimoteSDK.initialize(this, "Nearables", "09876");

    }

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.startNearableDiscovery();
    }

    @Override
    protected void onStop() {
        super.onStop();
        beaconManager.stopNearableDiscovery();
    }
}