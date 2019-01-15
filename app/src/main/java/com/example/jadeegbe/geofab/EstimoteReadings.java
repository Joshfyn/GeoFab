package com.example.jadeegbe.geofab;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class EstimoteReadings extends AppCompatActivity {


    private FabDatabaseHelper fabDatabaseHelper;

    private static final String TAG = "BeaconID";
    public static String RSSI_value_from_MyApplication = "";
    public static String Identifier_from_MyApplication = "";
    public static String MOVING_value_from_MyApplication = "";
    public static String Identifier_from_MyApplicationAccel = "";
    public static String XACCELERATION = "";
    public static String YACCELERATION = "";
    public static String ZACCELERATION = "";
    public static String XYZACCELERATION = "";


    private long Timestamp;

    public TextView textView, textView1, textView2, textView3, textView4, textView5, textView6;
    ListView listView;
    private Context mContect;
    private Thread threadApplication;
    private MyApplication myApplication;
    public int refresh_interval = 1 * 1000;
    public Handler uiRefresher = new Handler(Looper.getMainLooper());


    public static final long Mills_To_Minutes = 60000;
    public static final long Mills_To_Hours = 3600000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // helperClass created here
        fabDatabaseHelper = new FabDatabaseHelper(this, "estimoteNearable.db");


        mContect = this;


        textView = (TextView) findViewById(R.id.rssiValue);
        textView1 = (TextView) findViewById(R.id.booleanMovement);
        textView2 = (TextView) findViewById(R.id.xAcclero);
        textView3 = (TextView) findViewById(R.id.yAcclero);
        textView4 = (TextView) findViewById(R.id.zAcclero);
        textView5 = (TextView) findViewById(R.id.xyzAcclero);
        textView6 = (TextView) findViewById(R.id.timestamp);
        listView = (ListView) findViewById(R.id.my_listview);


        threadApplication = new Thread(myApplication);
        threadApplication.start();


        uiRefresher.post(updateRSSI);
        uiRefresher.post(updateMove);
        uiRefresher.post(xMove);
        uiRefresher.post(yMove);
        uiRefresher.post(zMove);
        uiRefresher.post(xyzMove);


        //TODO: Include the timestamp in the project
        Timestamp = System.currentTimeMillis();

        int hour = (int) ((Timestamp / (Mills_To_Hours)) % 24) + 3;
        int seconds = (int) ((Timestamp / 1000) % 60);
        int minutes = (int) (((Timestamp / Mills_To_Minutes)) % 60);
        textView6.setText(String.format(
                "%02dh:%02dmin:%02ds", hour, minutes, seconds
        ));

//        EstimotePackets estimotePacket1 = new EstimotePackets(String.valueOf(Timestamp), EstimoteReadings.Identifier_from_MyApplication, EstimoteReadings.RSSI_value_from_MyApplication,
//                EstimoteReadings.Identifier_from_MyApplicationAccel, EstimoteReadings.XACCELERATION, EstimoteReadings.YACCELERATION, EstimoteReadings.ZACCELERATION, EstimoteReadings.XYZACCELERATION);
//        Log.d("add data to db", estimotePacket1.get_estimoteRSSI() + "");
//        fabDatabaseHelper.addData(estimotePacket1);


        List<EstimotePackets> estimotePackets = fabDatabaseHelper.allPackets();
        if (estimotePackets != null) {
            String[] itemsNames = new String[estimotePackets.size()];

            for (int i = 0; i < estimotePackets.size(); i++) {
                itemsNames[i] = estimotePackets.get(i).toString();
            }

            // display like string instances
            ListView list = (ListView) findViewById(R.id.my_listview);
            list.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, itemsNames));

        }
    }

    Runnable updateRSSI = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(RSSI_value_from_MyApplication);
                    Log.i(TAG, String.valueOf(RSSI_value_from_MyApplication));
                }
            });

            uiRefresher.postDelayed(updateRSSI, refresh_interval);
        }

    };

    Runnable updateMove = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView1.setText(MOVING_value_from_MyApplication);
                    Log.i(TAG, String.valueOf(MOVING_value_from_MyApplication));
                }
            });

            uiRefresher.postDelayed(updateMove, refresh_interval);
        }

    };

    Runnable xMove = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView2.setText(XACCELERATION);
                    Log.i(TAG, String.valueOf(XACCELERATION));
                }
            });

            uiRefresher.postDelayed(xMove, refresh_interval);
        }

    };

    Runnable yMove = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView3.setText(YACCELERATION);
                    Log.i(TAG, String.valueOf(YACCELERATION));
                }
            });

            uiRefresher.postDelayed(yMove, refresh_interval);
        }

    };

    Runnable zMove = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView4.setText(ZACCELERATION);
                    Log.i(TAG, String.valueOf(ZACCELERATION));
                }
            });

            uiRefresher.postDelayed(zMove, refresh_interval);
        }

    };

    Runnable xyzMove = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView5.setText(XYZACCELERATION);
                    Log.i(TAG, String.valueOf(XYZACCELERATION));
                }
            });

            uiRefresher.postDelayed(xyzMove, refresh_interval);
        }

    };

}
