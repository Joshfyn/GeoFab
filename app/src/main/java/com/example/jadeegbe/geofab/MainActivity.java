package com.example.jadeegbe.geofab;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.recognition.packets.Nearable;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.estimote.coresdk.common.config.EstimoteSDK.getAppId;
import static com.estimote.coresdk.common.config.EstimoteSDK.getAppToken;

public class MainActivity extends AppCompatActivity {
    private BeaconManager beaconManager;
    private BeaconRegion region;
    private static final Map<String, List<String>> PLACES_BY_BEACONS;
    ListView listView;
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.my_listview);
        textView = (TextView) findViewById(R.id.nextActivity);

        EstimoteSDK.initialize(getApplicationContext(), "1", "0");
        EstimoteSDK.enableDebugLogging(true);

        beaconManager = new BeaconManager(this);

        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    List<String> places = placesNearBeacon(nearestBeacon);

                    Log.d("Airport", "Nearest places: " + places);
                    ListAdapter listAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, places);     //this object converts an array to list items


                    listView.setAdapter(listAdapter); //this coverts the array to display in the list
                }

                for (Beacon beacon : list){
                    String rssi = String.valueOf(beacon.getRssi());
                    textView.setText(rssi);
                }
            }
        });

       region = new BeaconRegion("36c73c9587ac250c79497aa983562a0d",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);





    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.startRanging(region);
        super.onPause();
    }

    static {
        Map<String, List<String>> placesByBeacons = new HashMap<>();
        placesByBeacons.put("19229:40678", new ArrayList<String>() {{
            add("Heavenly Sandwiches");
            // read as: "Heavenly Sandwiches" is closest
            // to the beacon with major 22504 and minor 48827
            add("Green & Green Salads");
            // "Green & Green Salads" is the next closest
            add("Mini Panini");
            // "Mini Panini" is the furthest away
        }});
        placesByBeacons.put("51308:21495", new ArrayList<String>() {{
            add("Mini Panini");
            add("Green & Green Salads");
            add("Heavenly Sandwiches");
        }});
        placesByBeacons.put("29323:24832", new ArrayList<String>() {{
            add("asfghjkli");
            add("qwertyuiop");
            add("zxcvbnm");
        }});
        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }

    private List<String> placesNearBeacon(Beacon beacon) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            return PLACES_BY_BEACONS.get(beaconKey);
        }
        return Collections.emptyList();
    }


}
