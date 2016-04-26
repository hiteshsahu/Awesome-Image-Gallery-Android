package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * The Class GallarySample.
 */
public class GallarySample extends Activity {

	private MediaPlayer mp;

	private ArrayList<ImageDataModel> images;

	/** The images. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_activity);

		images = GalleryHelper.getAllShownImagesPath(this);

		mp = MediaPlayer.create(this, R.raw.type_sound);

		GridView gallery = (GridView) findViewById(R.id.galleryGridView);

		gallery.setAdapter(new ImageAdapter(this, images));

		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (null != images && !images.isEmpty())
					Toast.makeText(
							getApplicationContext(),
							"position " + position + " " + images.get(position),
							300).show();

				makeNoice();
			}
		});

	}

	private void makeNoice() {

		if (mp.isPlaying()) {
			mp.stop();
			mp.release();
			mp = MediaPlayer.create(GallarySample.this, R.raw.type_sound);
		}

		mp.start();
	}
}