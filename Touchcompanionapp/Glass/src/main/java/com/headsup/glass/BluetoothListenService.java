package com.headsup.glass;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class BluetoothListenService extends IntentService {

    public BluetoothListenService() {
        super(BluetoothListenService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Toast.makeText(this, "Started listening...", Toast.LENGTH_SHORT).show();
        Log.i(BluetoothListenService.class.getSimpleName(), "Started listening on bluetooth");
        try {
            BluetoothConnection.listenBluetooth(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(BluetoothListenService.class.getSimpleName(), "Stopped listening on bluetooth");
    }

}
