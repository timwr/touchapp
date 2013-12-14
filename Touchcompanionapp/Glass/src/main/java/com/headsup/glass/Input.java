package com.headsup.glass;

import android.app.IntentService;
import android.content.Intent;

import java.io.IOException;

public class Input extends IntentService {

    public Input() {
        super(Input.class.getSimpleName());
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
