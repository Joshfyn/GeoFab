package com.example.jadeegbe.geofab;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.recognition.packets.Nearable;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

public class MyApplication extends Application implements Runnable{
    private BeaconManager beaconManager;
    boolean motionVAl;
    private static final String TAG = "BeaconID";

    private Context mcontext;
    public String RssiVAL;
    public String EstMoving;




    @Override
    public void onCreate() {
        super.onCreate();


        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setForegroundScanPeriod(500,0);
        beaconManager.setBackgroundScanPeriod(500,0);
        EstimoteSDK.initialize(this, "fablab-monitor-gxs", "83a80186bf2d8fd3204757256f889d46");

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
                        RssiVAL = String.valueOf(nearable.rssi);
                        MainActivity.RSSI_value_from_MyApplication = RssiVAL;
                        Log.i(TAG, String.valueOf(RssiVAL));

                    }

                    if (nearable.identifier.equals("2efe3b930fd5f6d2")) {
                        motionVAl = nearable.isMoving;
                        if (motionVAl == true){
                            EstMoving = "It is moving";
                        }
                        else {
                            EstMoving = "It is still";
                        }
                        MainActivity.MOVING_value_from_MyApplication = EstMoving;
                        Log.i(TAG, String.valueOf(motionVAl));

                    }
                }
            }
        });







    }

    @Override
    public void run() {
        /*((MainActivity)mcontext).updateRSSI(RssiVAL);
        ((MainActivity)mcontext).updateMoving(EstMoving);*/

        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
