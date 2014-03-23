package com.headsup.remote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class SplashActivity extends ActionBarActivity {

	private static final long SPLASH_SCREEN_DELAY = 3000;
	private static boolean sApplicationLoaded = false;

    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

        View view = findViewById(R.id.layout_splash);

		if (sApplicationLoaded) {
			launchApplication();
		} else {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    launchApplication();
                }
            }, SPLASH_SCREEN_DELAY);
        }
	}


	private void launchApplication() {
		if (!isFinishing()) {
			sApplicationLoaded = true;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
			finish();
		}
	}
}