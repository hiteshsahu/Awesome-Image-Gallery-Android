package com.hiteshsahu.awesome_gallery.view.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.hiteshsahu.awesome_gallery.R;
import com.hiteshsahu.awesome_gallery.modal.CenterRepository;
import com.hiteshsahu.awesome_gallery.modal.ImageModel;
import com.hiteshsahu.awesome_gallery.util.PaletteBitmap;
import com.hiteshsahu.awesome_gallery.util.PaletteBitmapTranscoder;
import com.hiteshsahu.awesome_gallery.util.Utils;


public class ImageGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int lastPosition = -1;
    private final GalleryItemClickListener mListener;
    private final BitmapRequestBuilder<String, PaletteBitmap> glideRequest;

    private final
    @ColorInt
    int defaultColor;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ImageGridAdapter(RequestManager glide, GalleryItemClickListener listener, Context context) {
        mListener = listener;
        this.defaultColor = ContextCompat.getColor(context, R.color.colorPrimary);
        this.glideRequest = glide
                .fromString()
                .asBitmap()
                .transcode(new PaletteBitmapTranscoder(context), PaletteBitmap.class)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

    }


    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        return new MediaViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_media_gallery, parent, false));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        MediaViewHolder imageHolder = (MediaViewHolder) holder;

        imageHolder.getMediaName().setText(getItem(position).getImageName());
        imageHolder.getMediaDesc().setText(getItem(position).getImagePath());
        imageHolder.getMediaName().setSelected(true);
        imageHolder.getMediaDesc().setSelected(true);


        holder.itemView.setBackground(Utils.generatePlaceHolder(getItem(position).getImageName()));

        String url = getItem(position).getImagePath();

        if (url != null) { // simulate an optional url from the data item
            ((MediaViewHolder) holder).getMediaImage().setVisibility(View.VISIBLE);
            glideRequest
                    .load(url)
                    .into(new ImageViewTarget<PaletteBitmap>(((MediaViewHolder) holder).getMediaImage()) {
                        @Override
                        protected void setResource(PaletteBitmap resource) {
                            super.view.setImageBitmap(resource.bitmap);
                            int color = resource.palette.getVibrantColor(defaultColor);
                            holder.itemView.setBackground(new ColorDrawable(color));
                        }
                    });
        } else {
            // clear when no image is shown, don't use holder.imageView.setImageDrawable(null) to do the same
            Glide.clear(((MediaViewHolder) holder).getMediaImage());
            ((MediaViewHolder) holder).getMediaImage().setVisibility(View.GONE);
        }


        // Here you apply the animation when the view is bound
        setAnimation(((MediaViewHolder) holder).getContainer(), position);

        ((MediaViewHolder) holder).getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemSelected(((MediaViewHolder) holder), position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return CenterRepository.Companion.getInstance().getImageCollection().getListOfImages().size();
    }


    private ImageModel getItem(int position) {
        return CenterRepository.Companion.getInstance().getImageCollection().getImageAt(position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.clear(((MediaViewHolder) holder).getMediaImage());
    }

    @Override
    public void onViewDetachedFromWindow(final RecyclerView.ViewHolder holder) {
        ((MediaViewHolder) holder).clearAnimation();
    }

}
