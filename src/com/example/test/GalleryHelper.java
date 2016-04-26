package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

public class GalleryHelper {

	/**
	 * Getting All Images Path.
	 *
	 * @param activity
	 *            the activity
	 * @return ArrayList with images Path
	 */
	public static ArrayList<ImageDataModel> getAllShownImagesPath(
			Activity activity) {
		Uri uri;
		Cursor cursor;
		int column_index_data, column_index_folder_name;
		ArrayList<ImageDataModel> listOfAllImages = new ArrayList<ImageDataModel>();
		String absolutePathOfImage = null, imageName;
		uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		String[] projection = { MediaColumns.DATA,
				MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

		cursor = activity.getContentResolver().query(uri, projection, null,
				null, null);

		column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);

		column_index_folder_name = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
		while (cursor.moveToNext()) {
			absolutePathOfImage = cursor.getString(column_index_data);

			imageName = cursor.getString(column_index_folder_name);

			ImageDataModel imDataModel = new ImageDataModel(imageName,
					absolutePathOfImage);

			listOfAllImages.add(imDataModel);
		}

		// Get all Internal images
		uri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

		cursor = activity.getContentResolver().query(uri, projection, null,
				null, null);

		column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		column_index_folder_name = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

		while (cursor.moveToNext()) {
			
			absolutePathOfImage = cursor.getString(column_index_data);

			imageName = cursor.getString(column_index_folder_name);

			ImageDataModel imDataModel = new ImageDataModel(imageName,
					absolutePathOfImage);

			listOfAllImages.add(imDataModel);
		}
		return listOfAllImages;
	}

}
