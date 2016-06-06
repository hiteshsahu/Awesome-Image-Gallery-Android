package com.serveroverload.gallery.adapter;


import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.serveroverload.gallery.adapter.model.ImageDataModel;
import com.serveroverload.gallery.ui.customeview.TextDrawable;
import com.serveroverload.gallery.ui.customeview.TextDrawable.IBuilder;
import com.serveroverload.gallery.ui.customeview.TouchImageView;
import com.serveroverload.gallery.util.ColorGenerator;

import java.util.ArrayList;

public class TouchImageAdapter extends PagerAdapter {

    public TouchImageAdapter(ArrayList<ImageDataModel> imageList) {
        this.imageList = imageList;
    }

    private ArrayList<ImageDataModel> imageList;

    private IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    @Override
    public void notifyDataSetChanged() {
        // TODO Auto-generated method stub
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public int getItemPosition(Object object) {

        return super.getItemPosition(object);
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        TouchImageView img = new TouchImageView(container.getContext());

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4).endConfig().roundRect(10);

        drawable = mDrawableBuilder.build(String.valueOf(imageList.get(position).getImageTitle().charAt(0)),
                mColorGenerator.getColor(imageList.get(position).getImageTitle()));

        Glide.with(container.getContext()).load(imageList.get(position).getImagePath()).placeholder(drawable)
                .error(drawable).into(img);

        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        return img;
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