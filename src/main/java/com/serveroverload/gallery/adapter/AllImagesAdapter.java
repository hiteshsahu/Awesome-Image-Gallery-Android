package com.serveroverload.gallery.adapter;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.serveroverload.gallery.adapter.model.ImageDataModel;
import com.serveroverload.gallery.ui.customeview.TextDrawable;
import com.serveroverload.gallery.ui.customeview.TextDrawable.IBuilder;
import com.serveroverload.gallery.util.ColorGenerator;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The Class ImageAdapter.
 */
public class AllImagesAdapter extends BaseAdapter {

	private Activity context;
	private ArrayList<ImageDataModel> images;

	private IBuilder mDrawableBuilder;
	private TextDrawable drawable;
	private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

	public AllImagesAdapter(Activity localContext, ArrayList<ImageDataModel> images) {

		this.context = localContext;

		this.images = images;

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

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.gallery_item, null);
		}

		((TextView) convertView.findViewById(R.id.textView)).setText(images.get(position).getImageTitle());

		mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4).endConfig().roundRect(10);

		drawable = mDrawableBuilder.build(String.valueOf(images.get(position).getImageTitle().charAt(0)),
				mColorGenerator.getColor(images.get(position).getImageTitle()));

		Glide.with(context).load(images.get(position).getImagePath()).placeholder(drawable).error(drawable).centerCrop().animate(R.anim.slide_up)
				.into((ImageView) convertView.findViewById(R.id.imageView));

		return convertView;
	}

}