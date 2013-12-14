package com.headsup.glass;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by tim on 12/12/13.
 */
public class BluetoothConnection {

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String MY_STRING = "touch";
    private static final String TAG = BluetoothConnection.class.getSimpleName();

    public static void listenBluetooth(Context context) throws IOException {
        final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        BluetoothServerSocket bluetoothServerSocket = bluetooth.listenUsingRfcommWithServiceRecord(MY_STRING, MY_UUID);
        BluetoothSocket connectSocket = bluetoothServerSocket.accept();
        InputStream inputStream = connectSocket.getInputStream();

        while (true) {
            byte[] buffer = new byte[1024];
            int bytes = inputStream.read(buffer);
            String keyString = new String(buffer).substring(0, bytes);
            Log.e("OUT", "Received: " + bytes + "=" + keyString);
            if (keyString != null && keyString.startsWith("key")) {
                String key = keyString.substring(3);
                new InputTask().execute(key);
            } else {
                try {
                    PowerManager.WakeLock screenLock = ((PowerManager)context.getSystemService(Context.POWER_SERVICE)).newWakeLock(
                            PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
                    screenLock.acquire();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(keyString));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(intent);
                    screenLock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
