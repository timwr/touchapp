package com.headsup.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.headsup.lib.BluetoothConstants;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

/**
 * Created by tim on 12/12/13.
 */
public class BluetoothConnection implements BluetoothConstants {

    private static BluetoothConnection sInstance = new BluetoothConnection();
    public static BluetoothConnection getInstance() {
        return sInstance;
    }

    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;

    public void sendKey(Context context, int keyCode) {
        sendBluetoothString(context, CONSTANT_KEY + String.valueOf(keyCode));
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
