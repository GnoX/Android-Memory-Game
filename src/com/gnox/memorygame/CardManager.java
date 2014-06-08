package com.gnox.memorygame;

/**
 * Class for managing the cards, their flipping and comparing.
 */
public class CardManager {
	Card first;
	Card second;
	boolean bothSet;

	/**
	 * Setting cards for comparation. If there is no first card selected, then
	 * function sets first card, if it is set, then it sets second card. If both
	 * are set, then it compares them.
	 * 
	 * @param card
	 *            Card that should be set.
	 */
	public void set(Card card) {
		if (first == null)
			setFirst(card);
		else {
			setSecond(card);
			checkPair();
		}
	}

	/**
	 * Sets the first card.
	 * 
	 * @param Card
	 *            First card that will be flipped.
	 */
	public void setFirst(Card first) {
		first.flip();
		this.first = first;
	}

	/**
	 * Sets the second card
	 * 
	 * @param Card
	 *            Second card that will be set and flipped.
	 */
	public void setSecond(Card second) {
		second.flip();
		this.second = second;
	}

	/**
	 * Function checks if cards has the same image. If they do, then it disables
	 * buttons. If they don't have same image, then function flips them to back.
	 */
	public void checkPair() {
		if (first.checkPair(second)) {
			first.disable();
			second.disable();
		} else {
			first.flipBack();
			second.flipBack();
		}
	}
}
