package com.headsup.glass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

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
    }

}
