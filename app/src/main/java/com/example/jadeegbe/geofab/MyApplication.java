package com.example.jadeegbe.geofab;

import android.app.Application;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.service.BeaconManager;

import java.util.UUID;

public class MyApplication extends Application implements Runnable{
    private BeaconManager mBeaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        /*mBeaconManager = new BeaconManager(getApplicationContext());

        mBeaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                mBeaconManager.
            }
        });*/
    }

    @Override
    public void run() {
        
    }
}
