package com.hiteshsahu.awesome_gallery.view.adapter


import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hiteshsahu.awesome_gallery.modal.CenterRepository
import com.hiteshsahu.awesome_gallery.view.widget.TouchImageView

class GalleryPagerAdapter : PagerAdapter() {


    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getCount(): Int {
        return CenterRepository.instance.imageCollection.listOfImages.size
    }


    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val touchImageView = TouchImageView(container.context)

        Glide.with(container.context).load(CenterRepository.instance
                .imageCollection.getImageAt(position).imagePath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade().fitCenter()
                .into(touchImageView)

        container.addView(touchImageView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)

        return touchImageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

}