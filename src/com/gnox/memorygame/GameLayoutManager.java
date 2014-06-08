package com.gnox.memorygame;

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
	Context context;
	List<Drawable> imagesList;
	Card[][] cards;

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
	@SuppressLint("NewApi")
	private TableRow createTableRow(int actRow) {
		// getting WindowManager to get size of screen
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Point size = new Point();
		wm.getDefaultDisplay().getSize(size);

		int width = size.x;
		int height = size.y;

		// ratio is used to determine size of buttons
		int ratio = width / height;

		// getting density pixels other than normal pixels
		final float scale = context.getResources().getDisplayMetrics().density;
		int pixels;

		// if ratio is 1, which means the screen is in land mode, then set
		// button size to fit all the buttons inside screen
		if (ratio == 1)
			pixels = (int) ((height / (Game.COLUMNS + 1)) * scale + .5f);
		else
			pixels = (int) ((width / (Game.ROWS + 1)) * scale + .5f);

		// setting buttons to be squares
		LayoutParams params = new LayoutParams(pixels, pixels);

		// margins to make it look better
		params.setMargins(10, 10, 0, 0);

		// row that will be returned
		TableRow row = new TableRow(context);

		// centering buttons inside row
		row.setGravity(Gravity.CENTER);

		// exactly what function says, adding buttons to created TableRow
		try {
			addButtonsToRow(row, params, actRow);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

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
	 * @throws Exception
	 *             Exception is thrown when images were not loaded
	 */
	@SuppressLint("NewApi")
	private void addButtonsToRow(TableRow row, LayoutParams params, int actRow) throws Exception {
		for (int actColumn = 0; actColumn < Game.ROWS; actColumn++) {
			Button button = new Button(context);

			// if images list is not set, then throw Exception
			if (imagesList != null)
				button.setBackground(imagesList.get(0));
			else
				throw new Exception("Images are not set.");

			button.setLayoutParams(params);

			// adding buttons to row
			row.addView(button);

			// TODO
			// make cards use different pictures
			Card card = new Card(button, imagesList.get(1));

			// adding cards into two dimensional array
			cards[actRow][actColumn] = card;
		}

	}

	/**
	 * This function creates TableLayout which contains all columns and rows
	 * that gamefield contains.
	 * 
	 * @return TableLayout containing whole game field.
	 */
	public TableLayout createGameField() {
		int actRow = 0;
		cards = new Card[Game.COLUMNS][Game.ROWS];

		TableLayout gameField = new TableLayout(context);

		// add TableRows into gameField TableLayout and incrementation of actRow
		for (int i = 0; i < Game.COLUMNS; i++)
			gameField.addView(createTableRow(actRow++));

		gameField.setGravity(Gravity.CENTER);

		return gameField;
	}

	public void setImagesList(List<Drawable> imagesList) {
		this.imagesList = imagesList;
	}

}
