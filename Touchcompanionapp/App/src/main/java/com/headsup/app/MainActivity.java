package com.headsup.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;

public class MainActivity extends ActionBarActivity {

    private static final String BLUETOOTH_FRAGMENT = "bluetooth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new GestureFragment())
                    .commit();
        }


//        new BluetoothFragment().show(getSupportFragmentManager(), BLUETOOTH_FRAGMENT);

        if (savedInstanceState == null && getIntent() != null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput (InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(getIntent());
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        new Throwable().printStackTrace();
        Log.v("", "" + intent);
        Log.v("", "" + intent.getData());
        Log.v("", "" + intent.getExtras());
        Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        String dataString = null;
        if (uri != null) {
            dataString = uri.toString();
        }
        uri = intent.getData();
        if (uri != null) {
            if (dataString == null) {
                dataString = uri.toString();
            }
        }
        if (dataString == null) {
            dataString = intent.getDataString();
        }

        if (dataString == null) {
            dataString = intent.getStringExtra(Intent.EXTRA_TEXT);
        }
//        Log.v("", "" + uri);
//        if (uri == null) {
//            new Throwable().printStackTrace();
//            return;
//        }
        Log.e("", "" + dataString);
        if (dataString != null) {
            new Throwable().printStackTrace();
            BluetoothConnection.getInstance().sendBluetoothString(this, dataString);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                BluetoothConnection.getInstance().sendBluetoothString(this, "home");
            } else {
                BluetoothConnection.getInstance().sendKey(this, keyCode);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
