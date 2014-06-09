package com.gnox.memorygame;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

import android.os.Handler;
import android.util.Log;

/**
 * Class for managing the cards, their flipping and comparing.
 */
public class CardManager {
	Card first = null;
	Card second = null;
	static Object lock = new Object();

	/**
	 * Setting cards for comparation. If there is no first card selected, then
	 * function sets first card, if it is set, then it sets second card. If both
	 * are set, then it compares them.
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
			first = null;
		}

	}

	/**
	 * Function checks if cards has the same image. If they do, then it disables
	 * buttons. If they don't have same image, then function flips them to back.
	 */
	public void checkPair(final Card card) {
		if (first.checkPair(card)) {
			Log.d("checkPair()", "Check passed");
			first.disable();
			card.disable();
		} else {
			Log.d("checkPair()", "Check did not pass");
			
			Handler handler = new Handler();
			final CardManager self = this;
			handler.postDelayed(new Runnable() {
				Card first = self.first;
				Card c = card;

				@Override
				public void run() {
					first.flipBack();
					c.flipBack();
				}
			}, 1000);
		}
	}
}
