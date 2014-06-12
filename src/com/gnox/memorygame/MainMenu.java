package com.gnox.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainMenu extends Activity {

	private static final int[] images = { R.drawable.card1 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		Intent intent = new Intent(MainMenu.this, Game.class);
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
