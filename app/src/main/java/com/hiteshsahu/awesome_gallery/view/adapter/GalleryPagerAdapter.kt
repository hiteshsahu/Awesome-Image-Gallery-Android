package com.hiteshsahu.awesome_gallery.view.adapter


import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ImageViewTarget
import com.hiteshsahu.awesome_gallery.R
import com.hiteshsahu.awesome_gallery.modal.CenterRepository
import com.hiteshsahu.awesome_gallery.util.PaletteBitmap
import com.hiteshsahu.awesome_gallery.util.PaletteBitmapTranscoder
import com.hiteshsahu.awesome_gallery.view.widget.TouchImageView


class GalleryPagerAdapter : androidx.viewpager.widget.PagerAdapter() {

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getCount(): Int {
        return CenterRepository.instance.imageCollection.listOfImages.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {

        val pageView = LayoutInflater.from(container.context).inflate(R.layout.gallery_page, container, false)

        val touchImageView = pageView.findViewById<TouchImageView>(R.id.touchImageView)

        val url: String? = CenterRepository.instance
                .imageCollection.getImageAt(position).imagePath

        if (url != null) {
            // simulate an optional url from the data item
            touchImageView.visibility = View.VISIBLE
            Glide.with(container.context)
                    .fromString()
                    .asBitmap()
                    .transcode(PaletteBitmapTranscoder(container.context), PaletteBitmap::class.java)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .load(url)
                    .into(object : ImageViewTarget<PaletteBitmap>(touchImageView) {
                        override fun setResource(resource: PaletteBitmap) {
                            super.view.setImageBitmap(resource.bitmap)
                            val color = resource.palette.getVibrantColor(ContextCompat.getColor(container.context, com.hiteshsahu.awesome_gallery.R.color.colorPrimaryDark))
                            touchImageView.background = ColorDrawable(color)
                        }
                    })
        } else {
            // clear when no image is shown, don't use holder.imageView.setImageDrawable(null) to do the same
            Glide.clear(touchImageView)
            touchImageView.visibility = View.GONE
        }

        container.addView(pageView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)

        return pageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

}