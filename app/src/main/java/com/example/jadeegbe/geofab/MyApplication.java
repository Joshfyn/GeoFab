package com.example.jadeegbe.geofab;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.recognition.packets.Nearable;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

public class MyApplication extends Application implements Runnable {
    public String RssiVAL;
    public String EstMoving;
    public String xAcceleraTion, yAcceleraTion, zAcceleraTion, xyzAcceleraTion;
    public String[] itemsNames;
    boolean motionVAl;
    long Timestamp;
    String estimoteIdentifier;
    int[] rssiEstimate;
    FabDatabaseHelper fabDatabaseHelper;
    String A;
    private BeaconManager beaconManager;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();



        SharedPreferences sharedPref = getSharedPreferences("identifier", Context.MODE_PRIVATE);
        A = sharedPref.getString("key1", "");



        fabDatabaseHelper = new FabDatabaseHelper(this, "estimoteNearable.db");


        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setForegroundScanPeriod(10000, 5000);
        beaconManager.setBackgroundScanPeriod(10000, 5000);
        EstimoteSDK.initialize(this, "adeegbejoshua-gmail-com-s--5w4", "31d16a9a2f44d55889c772a592d573cd");

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
                    if (nearable.identifier.equals("e286d152a894bc2e")) {
                        Timestamp = System.currentTimeMillis();
                        estimoteIdentifier = nearable.identifier;
                        EstimoteReadings.Identifier_from_MyApplication = estimoteIdentifier;
                        RssiVAL = String.valueOf(nearable.rssi);
                        rssiEstimate = new int[]{nearable.rssi};
                        EstimoteReadings.RSSI_value_from_MyApplication = RssiVAL;
                        Log.i("rssivalue", String.valueOf(RssiVAL));


                    }

                    //Log.i("The distance is", String.valueOf(getDistance(rssiEstimate)));


                    if (nearable.identifier.equals("55a6742318d3c585")) {
                        motionVAl = nearable.isMoving;
                        if (motionVAl == true) {
                            EstMoving = "It is moving";
                        } else {
                            EstMoving = "It is still";
                        }
                        EstimoteReadings.MOVING_value_from_MyApplication = EstMoving;
                        Log.i("motion", String.valueOf(motionVAl));

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
        /*((EstimoteReadings)mcontext).updateRSSI(RssiVAL);
        ((EstimoteReadings)mcontext).updateMoving(EstMoving);*/

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
