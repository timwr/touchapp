package com.headsup.glass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.InputMethodService;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;

public class GlassInputService extends InputMethodService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(GlassInputService.class.getSimpleName(), "onCreate");
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("key-event"));
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
        Log.e(GlassInputService.class.getSimpleName(), "onDestroy");
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.d("receiver", "key: " + message);
            try {
                int keyCode = Integer.valueOf(message);
                keyDownUp(keyCode);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    };

    private void keyDownUp(int keyEventCode) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            inputConnection.sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
            inputConnection.sendKeyEvent(
                    new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
        } else {
            new Throwable().printStackTrace();
        }
    }
}