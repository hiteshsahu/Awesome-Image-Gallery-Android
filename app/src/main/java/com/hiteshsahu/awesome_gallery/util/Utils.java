package com.hiteshsahu.awesome_gallery.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Hitesh on 08-07-2016.
 */
public class Utils {

    // declare the builder object once.
    private static TextDrawable.IBuilder builder = TextDrawable.builder()
            .beginConfig()
            .withBorder(4)
            .toUpperCase()
            .endConfig()
            .rect();

    private static ColorGenerator generator = ColorGenerator.MATERIAL;

    public static TextDrawable generatePlaceHolder(String key) {

        // reuse the builder specs to create multiple drawables
        return builder.build(String.valueOf(key.charAt(0)), generator.getColor(key));
    }

    final static Random mRandom = new Random();
    ;

    public static int generateRandomColor() {// This is the base color which will be
        // mixed with the generated one
        final int baseColor = Color.DKGRAY;
        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);
        final int red = (baseRed + mRandom.nextInt(256)) / 2;
        final int green = (baseGreen + mRandom.nextInt(256)) / 2;
        final int blue = (baseBlue + mRandom.nextInt(256)) / 2;
        return Color.rgb(red, green, blue);
    }


    /**
     * Cache fonts for eficieny and faster loading
     */
    public static class FontCache {

        private static HashMap<String, Typeface> fontCache = new HashMap<>();

        private static final String TTF = ".otf";

        public static Typeface getTypeface(String fontname, Context context) {

            Typeface typeface = fontCache.get(fontname);

            if (typeface == null) {
                try {
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontname + TTF);
                } catch (Exception e) {

                    //return null so that Android can use default font
                    return null;
                }

                fontCache.put(fontname, typeface);
            }

            return typeface;
        }
    }


    // I consider a tablet to have at least a 6.5 inch screen. This is how to compute it, based on Nolf's answer above.
    public static boolean isTablet(Activity appContext) {
        DisplayMetrics metrics = new DisplayMetrics();
        appContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6.5) {
            // 6.5inch device or bigger
            return true;
        } else {
            // smaller device
            return false;
        }
    }
}
