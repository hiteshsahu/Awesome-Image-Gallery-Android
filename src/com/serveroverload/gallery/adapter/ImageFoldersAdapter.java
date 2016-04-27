package com.serveroverload.gallery.adapter;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.serveroverload.gallery.ui.customeview.TextDrawable;
import com.serveroverload.gallery.ui.customeview.TextDrawable.IBuilder;
import com.serveroverload.gallery.util.ColorGenerator;
import com.serveroverload.gallery.util.GalleryHelper;

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
public class ImageFoldersAdapter extends BaseAdapter {

	private Activity context;

	private IBuilder mDrawableBuilder;
	private TextDrawable drawable;
	private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

	public ImageFoldersAdapter(Activity localContext) {
		context = localContext;
	}

	public int getCount() {
		return GalleryHelper.keyList.size();
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

			convertView = inflater.inflate(R.layout.folder_item, null);
		}

		((TextView) convertView.findViewById(R.id.textView)).setText(GalleryHelper.keyList.get(position));

		mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4).endConfig().roundRect(10);

		drawable = mDrawableBuilder.build(String.valueOf(GalleryHelper.keyList.get(position).charAt(0)),
				mColorGenerator.getColor(GalleryHelper.keyList.get(position)));

		Glide.with(context)
				.load(GalleryHelper.imageFolderMap.get(GalleryHelper.keyList.get(position)).get(0).getImagePath())
				.placeholder(drawable).centerCrop().error(drawable)
				.into((ImageView) convertView.findViewById(R.id.imageView));

		return convertView;
	}

}