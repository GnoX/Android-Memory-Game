package com.gnox.memorygame;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

public class Game extends Activity {

	private GameLayoutManager layoutManager;
	private ImageLoader imgLoader;
	public static CardManager cardManager;
	public List<Bitmap> bMapList;
	static TextView display; 

	// Back side of Cards
	public static Drawable backSide;
	public static int COLUMNS;
	public static int ROWS;

	static TableLayout mainTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		initialize();

		newGame(4, 5);
	}

	/**
	 * Objects initialization
	 */
	private void initialize() {
		imgLoader = new ImageLoader(getResources());

		mainTable = (TableLayout) findViewById(R.id.mainTable);

		layoutManager = new GameLayoutManager(this);

		cardManager = new CardManager();

		backSide = getResources().getDrawable(R.drawable.card1);
		
		display = (TextView) findViewById(R.id.display);
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

		imgLoader.setImageFrame(R.drawable.cards, 4, 4);
		
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				if (!(i == 3 && j == 2 || j == 3))
					imgLoader.addCroppedImage(i, j);

		layoutManager.setImagesList(imgLoader.getImageList());

		// adding view, which was created from layoutManager to main table
		mainTable.addView(layoutManager.createGameField());

	}

}
