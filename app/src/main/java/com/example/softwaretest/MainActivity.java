package com.example.softwaretest;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DisplayManager displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
		Display[] displays = displayManager.getDisplays();
		if (displays == null || displays.length <= 1){
			return;
		}
		Display display = displays[displays.length - 1];
		Empty adminPresentation = new Empty(this, display);
		adminPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		adminPresentation.show();
	}
}
