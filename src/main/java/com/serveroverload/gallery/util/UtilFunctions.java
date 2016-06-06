package com.serveroverload.gallery.util;

import com.example.test.R;
import com.serveroverload.gallery.ui.fragments.AboutAppFragment;
import com.serveroverload.gallery.ui.fragments.AllImagesFragment;
import com.serveroverload.gallery.ui.fragments.FoldersFragment;
import com.serveroverload.gallery.ui.fragments.FullScreenGalleryFragment;
import com.serveroverload.gallery.ui.fragments.SettingsFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

// TODO: Auto-generated Javadoc
/**
 * The Class UtilFunctions.
 */
@SuppressLint("NewApi")
public class UtilFunctions {

	private static String CURRENT_TAG = null;

	public static final String ALL_IMAGES_TAG = "HomeFragment";
	public static final String SETTINGS_TAG = "Settings";
	public static final String FULL_SCREEN_GALLERY = "DoodleDetail";
	public static final String IMAGE_FOLDERS_TAG = "Doodle";
	public static final String ABOUT_APP_TAG = "About";
	
	/** The Constant IS_ISC. */
	// public static final boolean IS_JBMR2 = Build.VERSION.SDK_INT ==
	// Build.VERSION_CODES.JELLY_BEAN_MR2;
	public static final boolean IS_ISC = Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH;

	/** The Constant IS_GINGERBREAD_MR1. */
	public static final boolean IS_GINGERBREAD_MR1 = Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD_MR1;

	public static void switchContent(int id, String TAG, FragmentActivity baseActivity, AnimationType transitionStyle) {

		Fragment fragmentToReplace = null;

		FragmentManager fragmentManager = baseActivity.getSupportFragmentManager();

		FragmentTransaction transaction = fragmentManager.beginTransaction();

		// If our current fragment is null, or the new fragment is different, we
		// need to change our current fragment
		if (CURRENT_TAG == null || !TAG.equals(CURRENT_TAG)) {

			if (transitionStyle != null) {
				switch (transitionStyle) {
				case SLIDE_DOWN:
					// Exit from down
					transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);

					break;
				case SLIDE_UP:
					// Enter from Up
					transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
					break;
				case SLIDE_LEFT:
					// Enter from left
					transaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left);
					break;
				// Enter from right
				case SLIDE_RIGHT:
					transaction.setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right);
					break;
				case FADE_IN:
					transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
				case FADE_OUT:
					transaction.setCustomAnimations(R.anim.fade_in, R.anim.donot_move);
					break;
				case SLIDE_IN_SLIDE_OUT:
					transaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left);
					break;
				default:
					break;
				}
			}

			// Try to find the fragment we are switching to
			Fragment fragment = fragmentManager.findFragmentByTag(TAG);

			// If the new fragment can't be found in the manager, create a new
			// one
			if (fragment == null) {

				if (TAG.equals(ALL_IMAGES_TAG)) {
					fragmentToReplace = AllImagesFragment.newInstance();
				}

				else if (TAG.equals(IMAGE_FOLDERS_TAG)) {
					fragmentToReplace = FoldersFragment.newInstance();
				}
				
//				 else if (TAG.equals(FULL_SCREEN_GALLERY)) {
//				 fragmentToReplace = FullScreenGalleryFragment.newInstance();
//				 }
				

				else if (TAG.equals(ABOUT_APP_TAG)) {
					fragmentToReplace = AboutAppFragment.newInstance();
				}

				if (TAG.equals(SETTINGS_TAG)) {
					fragmentToReplace = SettingsFragment.newInstance();
				}

			}
			// Otherwise, we found our fragment in the manager, so we will reuse
			// it
			else {

				if (TAG.equals(ALL_IMAGES_TAG)) {
					fragmentToReplace = (AllImagesFragment) fragment;

				}

				else if (TAG.equals(IMAGE_FOLDERS_TAG)) {
					fragmentToReplace = (FoldersFragment) fragment;

				}

//				else if (TAG.equals(FULL_SCREEN_GALLERY)) {
//					fragmentToReplace = (ImageEditorFragment) fragment;
//
//				}

				else if (TAG.equals(ABOUT_APP_TAG)) {
					fragmentToReplace = (AboutAppFragment) fragment;

				}

				else if (TAG.equals(SETTINGS_TAG)) {
					fragmentToReplace = (SettingsFragment) fragment;

				}

			}

			CURRENT_TAG = TAG;

			if (null != fragmentToReplace) {
				// Replace our current fragment with the one we are changing to
				transaction.replace(id, fragmentToReplace, TAG);
				transaction.commit();
			}

		} else

		{
			// Do nothing since we are already on the fragment being changed to
		}
	}

	public static String getVersion(Context context) {
		try {
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);
			return String.valueOf(pInfo.versionCode) + " " + pInfo.versionName;

		} catch (NameNotFoundException e) {
			return "1.0.1";
		}
	}

	public static void switchFragmentWithAnimation(int id, Fragment fragment, FragmentActivity activity, String TAG,
			AnimationType transitionStyle) {

		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		if (transitionStyle != null) {
			switch (transitionStyle) {
			case SLIDE_DOWN:

				// Exit from down
				fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);

				break;

			case SLIDE_UP:

				// Enter from Up
				fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);

				break;

			case SLIDE_LEFT:

				// Enter from left
				fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left);

				break;

			// Enter from right
			case SLIDE_RIGHT:
				fragmentTransaction.setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right);

				break;

			case FADE_IN:
				fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

			case FADE_OUT:
				fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.donot_move);

				break;

			case SLIDE_IN_SLIDE_OUT:

				fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left);

				break;

			default:
				break;
			}
		}

		CURRENT_TAG = TAG;

		fragmentTransaction.replace(id, fragment);
		fragmentTransaction.addToBackStack(TAG);
		fragmentTransaction.commit();
	}

	/**
	 * The Enum AnimationType.
	 */
	public enum AnimationType {

		/** The slide left. */
		SLIDE_LEFT, /** The slide right. */
		SLIDE_RIGHT, /** The slide up. */
		SLIDE_UP, /** The slide down. */
		SLIDE_DOWN, /** The fade in. */
		FADE_IN, /** The slide in slide out. */
		SLIDE_IN_SLIDE_OUT, /** The fade out. */
		FADE_OUT
	}

	/**
	 * Vibrate.
	 *
	 * @param context
	 *            the context
	 */
	public static void vibrate(Context context) {
		// Get instance of Vibrator from current Context and Vibrate for 400
		// milliseconds
		((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);
	}

	public static boolean isPortrait(Context context) {

		return context.getResources().getBoolean(R.bool.is_portrait);
	}

}
