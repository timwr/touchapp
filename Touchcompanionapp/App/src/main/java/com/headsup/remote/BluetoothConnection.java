package com.headsup.remote;

import android.content.Context;
import android.content.Intent;

import com.headsup.lib.BluetoothConstants;

/**
 * Created by tim on 12/12/13.
 */
public class BluetoothConnection implements BluetoothConstants {

    public static void sendKey(Context context, int keyCode) {
        sendBluetoothString(context, CONSTANT_KEY + String.valueOf(keyCode));
    }

    public static void sendBluetoothString(Context context, String dataString) {
        Intent intent = new Intent(context, BluetoothConnectService.class);
        intent.putExtra(BluetoothConnectService.EXTRA_KEY_SEND_STRING, dataString);
        context.startService(intent);
    }
}
