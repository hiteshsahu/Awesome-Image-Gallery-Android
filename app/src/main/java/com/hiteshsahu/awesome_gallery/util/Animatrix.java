package com.hiteshsahu.awesome_gallery.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.SeekBar;

/**
 * Created by Hitesh on 12-10-2016.
 */
public class Animatrix {

    public static void scale(View view, long delay) {
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate()
                .scaleX(1)
                .scaleY(1)
                .setDuration(500)
                .setStartDelay(delay)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    public static void slideUp(View view, long delay) {
        view.setTranslationY(0);
        view.animate()
                .scaleY(1)
                .setDuration(500)
                .setStartDelay(delay)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    public static void animateSeekBar(SeekBar seekBar) {
        seekBar.setProgress(15);

        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(seekBar, "progress", 15, 0);
        progressAnimator.setDuration(300);
        progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnimator.start();
    }

    public static void circularRevealView(View revealLayout) {

        int cx = revealLayout.getWidth() / 2;
        int cy = revealLayout.getHeight() / 2;

        float finalRadius = Math.max(revealLayout.getWidth(), revealLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils.createCircularReveal(revealLayout, cx, cy, 0, finalRadius);

            circularReveal.setDuration(1000);

            // make the view visible and start the animation
            revealLayout.setVisibility(View.VISIBLE);

            circularReveal.start();
        } else {
            revealLayout.setVisibility(View.VISIBLE);
        }

    }

}
