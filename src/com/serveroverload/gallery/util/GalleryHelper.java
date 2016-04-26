package com.serveroverload.gallery.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.serveroverload.gallery.adapter.model.ImageDataModel;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

public class GalleryHelper {

	public static Map<String, ArrayList<ImageDataModel>> imageFolderMap = new HashMap<>();

	public static ArrayList<String> keyList;

	public static ArrayList<ImageDataModel> allImages = new ArrayList<ImageDataModel>();;

	/**
	 * Getting All Images Path.
	 *
	 * @param activity
	 *            the activity
	 * @return ArrayList with images Path
	 */
	public static Map<String, ArrayList<ImageDataModel>> getImageFolderMap(
			Activity activity) {

		imageFolderMap.clear();

		Uri uri;
		Cursor cursor;
		int column_index_data, column_index_folder_name;

		String absolutePathOfImage = null, folderName;
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

			folderName = cursor.getString(column_index_folder_name);

			ImageDataModel imDataModel = new ImageDataModel(folderName,
					absolutePathOfImage);

			if (imageFolderMap.containsKey(folderName)) {

				imageFolderMap.get(folderName).add(imDataModel);

			} else {

				ArrayList<ImageDataModel> listOfAllImages = new ArrayList<ImageDataModel>();

				listOfAllImages.add(imDataModel);

				imageFolderMap.put(folderName, listOfAllImages);
			}
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

			folderName = cursor.getString(column_index_folder_name);

			ImageDataModel imDataModel = new ImageDataModel(folderName,
					absolutePathOfImage);

			if (imageFolderMap.containsKey(folderName)) {

				imageFolderMap.get(folderName).add(imDataModel);
			} else {

				ArrayList<ImageDataModel> listOfAllImages = new ArrayList<ImageDataModel>();

				listOfAllImages.add(imDataModel);

				imageFolderMap.put(folderName, listOfAllImages);
			}

		}

		keyList = new ArrayList(imageFolderMap.keySet());

		return imageFolderMap;
	}

	/**
	 * Getting All Images Path.
	 *
	 * @param activity
	 *            the activity
	 * @return ArrayList with images Path
	 */
	public static ArrayList<ImageDataModel> gettAllImages(Activity activity) {

		allImages.clear();
		Uri uri;
		Cursor cursor;
		int column_index_data, column_index_folder_name;

		String absolutePathOfImage = null, imageName;
		uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		String[] projection = { MediaColumns.DATA,
				MediaStore.Images.Media.DISPLAY_NAME };

		cursor = activity.getContentResolver().query(uri, projection, null,
				null, null);

		column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);

		column_index_folder_name = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

		while (cursor.moveToNext()) {

			absolutePathOfImage = cursor.getString(column_index_data);

			imageName = cursor.getString(column_index_folder_name);

			allImages.add(new ImageDataModel(imageName, absolutePathOfImage));

		}

		// Get all Internal images
		uri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

		cursor = activity.getContentResolver().query(uri, projection, null,
				null, null);

		column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);

		column_index_folder_name = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

		while (cursor.moveToNext()) {

			absolutePathOfImage = cursor.getString(column_index_data);

			imageName = cursor.getString(column_index_folder_name);

			allImages.add(new ImageDataModel(imageName, absolutePathOfImage));
		}

		return allImages;
	}

}
