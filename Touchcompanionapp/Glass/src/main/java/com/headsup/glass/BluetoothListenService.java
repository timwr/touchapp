package com.headsup.glass;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

public class BluetoothListenService extends IntentService {

    public BluetoothListenService() {
        super(BluetoothListenService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(BluetoothListenService.class.getSimpleName(), "Started listening on bluetooth");
        while (true) {
            try {
                BluetoothConnection.listenBluetooth(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(BluetoothListenService.class.getSimpleName(), "restarted listening on bluetooth");
        }
    }

}
