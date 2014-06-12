package com.gnox.memorygame;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Class that contains tools to manipulate with image, cutting image to frames.
 * Constructor has one parameter, resource from main activity, so that it can
 * load images by ids.
 */
public class ImageLoader {

	private final Resources resources;
	private int frameWidth;
	private int frameHeight;
	private Bitmap image;
	private List<Drawable> images;

	public ImageLoader(Resources resources) {
		this.resources = resources;
		images = new ArrayList<Drawable>();
	}

	public List<Drawable> getImageList() {
		return images;
	}

	/**
	 * Adds Drawable image into List by its id.
	 * 
	 * @param resourceId
	 *            Resource id.
	 */
	public void addImage(int resourceId) {
		try {
			images.add(resources.getDrawable(resourceId));
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds particular frame of image that was previously loaded with
	 * setImageFrame function.
	 * 
	 * @param horizontalPos
	 *            Position of horizontal image.
	 * @param verticalPos
	 *            Position of vertical image.
	 */
	public void addCroppedImage(int horizontalPos, int verticalPos) {
		int startX = frameWidth * horizontalPos;
		int startY = frameHeight * verticalPos;

		images.add(new BitmapDrawable(resources, Bitmap.createBitmap(image, startX, startY, frameWidth, frameHeight)));
	}

	/**
	 * Sets frame for image and the image itself, so it can be worked with until
	 * this function is not called again. Function sets width and height of
	 * frame what can be used to get one particular frame from image.
	 * 
	 * @param image
	 *            Image to get height and width.
	 * @param frameHeight
	 *            Height of one frame piece.
	 * @param frameWidth
	 *            Width of one frame piece.
	 */
	public void setImageFrame(Bitmap image, int frameHeight, int frameWidth) {
		if (image != null) {
			this.image = image;
			this.frameHeight = image.getHeight() / frameHeight;
			this.frameWidth = image.getWidth() / frameWidth;
		}
	}

	/**
	 * Overloaded function setImageFrame, that takes resource ID as opposed to
	 * image. <br>
	 * Sets frame for image and the image itself, so it can be worked with until
	 * this function is not called again. Function sets width and height of
	 * frame what can be used to get one particular frame from image.
	 * 
	 * @param resourceId
	 *            Id of image.
	 * @param frameHeight
	 *            Height of one frame piece.
	 * @param frameWidth
	 *            Width of one frame piece.
	 */
	public void setImageFrame(int resourceId, int frameHeight, int frameWidth) {
		try {
			Bitmap bMap = BitmapFactory.decodeResource(resources, resourceId);
			setImageFrame(bMap, frameHeight, frameWidth);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	public void test() {
		images.add(new BitmapDrawable(resources, Bitmap.createBitmap(image, 0, 0, image.getWidth() / 4,
				image.getHeight() / 4)));
	}
}
