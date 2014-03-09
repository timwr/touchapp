package com.headsup.remote;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ProActivity extends ActionBarActivity {

    private ImageView imageviewClear;
    private int bottomHeight = 0;

    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pro);
        imageviewClear = (ImageView) findViewById(R.id.imageview_clear);
        imageviewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bottomHeight = (int) (getResources().getDisplayMetrics().heightPixels * (900.0 / 1280.0));
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && event.getY() > bottomHeight) {
            finish();
            return true;
        }
        return super.onTouchEvent(event);
    }
}