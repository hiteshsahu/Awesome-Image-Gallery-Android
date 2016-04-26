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

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

// TODO: Auto-generated Javadoc
/**
 * The Class JazzyListView.
 */
public class JazzyListView extends ListView {

    /** The m helper. */
    private final JazzyHelper mHelper;

    /**
     * Instantiates a new jazzy list view.
     *
     * @param context the context
     */
    public JazzyListView(Context context) {
        super(context);
        mHelper = init(context, null);
    }

    /**
     * Instantiates a new jazzy list view.
     *
     * @param context the context
     * @param attrs the attrs
     */
    public JazzyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = init(context, attrs);
    }

    /**
     * Instantiates a new jazzy list view.
     *
     * @param context the context
     * @param attrs the attrs
     * @param defStyle the def style
     */
    public JazzyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHelper = init(context, attrs);
    }

    /**
     * Inits the.
     *
     * @param context the context
     * @param attrs the attrs
     * @return the jazzy helper
     */
    private JazzyHelper init(Context context, AttributeSet attrs) {
        JazzyHelper helper = new JazzyHelper(context, attrs);
        super.setOnScrollListener(helper);
        return helper;
    }

    /* (non-Javadoc)
     * @see android.widget.AbsListView#setOnScrollListener(android.widget.AbsListView.OnScrollListener)
     */
    @Override
    public final void setOnScrollListener(OnScrollListener l) {
        mHelper.setOnScrollListener(l);
    }

    /**
     * Sets the desired transition effect.
     *
     * @param transitionEffect Numeric constant representing a bundled transition effect.
     */
    public void setTransitionEffect(int transitionEffect ) {
        mHelper.setTransitionEffect(transitionEffect);
    }

    /**
     * Sets the desired transition effect.
     *
     * @param transitionEffect The non-bundled transition provided by the client.
     */
    public void setTransitionEffect(JazzyEffect transitionEffect) {
        mHelper.setTransitionEffect(transitionEffect);
    }

    /**
     * Sets whether new items or all items should be animated when they become visible.
     *
     * @param onlyAnimateNew True if only new items should be animated; false otherwise.
     */
    public void setShouldOnlyAnimateNewItems(boolean onlyAnimateNew) {
        mHelper.setShouldOnlyAnimateNewItems(onlyAnimateNew);
    }

    /**
     * If true animation will only occur when scrolling without the users finger on the screen.
     *
     * @param onlyWhenFling the new should only animate fling
     */
    public void setShouldOnlyAnimateFling(boolean onlyWhenFling) {
        mHelper.setShouldOnlyAnimateFling(onlyWhenFling);
    }

    /**
     * Stop animations after the list has reached a certain velocity. When the list slows down
     * it will start animating again. This gives a performance boost as well as preventing
     * the list from animating under the users finger if they suddenly stop it.
     *
     * @param itemsPerSecond the new max animation velocity
     */
    public void setMaxAnimationVelocity(int itemsPerSecond) {
        mHelper.setMaxAnimationVelocity(itemsPerSecond);
    }

    /**
     * Enable this if you are using a list with items that should act like grid items.
     *
     * @param simulateGridWithList the new simulate grid with list
     */
    public void setSimulateGridWithList(boolean simulateGridWithList) {
        mHelper.setSimulateGridWithList(simulateGridWithList);
        setClipChildren(!simulateGridWithList);
    }

}
