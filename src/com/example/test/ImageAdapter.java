package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * The Class ImageAdapter.
 */
class ImageAdapter extends BaseAdapter {

	/** The context. */
	private Activity context;
	private ArrayList<ImageDataModel> images;

	/**
	 * Instantiates a new image adapter.
	 *
	 * @param localContext
	 *            the local context
	 * @param images2
	 */
	public ImageAdapter(Activity localContext, ArrayList<ImageDataModel> images2) {
		context = localContext;
		images = images2;
	}

	public int getCount() {
		return images.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.gallery_item, null);

		}

		((TextView) convertView.findViewById(R.id.textView)).setText(images
				.get(position).getImageTitle());

		Glide.with(context).load(images.get(position).getImagePath())
				.placeholder(R.drawable.ic_launcher).centerCrop()
				.into((ImageView) convertView.findViewById(R.id.imageView));
		//
		// } else {
		// // If no image is provided, display a folder icon.
		// imageView.setImageResource(R.drawable.your_folder_icon);
		// }

		// } else {
		// convertView = (ImageView) convertView;
		// }

		// Glide.with(context).load(images.get(position).getImagePath())
		// .placeholder(R.drawable.ic_launcher).centerCrop()
		// .into(picturesView);

		return convertView;
	}

}