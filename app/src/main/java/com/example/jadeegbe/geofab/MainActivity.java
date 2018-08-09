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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BeaconID";
    public static String RSSI_value_from_MyApplication = "";
    public static String MOVING_value_from_MyApplication = "";
    public TextView textView, textView1, textView2;
    private Context mContect;
    private Thread threadApplication;
    private MyApplication myApplication;
    public int refresh_interval = 1 * 1000;
    public Handler uiRefresher = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContect = this;


        textView = (TextView) findViewById(R.id.nextActivity);
        textView1 = (TextView) findViewById(R.id.nextActivity1);
        textView2 = (TextView) findViewById(R.id.nextActivity2);


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
}