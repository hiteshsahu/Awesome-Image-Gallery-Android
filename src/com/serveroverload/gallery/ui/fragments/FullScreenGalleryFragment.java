package com.serveroverload.gallery.ui.fragments;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.example.test.R;
import com.serveroverload.gallery.adapter.TouchImageAdapter;
import com.serveroverload.gallery.ui.customeview.ExtendedViewPager;
import com.serveroverload.gallery.util.GalleryHelper;
import com.serveroverload.gallery.util.UtilFunctions;
import com.serveroverload.gallery.util.UtilFunctions.AnimationType;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class FullScreenGalleryFragment extends Fragment {

	private static final String IMAGE_POSITION = "PagerPosition";
	private static final String IS_FROM_FOLDER = "IsFromFolders";
	private static final String FOLDER_POSITION = "FolderPosition";
	private static int currentPagePosition = 0;
	private ExtendedViewPager mViewPager;
	private TouchImageAdapter touchImageAdapter;
	private boolean isFromFolders;
	private int currentFolderPosition;

	public static Fragment newInstance(int imagePosition, int folderPosition, boolean isFromFolders) {

		Bundle bundle = new Bundle();
		bundle.putBoolean(IS_FROM_FOLDER, isFromFolders);
		bundle.putInt(IMAGE_POSITION, imagePosition);
		bundle.putInt(FOLDER_POSITION, folderPosition);
		FullScreenGalleryFragment fullScreenPagerFragment = new FullScreenGalleryFragment();
		fullScreenPagerFragment.setArguments(bundle);
		return fullScreenPagerFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.doodle_gallery, container, false);

		if (null != getArguments()) {
			currentPagePosition = getArguments().getInt(IMAGE_POSITION);
			currentFolderPosition = getArguments().getInt(FOLDER_POSITION);
			isFromFolders = getArguments().getBoolean(IS_FROM_FOLDER);

			// setContentView(R.layout.activity_viewpager_example);
			mViewPager = (ExtendedViewPager) rootView.findViewById(R.id.view_pager);

			if (isFromFolders) {

				touchImageAdapter = new TouchImageAdapter(
						GalleryHelper.imageFolderMap.get(GalleryHelper.keyList.get(currentFolderPosition)));
			} else {

				touchImageAdapter = new TouchImageAdapter(GalleryHelper.allImages);

			}

			mViewPager.setAdapter(touchImageAdapter);

			mViewPager.setCurrentItem(currentPagePosition);

			rootView.findViewById(R.id.edit).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});

			rootView.findViewById(R.id.info).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// 1. Instantiate an AlertDialog.Builder with its
					// constructor
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

						}
					});
					builder.setNegativeButton("Done", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User cancelled the dialog
						}
					});

					StringBuilder str = new StringBuilder();

					// 2. Chain together various setter methods to set the
					// dialog characteristics

					// 3. Get the AlertDialog from create()

					// There are multiple ways to get a Metadata object for
					// a file

					//
					// SCENARIO 1: UNKNOWN FILE TYPE
					//
					// This is the most generic approach. It will
					// transparently determine the file type and invoke the
					// appropriate
					// readers. In most cases, this is the most appropriate
					// usage. This will handle JPEG, TIFF, GIF, BMP and RAW
					// (CRW/CR2/NEF/RW2/ORF) files and extract whatever
					// metadata is available and understood.

					//

					File file = getImageFile();

					try {
						Metadata metadata = ImageMetadataReader.readMetadata(file);

						for (Directory directory : metadata.getDirectories()) {

							//
							// Each Directory stores values in Tag objects
							//
							for (Tag tag : directory.getTags()) {
								System.out.println(tag);

								str.append(tag.toString());
								str.append("\n");

							}

							//
							// Each Directory may also contain error
							// messages
							//
							if (directory.hasErrors()) {
								for (String error : directory.getErrors()) {
									System.err.println("ERROR: " + error);
								}
							}

						}

					} catch (ImageProcessingException e) {
						// handle exception
					} catch (IOException e) {
						// handle exception
					}

					builder.setMessage(str.toString()).setTitle("Image Info");

					AlertDialog dialog = builder.create();

					dialog.show();

				}
			});

			rootView.findViewById(R.id.canvas).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// AppContants.doodleImageURL =
					// DiskUtil.getListOfDoodles(true).get(mViewPager.getCurrentItem());
					//
					// UtilFunctions.switchFragmentWithAnimation(R.id.frag_root,
					// DoodleFragment.newInstance(), getActivity(),
					// UtilFunctions.DOODLE_FRAGMENT,
					// AnimationType.SLIDE_RIGHT);

				}
			});

			rootView.findViewById(R.id.delete).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// // Set up the projection (we only need the ID)
					// String[] projection = { MediaStore.Images.Media._ID };
					//
					// // Match on the file path
					// String selection = MediaStore.Images.Media.DATA + " = ?";
					// String[] selectionArgs = new String[] {
					// new
					// File(DiskUtil.getListOfDoodles(true).get(mViewPager.getCurrentItem())).getAbsolutePath()
					// };
					//
					// // Query for the ID of the media matching the file path
					// Uri queryUri =
					// MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
					// ContentResolver contentResolver =
					// getActivity().getContentResolver();
					// Cursor c = contentResolver.query(queryUri, projection,
					// selection, selectionArgs, null);
					// if (c.moveToFirst()) {
					// // We found the ID. Deleting the item via the content
					// // provider will also remove the file
					// long id =
					// c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
					// Uri deleteUri =
					// ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					// id);
					// contentResolver.delete(deleteUri, null, null);
					// Toast.makeText(getActivity(), "Deleted", 500).show();
					// } else {
					// Toast.makeText(getActivity(), "Failed to Delete",
					// 500).show();
					// }
					// c.close();

					File file = getImageFile();

					// File f = new
					// File(GalleryHelper.imageFolderMap.get(GalleryHelper.keyList.get(currentPagePosition))
					// .get(mViewPager.getCurrentItem()).getImagePath());

					if (file.delete()) {

						touchImageAdapter.notifyDataSetChanged();

						getActivity()
								.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
										Uri.fromFile(new File(GalleryHelper.imageFolderMap
												.get(GalleryHelper.keyList.get(currentPagePosition))
												.get(mViewPager.getCurrentItem()).getImagePath()))));

						Toast.makeText(getActivity(), "Deleted", 500).show();
					} else {
						Toast.makeText(getActivity(), "Failed to Delete", 500).show();
					}
				}

			});

			rootView.findViewById(R.id.share).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent share = new Intent(Intent.ACTION_SEND);
					share.setType("image/jpeg");

					if (isFromFolders) {
						share.putExtra(Intent.EXTRA_STREAM,
								Uri.parse(
										GalleryHelper.imageFolderMap.get(GalleryHelper.keyList.get(currentPagePosition))
												.get(mViewPager.getCurrentItem()).getImagePath()));
					} else {

						share.putExtra(Intent.EXTRA_STREAM,
								Uri.parse(GalleryHelper.allImages.get(mViewPager.getCurrentItem()).getImagePath()));

					}
					startActivity(Intent.createChooser(share, "Share Image"));

				}
			});

			rootView.setFocusableInTouchMode(true);
			rootView.requestFocus();
			rootView.setOnKeyListener(new View.OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {

					if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

						if (isFromFolders) {
							
//							UtilFunctions.switchFragmentWithAnimation(R.id.frag_root,
//									ImagesInFolder.newInstance(folderIndex), getActivity(),
//									UtilFunctions.IMAGE_FOLDERS_TAG, AnimationType.SLIDE_RIGHT);

							UtilFunctions.switchFragmentWithAnimation(R.id.frag_root,
									ImagesInFolder.newInstance(currentPagePosition), getActivity(),
									UtilFunctions.IMAGE_FOLDERS_TAG, AnimationType.SLIDE_RIGHT);

						} else {

							UtilFunctions.switchContent(R.id.frag_root, UtilFunctions.ALL_IMAGES_TAG,
									(HomeActivity) getActivity(), AnimationType.SLIDE_RIGHT);
						}

					}
					return true;
				}
			});
		}

		return rootView;
	}

	private File getImageFile() {
		File file;

		if (isFromFolders) {
			file = new File(GalleryHelper.imageFolderMap.get(GalleryHelper.keyList.get(currentPagePosition))
					.get(mViewPager.getCurrentItem()).getImagePath());
		} else {
			file = new File(GalleryHelper.allImages.get(mViewPager.getCurrentItem()).getImagePath());
		}
		return file;
	}

}