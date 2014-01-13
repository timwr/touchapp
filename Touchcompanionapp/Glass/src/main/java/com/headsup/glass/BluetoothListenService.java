package com.headsup.glass;

import android.app.IntentService;
import android.content.Intent;

import java.io.IOException;

public class BluetoothListenService extends IntentService {

    public BluetoothListenService() {
        super(BluetoothListenService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            BluetoothConnection.listenBluetooth(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
