package com.hiteshsahu.awesome_gallery.view.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.hiteshsahu.awesome_gallery.R


class MediaViewHolder
/**
 * @param v
 */
(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

    val mediaName: TextView = v.findViewById<View>(R.id.mediaName) as TextView
    val mediaDesc: TextView = v.findViewById<View>(R.id.product_desc) as TextView
    val mediaImage: ImageView = v.findViewById<View>(R.id.product_image) as ImageView
    val container: CardView = v.findViewById<View>(R.id.list_container) as CardView
    val containerbg: LinearLayout = v.findViewById<View>(R.id.list_container_bg) as LinearLayout
    val menu: View = v.findViewById(R.id.overflow) as View

    fun clearAnimation() {
        container.clearAnimation()
    }

    override fun onClick(view: View) {

    }
}