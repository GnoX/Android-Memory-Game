package com.gnox.memorygame;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Card class, which contains functions, that every card use.
 */
public class Card {
	// Button, that will be shown on UI
	private Button button;

	// front side of card, varied
	private Drawable frontSide;
	private Card self = this;

	public Card(Button button, Drawable frontSide) {
		button.setBackground(Game.backSide);
		this.button = button;
		this.frontSide = frontSide;
		this.button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// we need to set the card in Card Manager so that it can
				// determine if cards were turned and then check if they are
				// same
				Game.cardManager.set(self);
			}

		});
	}

	public void setBackground(Drawable background) {
		button.setBackground(background);
	}

	/**
	 * Flips the card (change background to front side)
	 */
	@SuppressLint("NewApi")
	public void flip() {
		button.setBackground(frontSide);
	}

	/**
	 * Flips the card back to turned state.
	 */
	@SuppressLint("NewApi")
	public void flipBack() {
		button.setBackground(Game.backSide);
	}

	/**
	 * Check if card's drawable front side is same as compared card.
	 * 
	 * @param card
	 *            Card that will be compared.
	 * @return <b> boolean </b> if card is same returns <i> true </i> otherwise
	 *         returns <i> false </i>
	 */
	public boolean checkPair(Card card) {
		return frontSide.equals(card.frontSide);
	}

	/**
	 * Disables the display button.
	 */
	public void disable() {
		button.setEnabled(false);
		button.setVisibility(View.INVISIBLE);
	}

	public void refresh() {
		button.postInvalidate();
	}
}
