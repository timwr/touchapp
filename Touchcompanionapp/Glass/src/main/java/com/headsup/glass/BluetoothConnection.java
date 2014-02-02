package com.headsup.glass;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        while (true) {

            String keyString = null;
            try {
                keyString = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            Log.e("OUT", "Received: " + keyString.length() + "=" + keyString);

            if (keyString != null && keyString.length() > 0) {
                PowerManager.WakeLock screenLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(
                        PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
                screenLock.acquire();
                screenLock.release();
                if (keyString.startsWith("key")) {
                    String key = keyString.substring(3);
                    Intent intent = new Intent("key-event");
                    intent.putExtra("message", key);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else {
                    try {
                        Intent intent = null;
                        if ("home".equals(keyString)) {
                            intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                        } else if ("camera".equals(keyString)) {
                            intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        } else {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(keyString));
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        connectSocket.close();
        bluetoothServerSocket.close();
    }
}
