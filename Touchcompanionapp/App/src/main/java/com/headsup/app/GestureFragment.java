package com.headsup.app;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * A placeholder fragment containing a simple view.
 */
public class GestureFragment extends Fragment implements GestureDetector.OnGestureListener, View.OnTouchListener, GestureOverlayView.OnGesturePerformedListener {

    private View view;
    private LinearLayout layoutGesture;
    private android.gesture.GestureOverlayView viewGesture;

    private GestureDetector gestureDetector;
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
        view = rootView;
        viewGesture = (android.gesture.GestureOverlayView) view.findViewById(R.id.view_gesture);
        viewGesture.setOnTouchListener(this);

//        layoutGesture = (LinearLayout) rootView.findViewById(R.id.layout_gesture);
//        layoutGesture.addView(gestureOverlayView);
        return rootView;
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
                    BluetoothConnection.sendKey(getActivity(), KeyEvent.KEYCODE_DPAD_RIGHT);
                } else {
                    BluetoothConnection.sendKey(getActivity(), KeyEvent.KEYCODE_DPAD_LEFT);
                }
            } else {
                if (deltaY >= 0) {
                    BluetoothConnection.sendKey(getActivity(), KeyEvent.KEYCODE_ENTER);
                } else {
                    BluetoothConnection.sendKey(getActivity(), KeyEvent.KEYCODE_BACK);
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
        BluetoothConnection.sendKey(getActivity(), KeyEvent.KEYCODE_ENTER);
        return true;
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

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

    }
}
