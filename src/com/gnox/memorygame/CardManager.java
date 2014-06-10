package com.gnox.memorygame;

import android.os.Handler;
import android.util.Log;

/**
 * Class for managing the cards, their flipping and comparing.
 */
public class CardManager {
	private Card first = null;
	boolean stop = false;
	private int turn = 0;

	/**
	 * Setting cards for comparison. If there is no first card selected, then
	 * function sets first card, if it is set, then it sets second card. If both
	 * are set, then it compares them.
	 * 
	 * @param card
	 *            Card that should be set.
	 */
	public void set(Card card) {
		while (stop)
			;
		card.flip();
		if (first == null)
			first = card;
		else if (first == card)
			;
		else {
			checkPair(card);
			first = null;
		}

	}

	/**
	 * Function checks if cards has the same image. If they do, then it disables
	 * buttons. If they don't have same image, then function flips them to back.
	 */
	public void checkPair(final Card card) {
		Game.display.setText("Turns: " + turn);
		turn++;
		if (first.checkPair(card)) {
			Log.d("checkPair()", "Check passed");
			first.disable();
			card.disable();
		} else {
			Log.d("checkPair()", "Check did not pass");

			Handler handler = new Handler();
			final CardManager self = this;
			stop = true;

			handler.postDelayed(new Runnable() {
				private Card first = self.first;
				private Card c = card;

				@Override
				public void run() {
					first.flipBack();
					c.flipBack();
					stop = false;
				}
			}, 1300);
		}
	}
}
