package com.example.jadeegbe.geofab;

import android.app.Application;
import android.util.Log;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.recognition.packets.Nearable;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

public class MyApplication extends Application implements Runnable {

    long Timestamp;

    //Nearables for calculating proximity
    public String RssiVAL;
    String NearIdentifierDist;
    int[] rssiEstimate;


    public String[] itemsNames;

    //Nearables for calculating vibration intensity
    String NearIdentifierAccel;
    public String EstMoving;
    public String xAcceleraTion, yAcceleraTion, zAcceleraTion, xyzAcceleraTion;
    boolean motionVAl;



    FabDatabaseHelper fabDatabaseHelper;
    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();




        fabDatabaseHelper = new FabDatabaseHelper(this, "estimoteNearable.db");


        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setForegroundScanPeriod(10000, 5000);
        beaconManager.setBackgroundScanPeriod(10000, 5000);
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
                for (Nearable nearable : nearables) {
                    Timestamp = System.currentTimeMillis();

                    if (nearable.identifier.equals("6cf71fa481a5ae42")) {
                        NearIdentifierDist = nearable.identifier;
                        EstimoteReadings.Identifier_from_MyApplication = NearIdentifierDist;
                        RssiVAL = String.valueOf(nearable.rssi);
                        rssiEstimate = new int[]{nearable.rssi};
                        EstimoteReadings.RSSI_value_from_MyApplication = RssiVAL;
                        Log.i("rssivalue", String.valueOf(RssiVAL));


                    }

                    //Log.i("The distance is", String.valueOf(getDistance(rssiEstimate)));


                    if (nearable.identifier.equals("2efe3b930fd5f6d2")) {
                        motionVAl = nearable.isMoving;
                        if (motionVAl == true) {
                            EstMoving = "It is moving";
                        } else {
                            EstMoving = "It is still";
                        }
                        EstimoteReadings.MOVING_value_from_MyApplication = EstMoving;
                        Log.i("motion", String.valueOf(motionVAl));

                        NearIdentifierAccel = nearable.identifier;

                        //TODO: finish up the acceleration for Detecting vibration
                        double xAcceln = nearable.xAcceleration;
                        double yAcceln = nearable.yAcceleration;
                        double zAcceln = nearable.zAcceleration;
                        double xyzAceeln = Math.sqrt((xAcceln * xAcceln) + (yAcceln * yAcceln) + (zAcceln * zAcceln));

                        xAcceleraTion = String.valueOf(xAcceln);
                        yAcceleraTion = String.valueOf(yAcceln);
                        zAcceleraTion = String.valueOf(zAcceln);
                        xyzAcceleraTion = String.valueOf(xyzAceeln);

                        EstimoteReadings.XACCELERATION = xAcceleraTion;
                        EstimoteReadings.YACCELERATION = yAcceleraTion;
                        EstimoteReadings.ZACCELERATION = zAcceleraTion;
                        EstimoteReadings.XYZACCELERATION = xyzAcceleraTion;


                    }


                }
            }
        });


    }

    @Override
    public void run() {

        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public double getDistance(int[] array) {
        int total = 0;
        int txPower = -65; //hard coded power value. Usually ranges between -59 to -65

        if (array == null) {
            return -1;
        } else {

            for (int i = 0; i < array.length; i++) {
                total = total + array[i];

            }

            int averageRSSI = total / array.length;

            if (averageRSSI == 0) {
                return -1;
            }

            double ratio = averageRSSI * 1 / txPower;
            if (ratio < 1.0) {
                return Math.pow(ratio, 10);
            } else {
                double distance = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
                return distance;
            }
        }


    }


}
