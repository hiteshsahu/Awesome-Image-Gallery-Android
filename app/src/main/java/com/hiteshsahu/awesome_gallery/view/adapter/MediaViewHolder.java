package com.hiteshsahu.awesome_gallery.view.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hiteshsahu.awesome_gallery.R;


public class MediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mediaName, mediaDesc;
    private ImageView mediaImage;
    private CardView container;
    private LinearLayout containerbg;
    private View menu;


    public LinearLayout getContainerbg() {
        return containerbg;
    }

    /**
     * @param v
     */
    public MediaViewHolder(View v) {
        super(v);
        mediaName = (TextView) v.findViewById(R.id.mediaName);
        mediaDesc = (TextView) v.findViewById(R.id.product_desc);
        mediaImage = (ImageView) v.findViewById(R.id.product_image);
        container = (CardView) v.findViewById(R.id.list_container);
        containerbg = (LinearLayout) v.findViewById(R.id.list_container_bg);
        menu = (View) v.findViewById(R.id.overflow);
    }

    public ImageView getMediaImage() {
        return mediaImage;
    }

    public TextView getMediaName() {
        return mediaName;
    }

    public TextView getMediaDesc() {
        return mediaDesc;
    }

    public CardView getContainer() {
        return container;
    }

    public void clearAnimation() {
        container.clearAnimation();
    }

    @Override
    public void onClick(View view) {

    }

    public View getMenu() {
        return menu;
    }
}