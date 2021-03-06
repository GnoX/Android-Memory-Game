package com.gnox.memorygame;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

// TODO add settings

public class Game extends Activity {

	private GameLayoutManager layoutManager;
	private ImageLoader imgLoader;
	public static CardManager cardManager;
	public static TextView display;
	private Timer T;
	private int time;

	public static final String TIME_EXTRA = "TIME_EXTRA";

	// Back side of Cards
	public static Drawable backSide;

	public static int COLUMNS;
	public static int ROWS;

	static TableLayout mainTable;
	private TextView timerTextView;
	private Button scoreBoardButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		initialize();
		newGame(6, 4);

	}

	/**
	 * Objects initialization
	 */
	private void initialize() {

		imgLoader = new ImageLoader(getResources());

		mainTable = (TableLayout) findViewById(R.id.mainTable);

		layoutManager = new GameLayoutManager(this);

		cardManager = new CardManager();

		backSide = getResources().getDrawable(R.drawable.back);

		display = (TextView) findViewById(R.id.display);

		scoreBoardButton = (Button) findViewById(R.id.scoreBoardButton);
		scoreBoardButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Game.this, ScoreBoard.class);
				startActivity(intent);
			}
		});
	}

	private void createTimer() {
		T = new Timer();
		T.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						time++;
						timerTextView.setText("" + time);
					}
				});
			}
		}, 1000, 1000);

	}

	@Override
	protected void onResume() {
		super.onResume();
		createTimer();
	}

	@Override
	protected void onPause() {
		super.onPause();
		T.cancel();
		T = null;
	}

	/**
	 * Initializes while game field.
	 * 
	 * @param rows
	 *            Rows of game field.
	 * @param columns
	 *            Columns of game field.
	 */
	private void newGame(int rows, int columns) {
		COLUMNS = columns;
		ROWS = rows;

		cardManager.setCardsLeft(COLUMNS * ROWS);

		int frontCardImageId = getIntent().hasExtra(MainMenu.DRAWABLE_ID_EXTRA) ? getIntent().getExtras().getInt(
				MainMenu.DRAWABLE_ID_EXTRA) : MainMenu.chosenCard;

		imgLoader.setImageFrame(frontCardImageId, 3, 4);

		int w, h;

		switch (frontCardImageId) {
		case R.drawable.card7:
			w = 3;
			h = 4;
		default:
			w = 4;
			h = 3;
		}

		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++)
				imgLoader.addCroppedImage(i, j);

		layoutManager.setImagesList(imgLoader.getImageList());

		// adding view, which was created from layoutManager to main table
		mainTable.addView(layoutManager.createGameField());

		timerTextView = new TextView(this);
		timerTextView.setGravity(Gravity.CENTER);
		mainTable.addView(timerTextView);
	}

	public class CardManager {
		private Card first = null;
		boolean stop = false;
		private int turn = 0;
		private int cardsLeft;

		/**
		 * Setting cards for comparison. If there is no first card selected,
		 * then function sets first card, if it is set, then it sets second
		 * card. If both are set, then it compares them.
		 * 
		 * @param card
		 *            Card that should be set.
		 */
		public void set(Card card) {
			card.flip();
			if (first == null)
				first = card;
			else if (first == card)
				;
			else {
				checkPair(card);
			}

		}

		/**
		 * Function checks if cards has the same image. If they do, then it
		 * disables buttons. If they don't have same image, then function flips
		 * them to back.
		 */
		public void checkPair(final Card card) {
			Game.display.setText("Turns: " + turn);
			turn++;
			if (first.checkPair(card)) {
				first.disable(true);
				card.disable(true);
				first = null;
				cardsLeft -= 2;
			} else {

				stop = true;

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						first.flipBack();
						card.flipBack();
						first = null;
						stop = false;
					}
				}, 1300);
			}

			if (isGameOver()) {
				Intent i = new Intent(Game.this, ScoreBoard.class);
				i.putExtra(TIME_EXTRA, time);
				finish();
				startActivity(i);
			}
		}

		public boolean isGameOver() {
			return cardsLeft <= 0;
		}

		public void setCardsLeft(int cardsLeft) {
			this.cardsLeft = cardsLeft;
		}

	}
}
