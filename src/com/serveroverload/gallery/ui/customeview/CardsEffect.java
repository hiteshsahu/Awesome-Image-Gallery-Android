package com.serveroverload.gallery.ui.customeview;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewPropertyAnimator;

// TODO: Auto-generated Javadoc
/**
 * The Class CardsEffect.
 */
@SuppressLint("NewApi")
public class CardsEffect implements JazzyEffect {

	/** The Constant CARD_EFFECT. */
	// List Anime 14 Anime support
	public static final int CARD_EFFECT = 10;

	/** The Constant CURL_EFFECT. */
	public static final int CURL_EFFECT = 11;

	/** The Constant FADE_EFFECT. */
	public static final int FADE_EFFECT = 12;

	/** The Constant FAN_EFFECT. */
	public static final int FAN_EFFECT = 13;

	/** The Constant FLIP_EFFECT. */
	public static final int FLIP_EFFECT = 14;

	/** The Constant FLY_EFFECT. */
	public static final int FLY_EFFECT = 15;

	/** The Constant GROW_EFFECT. */
	public static final int GROW_EFFECT = 16;

	/** The Constant HELIX_EFFECT. */
	public static final int HELIX_EFFECT = 17;

	/** The Constant REVERSE_FLY_EFFECT. */
	public static final int REVERSE_FLY_EFFECT = 18;

	/** The Constant SLIDE_IN_EFFECT. */
	public static final int SLIDE_IN_EFFECT = 19;

	/** The Constant STANDARD_EFFECT. */
	public static final int STANDARD_EFFECT = 20;

	/** The Constant TILT_EFFECT. */
	public static final int TILT_EFFECT = 21;

	/** The Constant TWIRL_EFFECT. */
	public static final int TWIRL_EFFECT = 22;

	/** The Constant WAVE_EFFECT. */
	public static final int WAVE_EFFECT = 23;

	/** The Constant ZIPPER_EFFECT. */
	public static final int ZIPPER_EFFECT = 24;

	/** The animation key. */
	int animationKey;

	/** The Constant INITIAL_SCALE_FACTOR. */
	private static final float INITIAL_SCALE_FACTOR = 0.01f;

	/** The Constant TILT_INITIAL_SCALE_FACTOR. */
	private static final float TILT_INITIAL_SCALE_FACTOR = 0.7f;

	/** The Constant INITIAL_ROTATION_ANGLE. */
	private static final int INITIAL_ROTATION_ANGLE = 90;

	/** The Constant HELIX_INITIAL_ROTATION_ANGLE. */
	private static final int HELIX_INITIAL_ROTATION_ANGLE = 180;

	/** The Constant FAN_INITIAL_ROTATION_ANGLE. */
	private static final int FAN_INITIAL_ROTATION_ANGLE = 70;

	/** The Constant FLY_INITIAL_ROTATION_ANGLE. */
	private static final int FLY_INITIAL_ROTATION_ANGLE = 135;

	/** The Constant INITIAL_ROTATION_X. */
	private static final int INITIAL_ROTATION_X = 80;

	/** The Constant INITIAL_ROTATION_Y. */
	private static final int INITIAL_ROTATION_Y = 70;

	/** The Constant INITIAL_ROTATION_Z. */
	private static final int INITIAL_ROTATION_Z = 10;

	/** The Constant REVERSE_FLY_INITIAL_ROTATION_ANGLE. */
	private static final int REVERSE_FLY_INITIAL_ROTATION_ANGLE = 135;

	/** The Constant DURATION_MULTIPLIER. */
	private static final int DURATION_MULTIPLIER = 5;

	/** The Constant TWIRL_INITIAL_ROTATION_X. */
	private static final int TWIRL_INITIAL_ROTATION_X = 80;

	/** The Constant TWIRL_INITIAL_ROTATION_Y. */
	private static final int TWIRL_INITIAL_ROTATION_Y = 70;

	/** The Constant TWIRL_INITIAL_ROTATION_Z. */
	private static final int TWIRL_INITIAL_ROTATION_Z = 10;

	/**
	 * Instantiates a new cards effect.
	 *
	 * @param animationKey
	 *            the animation key
	 */
	public CardsEffect(int animationKey) {
		this.animationKey = animationKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.serveroverload.speedyturtle.customView.JazzyEffect#initView(android.
	 * view.View, int, int)
	 */
	@Override
	public void initView(View item, int position, int scrollDirection) {

		switch (animationKey) {
		case CARD_EFFECT:

			item.setPivotX(item.getWidth() / 2);
			item.setPivotY(item.getHeight() / 2);
			item.setRotationX(INITIAL_ROTATION_ANGLE * scrollDirection);
			item.setTranslationY(item.getHeight() * scrollDirection);

			break;

		case CURL_EFFECT:

			item.setPivotX(0);
			item.setPivotY(item.getHeight() / 2);
			item.setRotationY(INITIAL_ROTATION_ANGLE);

			break;

		case FADE_EFFECT:

			item.setAlpha(JazzyHelper.TRANSPARENT);
			break;

		case FAN_EFFECT:

			item.setPivotX(0);
			item.setPivotY(0);
			item.setRotation(FAN_INITIAL_ROTATION_ANGLE * scrollDirection);
			break;

		case FLIP_EFFECT:

			item.setPivotX(item.getWidth() / 2);
			item.setPivotY(item.getHeight() / 2);
			item.setRotationX(-INITIAL_ROTATION_ANGLE * scrollDirection);

			break;
		case FLY_EFFECT:

			item.setPivotX(item.getWidth() / 2);
			item.setPivotY(item.getHeight() / 2);
			item.setRotationX(-FLY_INITIAL_ROTATION_ANGLE * scrollDirection);
			item.setTranslationY(item.getHeight() * 2 * scrollDirection);

			break;

		case GROW_EFFECT:

			item.setPivotX(item.getWidth() / 2);
			item.setPivotY(item.getHeight() / 2);
			item.setScaleX(INITIAL_SCALE_FACTOR);
			item.setScaleY(INITIAL_SCALE_FACTOR);
			break;

		case HELIX_EFFECT:

			item.setRotationY(HELIX_INITIAL_ROTATION_ANGLE);

			break;

		case REVERSE_FLY_EFFECT:

			item.setPivotX(item.getWidth() / 2);
			item.setPivotY(item.getHeight() / 2);
			item.setRotationX(REVERSE_FLY_INITIAL_ROTATION_ANGLE * scrollDirection);
			item.setTranslationY(-item.getHeight() * 2 * scrollDirection);
			break;

		case SLIDE_IN_EFFECT:

			item.setTranslationY(item.getHeight() / 2 * scrollDirection);

			break;

		case STANDARD_EFFECT:

			break;

		case TILT_EFFECT:
			item.setPivotX(item.getWidth() / 2);
			item.setPivotY(item.getHeight() / 2);
			item.setScaleX(TILT_INITIAL_SCALE_FACTOR);
			item.setScaleY(TILT_INITIAL_SCALE_FACTOR);
			item.setTranslationY(item.getHeight() / 2 * scrollDirection);
			item.setAlpha(JazzyHelper.OPAQUE / 2);

			break;

		case TWIRL_EFFECT:

			item.setPivotX(item.getWidth() / 2);
			item.setPivotY(item.getWidth() / 2);
			item.setRotationX(TWIRL_INITIAL_ROTATION_X);
			item.setRotationY(TWIRL_INITIAL_ROTATION_Y * scrollDirection);
			item.setRotation(TWIRL_INITIAL_ROTATION_Z);

			break;

		case WAVE_EFFECT:

			item.setTranslationX(-item.getWidth());
			break;

		case ZIPPER_EFFECT:
			boolean isEven = position % 2 == 0;
			item.setTranslationX((isEven ? -1 : 1) * item.getWidth());

			break;

		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.serveroverload.speedyturtle.customView.JazzyEffect#setupAnimation(
	 * android.view.View, int, int, android.view.ViewPropertyAnimator)
	 */
	@Override
	public void setupAnimation(View item, int position, int scrollDirection, ViewPropertyAnimator animator) {

		switch (animationKey) {
		case CARD_EFFECT:

			animator.rotationXBy(-INITIAL_ROTATION_ANGLE * scrollDirection)
					.translationYBy(-item.getHeight() * scrollDirection);

			break;

		case CURL_EFFECT:
			animator.rotationYBy(-INITIAL_ROTATION_ANGLE);

			break;

		case FADE_EFFECT:

			animator.setDuration(JazzyHelper.DURATION * DURATION_MULTIPLIER).alpha(JazzyHelper.OPAQUE);
			break;

		case FAN_EFFECT:
			animator.rotationBy(-FAN_INITIAL_ROTATION_ANGLE * scrollDirection);
			break;

		case FLIP_EFFECT:

			animator.rotationXBy(INITIAL_ROTATION_ANGLE * scrollDirection);
			break;
		case FLY_EFFECT:

			animator.rotationXBy(FLY_INITIAL_ROTATION_ANGLE * scrollDirection)
					.translationYBy(-item.getHeight() * 2 * scrollDirection);

			break;

		case GROW_EFFECT:
			animator.scaleX(1).scaleY(1);
			break;

		case HELIX_EFFECT:

			animator.rotationYBy(HELIX_INITIAL_ROTATION_ANGLE * scrollDirection);

			break;

		case REVERSE_FLY_EFFECT:

			animator.rotationXBy(-REVERSE_FLY_INITIAL_ROTATION_ANGLE * scrollDirection)
					.translationYBy(item.getHeight() * 2 * scrollDirection);

			break;

		case SLIDE_IN_EFFECT:

			animator.translationYBy(-item.getHeight() / 2 * scrollDirection);

			break;

		case STANDARD_EFFECT:

			break;

		case TILT_EFFECT:
			animator.translationYBy(-item.getHeight() / 2 * scrollDirection).scaleX(1).scaleY(1)
					.alpha(JazzyHelper.OPAQUE);

			break;

		case TWIRL_EFFECT:
			animator.rotationXBy(-TWIRL_INITIAL_ROTATION_X).rotationYBy(-TWIRL_INITIAL_ROTATION_Y * scrollDirection)
					.rotationBy(-TWIRL_INITIAL_ROTATION_Z);

			break;

		case WAVE_EFFECT:

			animator.translationX(0);
			break;

		case ZIPPER_EFFECT:
			animator.translationX(0);
			break;

		default:
			break;
		}
	}
}
