package com.gnox.memorygame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

/**
 * Class for managing the game field layout. It contains functions that are used
 * to create TableRows, Buttons and whole formatted TableLayout.
 */
public class GameLayoutManager {
	private final Context context;
	private List<Drawable> imagesList;
	private Integer[] rand;
	private int randPos;
	private int actRow = 0;

	public GameLayoutManager(Context context) {
		this.context = context;
	}

	/**
	 * Function is dynamically creating TableRows according to number of columns
	 * and rows, that was inserted from Main Menu activity.
	 * 
	 * @param actRow
	 *            Row that is being inflated
	 * @return TableRow that was inflated with buttons or null if no images are
	 *         present
	 */
	private TableRow createTableRow(int actRow) {
		// row that will be returned
		TableRow row = new TableRow(context);

		// centering buttons inside row
		row.setGravity(Gravity.CENTER);

		return row;

	}

	/**
	 * Function is inflating buttons dynamically into <b> TableRow row</b>
	 * according to number of columns and rows.
	 * 
	 * @param row
	 *            Row, that will be inflated with buttons.
	 * @param params
	 *            Parameters of buttons.
	 * @param actRow
	 *            Row, that is being worked with
	 */
	@SuppressLint("NewApi")
	private void addButtonsToRow(TableRow row) {
		LayoutParams params = calculateLayoutParameters();
		for (int actColumn = 0; actColumn < Game.ROWS; actColumn++) {
			Button button = new Button(context);

			button.setLayoutParams(params);

			// adding buttons to row
			row.addView(button);

			new Card(button, imagesList.get(nextRandomNumber()));
		}

	}

	/**
	 * This function creates TableLayout which contains all columns and rows
	 * that gamefield contains.
	 * 
	 * @return TableLayout containing whole game field.
	 */
	public TableLayout createGameField() {

		// creating pair of random numbers for inserting images into their
		// respective buttons
		rand = new Integer[(Game.COLUMNS * Game.ROWS)];
		randPos = 0;
		for (int i = 0, j = 0; i < rand.length / 2; i++) {
			rand[j++] = i;
			rand[j++] = i;
		}

		Collections.shuffle(Arrays.asList(rand));

		TableLayout gameField = new TableLayout(context);

		// add TableRows into gameField TableLayout and incrementation of actRow
		for (int i = 0; i < Game.COLUMNS; i++, actRow++) {
			TableRow row = createTableRow(actRow);
			addButtonsToRow(row);
			gameField.addView(row);

			// exactly what function says, adding buttons to created TableRow
		}

		gameField.setGravity(Gravity.CENTER);

		return gameField;
	}

	public void setImagesList(List<Drawable> imagesList) {
		this.imagesList = imagesList;
	}

	private int nextRandomNumber() {
		return rand[randPos++];
	}

	@SuppressLint("NewApi")
	private LayoutParams calculateLayoutParameters() {
		// getting WindowManager to get size of screen
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Point size = new Point();
		wm.getDefaultDisplay().getSize(size);

		int width = size.x;
		int height = size.y;

		// ratio is used to determine size of buttons
		int ratio = width / height;

		int pixels;

		// if ratio is 1, which means the screen is in land mode, then set
		// button size to fit all the buttons inside screen
		if (ratio == 1)
			pixels = (int) convertPixelsToDp(context, height / (Game.COLUMNS + 1));
		else
			pixels = (int) convertPixelsToDp(context, width / (Game.ROWS + 1));

		// setting buttons to be squares
		LayoutParams params = new LayoutParams(pixels, pixels);

		// margins to make it look better
		params.setMargins(10, 10, 0, 0);

		return params;
	}

	public static float convertPixelsToDp(Context context, float pixels) {
		// getting density pixels other than normal pixels
		final float scale = context.getResources().getDisplayMetrics().density;
		return pixels * scale + .5f;

	}
}
