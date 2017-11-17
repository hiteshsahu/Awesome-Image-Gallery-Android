package com.hiteshsahu.awesome_gallery.view.adapter;


import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hiteshsahu.awesome_gallery.modal.CenterRepository;
import com.hiteshsahu.awesome_gallery.view.widget.TouchImageView;

public class TouchImageAdapter extends PagerAdapter {

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return CenterRepository.getInstance().getImageCollection().getListOfImages().size();
    }
//
//    @Override
//    public int getItemPosition(Object object) {
//
//        return super.getItemPosition(object);
//    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        TouchImageView touchImageView = new TouchImageView(container.getContext());

        Glide.with(container.getContext()).load(CenterRepository.getInstance().getImageCollection().getImageAt(position).getImagePath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade().fitCenter()
                .into(touchImageView);

        container.addView(touchImageView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        return touchImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}