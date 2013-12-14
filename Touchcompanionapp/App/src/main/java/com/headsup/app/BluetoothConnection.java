package com.headsup.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * Created by tim on 12/12/13.
 */
public class BluetoothConnection {

    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static BluetoothConnection sInstance = new BluetoothConnection();
    public static BluetoothConnection getInstance() {
        return sInstance;
    }

    private BluetoothSocket bluetoothSocket;

    public void sendKey(Context context, int keyCode) {
        sendBluetoothString(context, "key" + String.valueOf(keyCode));
    }

    public void sendBluetoothString(Context context, String keyString) {
        Log.e("LOL", "sending " + keyString);
        try {
            Log.v("LOL", "socket " + bluetoothSocket);
            if (bluetoothSocket == null) {
                bluetoothSocket = connectBluetooth(context);
            }
            Log.v("LOL", "socket1 " + bluetoothSocket);
            if (bluetoothSocket == null) {
                return;
            }
            Log.v("LOL", "socket2 " + bluetoothSocket);
            byte[] keyBytes = keyString.getBytes();
            try {
                bluetoothSocket.getOutputStream().write(keyBytes);
            } catch (IOException e) {
                e.printStackTrace();
                bluetoothSocket = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BluetoothSocket connectBluetooth(Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
//        final BluetoothAdapter bluetooth = bluetoothManager.getAdapter();
        final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetooth.getBondedDevices();
        // TODO fix
        for (final BluetoothDevice bthDevice : pairedDevices) {
            Log.v("BLUETOOTH", bthDevice.getName() + " = " + bthDevice.getAddress() + " " + bthDevice.getType() + " " + bthDevice.toString());
            if (bthDevice.getAddress().equals("F8:8F:CA:24:55:1C")) {
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
