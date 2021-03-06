package com.headsup.remote;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.headsup.lib.BluetoothConstants;

import java.util.Set;

public class MainActivity extends ActionBarActivity {

    private static final String BLUETOOTH_FRAGMENT = "bluetooth";

    private ScrollView mDrawerScroll;
    private LinearLayout mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new GestureFragment())
                    .commit();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerScroll = (ScrollView) findViewById(R.id.scrollview_menu);
        mDrawerList = (LinearLayout) findViewById(R.id.layout_menu);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_launcher,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
            }

            public void onDrawerOpened(View drawerView) {
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void checkBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Dialog))
                    .setMessage(getString(R.string.turn_on_bluetooth))
                    .setPositiveButton(getString(R.string.bluetooth_settings), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            launchBluetooth(null);
                        }
                    });
            alertDialog.show();
            return;
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() == 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Dialog))
                    .setMessage(getString(R.string.please_pair))
                    .setPositiveButton(getString(R.string.bluetooth_settings), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            launchBluetooth(null);
                        }
                    });
            alertDialog.show();
            return;
        }

        CharSequence[] bluetoothDevices = new CharSequence[pairedDevices.size()];
        final CharSequence[] bluetoothMacs = new CharSequence[pairedDevices.size()];
        int charIndex = 0;
        for (BluetoothDevice bluetoothDevice : pairedDevices) {
            bluetoothDevices[charIndex] = bluetoothDevice.getName();
            bluetoothMacs[charIndex] = bluetoothDevice.getAddress();
            charIndex++;
        }

        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String mac = sharedPrefs.getString("bt_mac", null);
        if (mac == null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Dialog))
                    .setTitle(getString(R.string.select_your_glass))
                    .setSingleChoiceItems(bluetoothDevices, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPrefs.edit().putString("bt_mac", bluetoothMacs[which].toString()).commit();
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton(getString(R.string.bluetooth_settings), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            launchBluetooth(null);
                        }
                    });
            alertDialog.show();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkBluetooth();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                if (mDrawerLayout.isDrawerOpen(mDrawerScroll)) {
                    mDrawerLayout.closeDrawer(mDrawerScroll);
                } else {
                    mDrawerLayout.openDrawer(mDrawerScroll);
                }

            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                BluetoothConnection.sendBluetoothString(this, BluetoothConstants.CONSTANT_HOME);
            } else {
                BluetoothConnection.sendKey(this, keyCode);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void launchCamera(View view) {
        BluetoothConnection.sendBluetoothString(this, BluetoothConstants.CONSTANT_CAMERA);
    }

    public void launchBluetooth(View view) {
        Intent settingsIntent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(settingsIntent);
    }

    public void quitApp(View view) {
        finish();
    }

    public void launchKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput (InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void closeMenu(View view) {
        Intent intent = new Intent(this, ProActivity.class);
        startActivity(intent);
        mDrawerLayout.closeDrawer(mDrawerScroll);
    }
}
