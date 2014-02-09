package com.headsup.remote;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class ShareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null && getIntent() != null) {
            handleIntent(getIntent());
        }

        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
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
            BluetoothConnection.sendBluetoothString(this, dataString);
        }
    }
}
