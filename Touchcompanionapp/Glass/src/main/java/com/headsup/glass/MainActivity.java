package com.headsup.glass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String currentKeyboard =  Settings.Secure.getString(getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
        Log.i("Keyboard", currentKeyboard);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, BluetoothListenService.class);
        startService(intent);

        InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

}
