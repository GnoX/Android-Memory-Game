package com.gnox.memorygame;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TableLayout;

public class Game extends Activity {

	private GameLayoutManager layoutManager;
	private List<Drawable> images;
	public static CardManager cardManager;

	// Back side of Cards
	public static Drawable backSide;
	public static int COLUMNS;
	public static int ROWS;

	TableLayout mainTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		loadImages();

		mainTable = (TableLayout) findViewById(R.id.mainTable);

		layoutManager = new GameLayoutManager(this);
		layoutManager.setImagesList(images);

		cardManager = new CardManager();

		backSide = getResources().getDrawable(R.drawable.card1);

		newGame(6, 6);
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

		// adding view, which was created from layoutManager to main table
		mainTable.addView(layoutManager.createGameField());
	}

	/**
	 * Loads every image that will be used in project
	 */
	private void loadImages() {
		images = new ArrayList<Drawable>();

		images.add(getResources().getDrawable(R.drawable.card1));
		images.add(getResources().getDrawable(R.drawable.cards));

	}
}
