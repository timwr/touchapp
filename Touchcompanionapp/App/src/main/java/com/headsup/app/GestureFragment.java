package com.headsup.app;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class GestureFragment extends Fragment implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private GestureDetector gestureDetector;
    private BluetoothSocket bluetoothSocket;
    private float downX = 0;
    private float downY;

    public GestureFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        gestureDetector = new GestureDetector(activity, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView.setOnTouchListener(this);
        return rootView;
    }

    public void sendKey(int keyCode) {
        sendBluetoothString(String.valueOf(keyCode));
    }

    public void sendBluetoothString(String keyString) {
        try {
            if (bluetoothSocket == null) {
                bluetoothSocket = BluetoothConnection.connectBluetooth(getActivity());
            }
            if (bluetoothSocket == null) {
                return;
            }
            byte[] keyBytes = keyString.getBytes();
            bluetoothSocket.getOutputStream().write(keyBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = event.getX();
            downY = event.getY();
        } else if (downX != 0 && event.getAction() == MotionEvent.ACTION_UP) {
            float deltaX = downX - event.getX();
            float deltaY = downY - event.getY();
            if (Math.abs(deltaY) < Math.abs(deltaX)) { // horizontal
                if (deltaX > 0) {
                    sendKey(KeyEvent.KEYCODE_DPAD_LEFT);
                } else {
                    sendKey(KeyEvent.KEYCODE_DPAD_RIGHT);
                }
            } else {
                if (deltaY > 0) {
                    sendKey(KeyEvent.KEYCODE_DPAD_UP);
                } else {
                    sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
                }
            }
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

}