package com.hiteshsahu.awesome_gallery.view.adapter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ImageViewTarget
import com.hiteshsahu.awesome_gallery.R
import com.hiteshsahu.awesome_gallery.modal.CenterRepository
import com.hiteshsahu.awesome_gallery.util.PaletteBitmap
import com.hiteshsahu.awesome_gallery.util.PaletteBitmapTranscoder
import com.hiteshsahu.awesome_gallery.view.widget.TouchImageView
import java.util.*

class MyAdapter(private val context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.gallery_page, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val url: String? = CenterRepository.instance
                .imageCollection.getImageAt(position).imagePath

        if (url != null) {
            // simulate an optional url from the data item
            holder.touchImageView.visibility = View.VISIBLE
            Glide.with(context)
                    .fromString()
                    .asBitmap()
                    .transcode(PaletteBitmapTranscoder(context), PaletteBitmap::class.java)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .load(url)
                    .into(object : ImageViewTarget<PaletteBitmap>(holder.touchImageView) {
                        override fun setResource(resource: PaletteBitmap) {
                            super.view.setImageBitmap(resource.bitmap)
                            val color = resource.palette.getVibrantColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                            holder.touchImageView.background = ColorDrawable(color)
                        }
                    })
        } else {
            // clear when no image is shown, don't use holder.imageView.setImageDrawable(null) to do the same
            Glide.clear(holder.touchImageView)
            holder.touchImageView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return CenterRepository.instance.imageCollection.listOfImages.size

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val touchImageView = itemView.findViewById<TouchImageView>(R.id.touchImageView)

        init {


        }
    }
}