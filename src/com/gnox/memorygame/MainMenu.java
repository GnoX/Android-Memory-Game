package com.gnox.memorygame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainMenu extends Activity {

	public static final String DRAWABLE_ID_EXTRA = "ID_EXTRA";
	public static int chosenCard = 0;
	private int[] imageIds = { R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5 };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		Button scoreBoard = (Button) findViewById(R.id.mainMenuScoreBoardButton);
		LinearLayout scroll = (LinearLayout) findViewById(R.id.imageScrolling);

		for (int i = 0; i < imageIds.length; i++) {
			Button image = new Button(this);
			image.setBackground(getResources().getDrawable(imageIds[i]));
			image.setTag(imageIds[i]);

			LayoutParams params = new LayoutParams((int) GameLayoutManager.convertPixelsToDp(this, 300),
					(int) GameLayoutManager.convertPixelsToDp(this, 300));

			params.setMargins(5, 5, 5, 5);

			image.setLayoutParams(params);

			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainMenu.this, Game.class);
					chosenCard = (int) v.getTag();
					i.putExtra(DRAWABLE_ID_EXTRA, (int) v.getTag());
					startActivity(i);
				}
			});

			scroll.addView(image);
		}

		scoreBoard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent scoreboard = new Intent(MainMenu.this, ScoreBoard.class);
				startActivity(scoreboard);
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
