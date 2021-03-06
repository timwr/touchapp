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

import com.headsup.lib.BluetoothConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by tim on 12/12/13.
 */
public class BluetoothConnection implements BluetoothConstants {

    private static final String ACTION_TAKE_PICTURE = "com.google.glass.action.TAKE_PICTURE";
    private static final String ACTION_PREPARE_CAMERA = "com.google.glass.action.PREPARE_CAMERA";

    private static final String TAG = BluetoothConnection.class.getSimpleName();

    private static void sendKey(Context context, String key) {
        Intent intent = new Intent("key-event");
        intent.putExtra("message", key);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

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
                if (keyString.startsWith(BluetoothConstants.CONSTANT_KEY)) {
                    String key = keyString.substring(3);
                    sendKey(context, key);
                } else {
                    try {
                        Intent intent = null;
                        if (BluetoothConstants.CONSTANT_HOME.equals(keyString)) {
                            intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(0x10210000);
                        } else if (BluetoothConstants.CONSTANT_CAMERA.equals(keyString)) {
                            Intent prepareIntent = new Intent(ACTION_PREPARE_CAMERA);
                            context.sendBroadcast(prepareIntent);
                            intent = new Intent(ACTION_TAKE_PICTURE);
                            intent.setFlags(0x18000000);
                            intent.putExtra("should_take_picture", true);
                        } else {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(keyString));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
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
