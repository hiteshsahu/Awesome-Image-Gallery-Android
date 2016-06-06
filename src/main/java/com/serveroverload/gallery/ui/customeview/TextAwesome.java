package com.serveroverload.gallery.ui.customeview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextAwesome extends TextView {

	private final static String NAME = "FONTAWESOME";
	private static LruCache<String, Typeface> sTypefaceCache = new LruCache<String, Typeface>(
			12);

	public TextAwesome(Context context) {
		super(context);
		init();

	}

	public TextAwesome(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {

		Typeface typeface = sTypefaceCache.get(NAME);

		if (typeface == null) {

			typeface = Typeface.createFromAsset(getContext().getAssets(),
					"fontawesome-webfont.ttf");
			sTypefaceCache.put(NAME, typeface);

		}

		setTypeface(typeface);

	}

}
