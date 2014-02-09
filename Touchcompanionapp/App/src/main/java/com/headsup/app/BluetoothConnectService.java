package com.headsup.app;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.headsup.lib.BluetoothConstants;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

public class BluetoothConnectService extends IntentService implements BluetoothConstants {

    public static final String EXTRA_KEY_SEND_STRING = "send";
    private static BluetoothSocket bluetoothSocket;
    private static OutputStream outputStream;

    public BluetoothConnectService() {
        super(BluetoothConnectService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(BluetoothConnectService.class.getSimpleName(), "Started connect on bluetooth");
        String cmd = intent.getStringExtra(EXTRA_KEY_SEND_STRING);

        sendBluetoothString(this, cmd);
    }


    public void sendBluetoothString(Context context, String keyString) {
        Log.e("LOL", "sending " + keyString);
        try {
            keyString = keyString + "\n";
            Log.v("LOL", "socket " + bluetoothSocket);
            if (bluetoothSocket == null) {
                bluetoothSocket = connectBluetooth(context);
                outputStream = null;
            }
            Log.v("LOL", "socket1 " + bluetoothSocket);
            if (bluetoothSocket == null) {
                return;
            }
            Log.v("LOL", "socket2 " + bluetoothSocket);
            byte[] keyBytes = keyString.getBytes();
            try {
                if (outputStream == null) {
                    outputStream = bluetoothSocket.getOutputStream();
                }
                outputStream.write(keyBytes);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
                bluetoothSocket = null;
                outputStream = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BluetoothSocket connectBluetooth(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String mac = sharedPrefs.getString("bt_mac", null);
        if (mac == null) {
            return null;
        }
        final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetooth.getBondedDevices();
        for (final BluetoothDevice bthDevice : pairedDevices) {
//            Log.v("BLUETOOTH", bthDevice.getName() + " = " + bthDevice.getAddress() + " " + bthDevice.getType() + " " + bthDevice.toString());
            if (mac.equals(bthDevice.getAddress())) {
                final String addressPairedDevice = bthDevice.getAddress();
                try {
                    BluetoothSocket socket = bthDevice.createRfcommSocketToServiceRecord(MY_UUID);
                    socket.connect();
                    return socket;
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                AsyncTask<Integer, Void, Void> asynTask = new AsyncTask<Integer, Void, Void>() {
//                    @Override
//                    protected Void doInBackground(Integer... params) {
//                        return null;
//                    }
//                };
//                asynTask.execute(0);
            }
        }
        return null;
    }

}
