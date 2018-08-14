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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BeaconID";
    public static String RSSI_value_from_MyApplication = "";
    public static String MOVING_value_from_MyApplication = "";
    public static String XACCELERATION = "";
    public static String YACCELERATION = "";
    public static String ZACCELERATION = "";
    public static String XYZACCELERATION = "";
    public static String [] allPackets;


    //private long Timestamp;


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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContect = this;


        textView = (TextView) findViewById(R.id.nextActivity);
        textView1 = (TextView) findViewById(R.id.nextActivity1);
        textView2 = (TextView) findViewById(R.id.xAcclero);
        textView3 = (TextView) findViewById(R.id.yAcclero);
        textView4 = (TextView) findViewById(R.id.zAcclero);
        textView5 = (TextView) findViewById(R.id.xyzAcclero);
        textView6 = (TextView) findViewById(R.id.timestamp);
        listView = (ListView) findViewById(R.id.my_listview);


        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
        }

        if (!bluetoothAdapter.isEnabled()) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);
            Toast.makeText(getApplicationContext(), "Turn ON Bluetooth to continue using APP", Toast.LENGTH_SHORT).show();

        }

        threadApplication = new Thread(myApplication);
        threadApplication.start();


        uiRefresher.post(updateRSSI);
        uiRefresher.post(updateMove);
        uiRefresher.post(xMove);
        uiRefresher.post(yMove);
        uiRefresher.post(zMove);
        uiRefresher.post(xyzMove);


        //TODO: Include the timestamp in the project
        /*Timestamp = System.currentTimeMillis();

        int hour = (int) ((Timestamp / (Mills_To_Hours))%24)+3;
        int seconds = (int) ((Timestamp / 1000) % 60);
        int minutes = (int) (((Timestamp / Mills_To_Minutes)) % 60);
        textView6.setText(String.format(
                "%02dh:%02dmin:%02ds", hour, minutes, seconds
        ));*/



    }

    Runnable updateRSSI =new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(RSSI_value_from_MyApplication);
                    Log.i(TAG, String.valueOf(RSSI_value_from_MyApplication));
                }
            });

            uiRefresher.postDelayed(updateRSSI,refresh_interval);
        }

    };

    Runnable updateMove =new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView1.setText(MOVING_value_from_MyApplication);
                    Log.i(TAG, String.valueOf(MOVING_value_from_MyApplication));
                }
            });

            uiRefresher.postDelayed(updateMove,refresh_interval);
        }

    };

    Runnable xMove =new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView2.setText(XACCELERATION);
                    Log.i(TAG, String.valueOf(XACCELERATION));
                }
            });

            uiRefresher.postDelayed(xMove,refresh_interval);
        }

    };

    Runnable yMove =new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView3.setText(YACCELERATION);
                    Log.i(TAG, String.valueOf(YACCELERATION));
                }
            });

            uiRefresher.postDelayed(yMove,refresh_interval);
        }

    };

    Runnable zMove =new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView4.setText(ZACCELERATION);
                    Log.i(TAG, String.valueOf(ZACCELERATION));
                }
            });

            uiRefresher.postDelayed(zMove,refresh_interval);
        }

    };

    Runnable xyzMove =new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView5.setText(XYZACCELERATION);
                    Log.i(TAG, String.valueOf(XYZACCELERATION));
                }
            });

            uiRefresher.postDelayed(xyzMove,refresh_interval);
        }

    };

    Runnable Viewss =new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(new ArrayAdapter<String>(this, allPackets));
                }
            });

            uiRefresher.postDelayed(xyzMove,refresh_interval);
        }

    };
}