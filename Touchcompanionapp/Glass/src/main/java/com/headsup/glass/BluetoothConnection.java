package com.headsup.glass;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by tim on 12/12/13.
 */
public class BluetoothConnection {

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String MY_STRING = "touch";
    private static final String TAG = BluetoothConnection.class.getSimpleName();

    public static BluetoothSocket connectBluetooth(Context context) {
//        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
//        final BluetoothAdapter bluetooth = bluetoothManager.getAdapter();
        final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetooth.getBondedDevices();
        // TODO fix
        for (final BluetoothDevice bthDevice : pairedDevices) {
            Log.v("BLUETOOTH", bthDevice.getName() + " = " + bthDevice.getAddress() + "  " + bthDevice.toString());
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

    public static void listenBluetooth() throws IOException {
        final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        BluetoothServerSocket bluetoothServerSocket = bluetooth.listenUsingRfcommWithServiceRecord(MY_STRING, MY_UUID);
        BluetoothSocket connectSocket = null;
        byte[] buffer = new byte[10];
        int bytes;

        while (true) {
            try {
                connectSocket = bluetoothServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Connection failed");
                break;
            }

            Log.e(TAG, "ALL THE WAY AROUND");
//            try {
//                connectSocket = connectSocket.getRemoteDevice().createRfcommSocketToServiceRecord(MY_UUID);
//                connectSocket.connect();
//            } catch (IOException e1) {
//                Log.e(TAG, "DIDNT WORK");
//            }

            try {
                InputStream inStream = connectSocket.getInputStream();
                while (true) {
                    try {
                        bytes = inStream.read(buffer);
                        String keyString = new String(buffer).substring(0, bytes);
                        Log.e(TAG, "Received: " + bytes  +  "=" + keyString);
                        new InputTask().execute(keyString);
                    } catch (IOException e3) {
                        Log.e(TAG, "disconnected");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }
    }
}
