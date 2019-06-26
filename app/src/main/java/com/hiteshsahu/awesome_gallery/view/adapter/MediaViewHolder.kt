package com.hiteshsahu.awesome_gallery.view.adapter

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.hiteshsahu.awesome_gallery.R


class MediaViewHolder
/**
 * @param v
 */
(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v), View.OnClickListener {

    val mediaName: TextView = v.findViewById<View>(R.id.mediaName) as TextView
    val mediaDesc: TextView = v.findViewById<View>(R.id.media_location) as TextView
    val mediaImage: ImageView = v.findViewById<View>(R.id.image) as ImageView
    val container: androidx.cardview.widget.CardView = v.findViewById<View>(R.id.list_container) as androidx.cardview.widget.CardView
    val containerbg: LinearLayout = v.findViewById<View>(R.id.list_container_bg) as LinearLayout
    val menu: View = v.findViewById(R.id.overflow) as View

    fun clearAnimation() {
        container.clearAnimation()
    }

    override fun onClick(view: View) {

    }
}