/*
 * Copyright (C) 2015 Two Toasters
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.serveroverload.gallery.ui.customeview;

import java.util.HashSet;

import com.example.test.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;

// TODO: Auto-generated Javadoc
/**
 * The Class JazzyHelper.
 */
@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class JazzyHelper implements AbsListView.OnScrollListener {

	/** The Constant STANDARD. */
	public static final int STANDARD = 0;

	/** The Constant GROW. */
	public static final int GROW = 1;

	/** The Constant CARDS. */
	public static final int CARDS = 2;

	/** The Constant CURL. */
	public static final int CURL = 3;

	/** The Constant WAVE. */
	public static final int WAVE = 4;

	/** The Constant FLIP. */
	public static final int FLIP = 5;

	/** The Constant FLY. */
	public static final int FLY = 6;

	/** The Constant REVERSE_FLY. */
	public static final int REVERSE_FLY = 7;

	/** The Constant HELIX. */
	public static final int HELIX = 8;

	/** The Constant FAN. */
	public static final int FAN = 9;

	/** The Constant TILT. */
	public static final int TILT = 10;

	/** The Constant ZIPPER. */
	public static final int ZIPPER = 11;

	/** The Constant FADE. */
	public static final int FADE = 12;

	/** The Constant TWIRL. */
	public static final int TWIRL = 13;

	/** The Constant SLIDE_IN. */
	public static final int SLIDE_IN = 14;

	/** The Constant DURATION. */
	public static final int DURATION = 300;

	/** The Constant TRANSPARENT. */
	public static final int OPAQUE = 255, TRANSPARENT = 0;

	/** The m transition effect. */
	private JazzyEffect mTransitionEffect = null;

	/** The m is scrolling. */
	private boolean mIsScrolling = false;

	/** The m first visible item. */
	private int mFirstVisibleItem = -1;

	/** The m last visible item. */
	private int mLastVisibleItem = -1;

	/** The m previous first visible item. */
	private int mPreviousFirstVisibleItem = 0;

	/** The m previous event time. */
	private long mPreviousEventTime = 0;

	/** The m speed. */
	private double mSpeed = 0;

	/** The m max velocity. */
	private int mMaxVelocity = 0;

	/** The Constant MAX_VELOCITY_OFF. */
	public static final int MAX_VELOCITY_OFF = 0;

	/** The m additional on scroll listener. */
	private AbsListView.OnScrollListener mAdditionalOnScrollListener;

	/** The m only animate new items. */
	private boolean mOnlyAnimateNewItems;

	/** The m only animate on fling. */
	private boolean mOnlyAnimateOnFling;

	/** The m is fling event. */
	private boolean mIsFlingEvent;

	/** The m simulate grid with list. */
	private boolean mSimulateGridWithList;

	/** The m already animated items. */
	private final HashSet<Integer> mAlreadyAnimatedItems;

	/**
	 * Instantiates a new jazzy helper.
	 */
	public JazzyHelper() {
		this(null, null);
	}

	/**
	 * Instantiates a new jazzy helper.
	 *
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public JazzyHelper(Context context, AttributeSet attrs) {
		mAlreadyAnimatedItems = new HashSet<>();
		int transitionEffect = HELIX;
		int maxVelocity = 0;

		if (context != null && attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.JazzyListView);
			transitionEffect = a.getInteger(R.styleable.JazzyListView_effect, STANDARD);
			maxVelocity = a.getInteger(R.styleable.JazzyListView_max_velocity, MAX_VELOCITY_OFF);
			mOnlyAnimateNewItems = a.getBoolean(R.styleable.JazzyListView_only_animate_new_items, false);
			mOnlyAnimateOnFling = a.getBoolean(R.styleable.JazzyListView_max_velocity, false);
			mSimulateGridWithList = a.getBoolean(R.styleable.JazzyListView_simulate_grid_with_list, false);
			a.recycle();
		}

		setTransitionEffect(transitionEffect);
		setMaxAnimationVelocity(maxVelocity);
	}

	/**
	 * Sets the on scroll listener.
	 *
	 * @param l
	 *            the new on scroll listener
	 */
	public void setOnScrollListener(AbsListView.OnScrollListener l) {
		// hijack the scroll listener setter and have this list also notify the
		// additional listener
		mAdditionalOnScrollListener = l;
	}

	/**
	 * On scroll.
	 *
	 * @param view
	 *            the view
	 * @param firstVisibleItem
	 *            the first visible item
	 * @param visibleItemCount
	 *            the visible item count
	 * @param totalItemCount
	 *            the total item count
	 * @see AbsListView.OnScrollListener#onScroll
	 */
	@Override
	public final void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		onScrolled(view, firstVisibleItem, visibleItemCount, totalItemCount);
		notifyAdditionalOnScrollListener(view, firstVisibleItem, visibleItemCount, totalItemCount);
	}

	/**
	 * On scrolled.
	 *
	 * @param viewGroup
	 *            the view group
	 * @param firstVisibleItem
	 *            the first visible item
	 * @param visibleItemCount
	 *            the visible item count
	 * @param totalItemCount
	 *            the total item count
	 */
	public final void onScrolled(ViewGroup viewGroup, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		boolean shouldAnimateItems = (mFirstVisibleItem != -1 && mLastVisibleItem != -1);

		int lastVisibleItem = firstVisibleItem + visibleItemCount - 1;
		if (mIsScrolling && shouldAnimateItems) {
			setVelocity(firstVisibleItem, totalItemCount);
			int indexAfterFirst = 0;
			while (firstVisibleItem + indexAfterFirst < mFirstVisibleItem) {
				View item = viewGroup.getChildAt(indexAfterFirst);
				doJazziness(item, firstVisibleItem + indexAfterFirst, -1);
				indexAfterFirst++;
			}

			int indexBeforeLast = 0;
			while (lastVisibleItem - indexBeforeLast > mLastVisibleItem) {
				View item = viewGroup.getChildAt(lastVisibleItem - firstVisibleItem - indexBeforeLast);
				doJazziness(item, lastVisibleItem - indexBeforeLast, 1);
				indexBeforeLast++;
			}
		} else if (!shouldAnimateItems) {
			for (int i = firstVisibleItem; i < visibleItemCount; i++) {
				mAlreadyAnimatedItems.add(i);
			}
		}

		mFirstVisibleItem = firstVisibleItem;
		mLastVisibleItem = lastVisibleItem;
	}

	/**
	 * Should be called in onScroll to keep take of current Velocity.
	 *
	 * @param firstVisibleItem
	 *            The index of the first visible item in the ListView.
	 * @param totalItemCount
	 *            the total item count
	 */
	private void setVelocity(int firstVisibleItem, int totalItemCount) {
		if (mMaxVelocity > MAX_VELOCITY_OFF && mPreviousFirstVisibleItem != firstVisibleItem) {
			long currTime = System.currentTimeMillis();
			long timeToScrollOneItem = currTime - mPreviousEventTime;
			if (timeToScrollOneItem < 1) {
				double newSpeed = ((1.0d / timeToScrollOneItem) * 1000);
				// We need to normalize velocity so different size item don't
				// give largely different velocities.
				if (newSpeed < (0.9f * mSpeed)) {
					mSpeed *= 0.9f;
				} else if (newSpeed > (1.1f * mSpeed)) {
					mSpeed *= 1.1f;
				} else {
					mSpeed = newSpeed;
				}
			} else {
				mSpeed = ((1.0d / timeToScrollOneItem) * 1000);
			}

			mPreviousFirstVisibleItem = firstVisibleItem;
			mPreviousEventTime = currTime;
		}
	}

	/**
	 * Initializes the item view and triggers the animation.
	 *
	 * @param item
	 *            The view to be animated.
	 * @param position
	 *            The index of the view in the list.
	 * @param scrollDirection
	 *            Positive number indicating scrolling down, or negative number
	 *            indicating scrolling up.
	 */
	private void doJazziness(View item, int position, int scrollDirection) {
		if (mIsScrolling) {
			if (mOnlyAnimateNewItems && mAlreadyAnimatedItems.contains(position))
				return;

			if (mOnlyAnimateOnFling && !mIsFlingEvent)
				return;

			if (mMaxVelocity > MAX_VELOCITY_OFF && mMaxVelocity < mSpeed)
				return;

			if (mSimulateGridWithList) {
				ViewGroup itemRow = (ViewGroup) item;
				for (int i = 0; i < itemRow.getChildCount(); i++)
					doJazzinessImpl(itemRow.getChildAt(i), position, scrollDirection);
			} else {
				doJazzinessImpl(item, position, scrollDirection);
			}

			mAlreadyAnimatedItems.add(position);
		}
	}

	/**
	 * Do jazziness impl.
	 *
	 * @param item
	 *            the item
	 * @param position
	 *            the position
	 * @param scrollDirection
	 *            the scroll direction
	 */
	private void doJazzinessImpl(View item, int position, int scrollDirection) {
		ViewPropertyAnimator animator = item.animate().setDuration(DURATION)
				.setInterpolator(new AccelerateDecelerateInterpolator());

		scrollDirection = scrollDirection > 0 ? 1 : -1;
		mTransitionEffect.initView(item, position, scrollDirection);
		mTransitionEffect.setupAnimation(item, position, scrollDirection, animator);
		animator.start();
	}

	/**
	 * On scroll state changed.
	 *
	 * @param view
	 *            the view
	 * @param scrollState
	 *            the scroll state
	 * @see AbsListView.OnScrollListener#onScrollStateChanged
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
			mIsScrolling = false;
			mIsFlingEvent = false;
			break;
		case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
			mIsFlingEvent = true;
			break;
		case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			mIsScrolling = true;
			mIsFlingEvent = false;
			break;
		default:
			break;
		}
		notifyAdditionalOnScrollStateChangedListener(view, scrollState);
	}

	/**
	 * Sets the transition effect.
	 *
	 * @param transitionEffect
	 *            the new transition effect
	 */
	public void setTransitionEffect(int transitionEffect) {
		switch (transitionEffect) {
		case STANDARD:
			setTransitionEffect(new CardsEffect(CardsEffect.STANDARD_EFFECT));
			break;
		case GROW:
			setTransitionEffect(new CardsEffect(CardsEffect.GROW_EFFECT));
			break;
		case CARDS:
			setTransitionEffect(new CardsEffect(CardsEffect.CARD_EFFECT));
			break;
		case CURL:
			setTransitionEffect(new CardsEffect(CardsEffect.CURL_EFFECT));
			break;
		case WAVE:
			setTransitionEffect(new CardsEffect(CardsEffect.WAVE_EFFECT));
			break;
		case FLIP:
			setTransitionEffect(new CardsEffect(CardsEffect.FLIP_EFFECT));
			break;
		case FLY:
			setTransitionEffect(new CardsEffect(CardsEffect.FLY_EFFECT));
			break;
		case REVERSE_FLY:
			setTransitionEffect(new CardsEffect(CardsEffect.REVERSE_FLY_EFFECT));
			break;
		case HELIX:
			setTransitionEffect(new CardsEffect(CardsEffect.HELIX_EFFECT));
			break;
		case FAN:
			setTransitionEffect(new CardsEffect(CardsEffect.FAN_EFFECT));
			break;
		case TILT:
			setTransitionEffect(new CardsEffect(CardsEffect.TILT_EFFECT));
			break;
		case ZIPPER:
			setTransitionEffect(new CardsEffect(CardsEffect.ZIPPER_EFFECT));
			break;
		case FADE:
			setTransitionEffect(new CardsEffect(CardsEffect.FADE_EFFECT));
			break;
		case TWIRL:
			setTransitionEffect(new CardsEffect(CardsEffect.TWIRL_EFFECT));
			break;
		case SLIDE_IN:
			setTransitionEffect(new CardsEffect(CardsEffect.SLIDE_IN_EFFECT));
			break;
		default:
			break;
		}
	}

	/**
	 * Sets the transition effect.
	 *
	 * @param transitionEffect
	 *            the new transition effect
	 */
	public void setTransitionEffect(JazzyEffect transitionEffect) {
		mTransitionEffect = transitionEffect;
	}

	/**
	 * Sets the should only animate new items.
	 *
	 * @param onlyAnimateNew
	 *            the new should only animate new items
	 */
	public void setShouldOnlyAnimateNewItems(boolean onlyAnimateNew) {
		mOnlyAnimateNewItems = onlyAnimateNew;
	}

	/**
	 * Sets the should only animate fling.
	 *
	 * @param onlyFling
	 *            the new should only animate fling
	 */
	public void setShouldOnlyAnimateFling(boolean onlyFling) {
		mOnlyAnimateOnFling = onlyFling;
	}

	/**
	 * Sets the max animation velocity.
	 *
	 * @param itemsPerSecond
	 *            the new max animation velocity
	 */
	public void setMaxAnimationVelocity(int itemsPerSecond) {
		mMaxVelocity = itemsPerSecond;
	}

	/**
	 * Sets the simulate grid with list.
	 *
	 * @param simulateGridWithList
	 *            the new simulate grid with list
	 */
	public void setSimulateGridWithList(boolean simulateGridWithList) {
		mSimulateGridWithList = simulateGridWithList;
	}

	/**
	 * Sets the scrolling.
	 *
	 * @param isScrolling
	 *            the new scrolling
	 */
	public void setScrolling(boolean isScrolling) {
		mIsScrolling = isScrolling;
	}

	/**
	 * Sets the fling event.
	 *
	 * @param isFlingEvent
	 *            the new fling event
	 */
	public void setFlingEvent(boolean isFlingEvent) {
		mIsFlingEvent = isFlingEvent;
	}

	/**
	 * Notifies the OnScrollListener of an onScroll event, since JazzyListView
	 * is the primary listener for onScroll events.
	 *
	 * @param view
	 *            the view
	 * @param firstVisibleItem
	 *            the first visible item
	 * @param visibleItemCount
	 *            the visible item count
	 * @param totalItemCount
	 *            the total item count
	 */
	private void notifyAdditionalOnScrollListener(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
		if (mAdditionalOnScrollListener != null) {
			mAdditionalOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	/**
	 * Notifies the OnScrollListener of an onScrollStateChanged event, since
	 * JazzyListView is the primary listener for onScrollStateChanged events.
	 *
	 * @param view
	 *            the view
	 * @param scrollState
	 *            the scroll state
	 */
	private void notifyAdditionalOnScrollStateChangedListener(AbsListView view, int scrollState) {
		if (mAdditionalOnScrollListener != null) {
			mAdditionalOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}
}
