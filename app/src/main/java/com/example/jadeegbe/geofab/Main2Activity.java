package com.example.jadeegbe.geofab;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.recognition.packets.Nearable;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    public BeaconManager mBeaconManager;
    public String mScanID;
    public String mEstimoteID;
    public Context mContext;

    public static ArrayList<String> myitems;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = (ListView) findViewById(R.id.my_device);
        myitems =new ArrayList<>();
        //nearablesDetectors.onCreate();

        EstimoteSDK.initialize(this, "care-estimotes-b0n", "6a749930b80298c5dbb16af6c9709da6");
        mBeaconManager = new BeaconManager(getApplicationContext());
        mBeaconManager.setForegroundScanPeriod(500,0);
        mBeaconManager.setBackgroundScanPeriod(500,0);


        mBeaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> nearables) {
                for (Nearable nearable: nearables){
                    mEstimoteID = nearable.identifier;
                    myitems.add(mEstimoteID);
                    ListAdapter listAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, myitems);     //this object converts an array to list items
                    listView.setAdapter(listAdapter); //this coverts the array to display in the list

                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mBeaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                mBeaconManager.startNearableDiscovery();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBeaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                mScanID = mBeaconManager.startNearableDiscovery();
            }
        });
    }

    onSr
}


