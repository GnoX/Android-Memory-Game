package com.gnox.memorygame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainMenu extends Activity implements OnClickListener {

	public static final String DRAWABLE_ID_EXTRA = "ID_EXTRA";
	private int[] imageIds = { R.drawable.card1, R.drawable.card2, R.drawable.card3 };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		Button newGame = (Button) findViewById(R.id.mainMenuNewGameButton);
		Button scoreBoard = (Button) findViewById(R.id.mainMenuScoreBoardButton);
		LinearLayout scroll = (LinearLayout) findViewById(R.id.imageScrolling);

		for (int i = 0; i < 3; i++) {
			Button test = new Button(this);
			test.setBackground(getResources().getDrawable(imageIds[i]));
			test.setTag(imageIds[i]);
			test.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainMenu.this, Game.class);
					i.putExtra(DRAWABLE_ID_EXTRA, (int) v.getTag());
					startActivity(i);
				}
			});
			
			scroll.addView(test);
		}

		newGame.setOnClickListener(this);
		scoreBoard.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mainMenuNewGameButton:
			Intent newGame = new Intent(MainMenu.this, Game.class);
			startActivity(newGame);
			break;
		case R.id.mainMenuScoreBoardButton:
			Intent scoreboard = new Intent(MainMenu.this, ScoreBoard.class);
			startActivity(scoreboard);
			break;
		}

	}
}
