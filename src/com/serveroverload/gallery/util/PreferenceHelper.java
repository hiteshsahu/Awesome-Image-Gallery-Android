/**
 * 
 */
package com.serveroverload.gallery.util;

import android.content.Context;
import android.preference.PreferenceManager;

// TODO: Auto-generated Javadoc
/**
 * The Class PreferenceHelper.
 *
 * @author Hitesh
 */
public class PreferenceHelper {

	private static final String USER_LOGGED_IN = "isLoggedIn";
	public final static String FIRST_TIME = "FirstTime";
	public final static String WHATS_NEW_LAST_SHOWN = "whats_new_last_shown";
	public final static String SUBMIT_LOGS = "CrashLogs";
	public final static String PHONE_NUMBER = "ContactNumber";
	public final static String SAVE_DEATILS = "shouldStoreNumber";
	public static final String SAVE_ADDRESS = "shouldSaveAdd";
	public static final String ADDRESS = "Address";
	public static final String ADDRESS_ID = "AddID";
	public static final String ADD_HOUSE = "AddHouse";
	public static final String ADD_BUILDING = "AddBuilding";
	public static final String ADD_STREET = "AddStreet";
	public static final String ADD_AREA = "AddArea";
	public static final String FIRST_NAME = "FirstName";
	public static final String LAST_NAME = "lastName";
	public static final String EMAIL_ID = "EmailID";

	// Handle Local Caching of data for responsiveness
	public static final String PRODUCT_CATEGORY_RESPONSE_JSON = "CategoryResponse";
	public static final String ALL_PRODUCT_LIST_RESPONSE_JSON = "AllProductsResponse";
	public static final String MY_ADDRESS_RESPONSE_JSON = "AllProductsResponse";
	public static final String MY_ORDER_RESPONSE_JSON = "AllProductsResponse";
	public static final String HOT_OFFER_RESPONSE_JSON = "AllProductsResponse";
	public static final String MY_CART_LIST_LOCAL = "MyCartItems";

	/** The app context. */
	private static Context appContext;

	/** The preference helper instance. */
	private static PreferenceHelper preferenceHelperInstance;
	public static String LOGED_IN = USER_LOGGED_IN;
	public static String IMAGE_URL ="ProfilePicURL";

	/**
	 * Instantiates a new preference helper.
	 * 
	 * @param context
	 */
	private PreferenceHelper(Context context) {

		this.appContext = context;
	}

	// /**
	// * Inits the.
	// *
	// * @param context
	// * the context
	// */
	// public void init(Context context) {
	// appContext = context;
	//
	// if (!getPrefernceHelperInstace().getBoolean(FIRST_TIME, false)) {
	//
	// // App
	// setBoolean(FIRST_TIME, true);
	//
	// // Support
	// setBoolean(SUBMIT_LOGS, true);
	//
	// }
	//
	// }

	/**
	 * Gets the prefernce helper instace.
	 *
	 * @return the prefernce helper instace
	 */
	public static PreferenceHelper getPrefernceHelperInstace(Context context) {

		if (null == preferenceHelperInstance) {
			preferenceHelperInstance = new PreferenceHelper(context);
		}

		return preferenceHelperInstance;
	}

	/**
	 * Sets the boolean.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void setBoolean(String key, Boolean value) {

		PreferenceManager.getDefaultSharedPreferences(appContext).edit()
				.putBoolean(key, value).apply();
	}

	/**
	 * Sets the integer.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void setInteger(String key, int value) {

		PreferenceManager.getDefaultSharedPreferences(appContext).edit()
				.putInt(key, value).apply();
	}

	/**
	 * Sets the float.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void setFloat(String key, float value) {

		PreferenceManager.getDefaultSharedPreferences(appContext).edit()
				.putFloat(key, value).apply();
	}

	/**
	 * Sets the string.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void setString(String key, String value) {

		PreferenceManager.getDefaultSharedPreferences(appContext).edit()
				.putString(key, value).apply();
	}

	// To retrieve values from shared preferences:

	/**
	 * Gets the boolean.
	 *
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return the boolean
	 */
	public boolean getBoolean(String key, Boolean defaultValue) {

		return PreferenceManager.getDefaultSharedPreferences(appContext)
				.getBoolean(key, defaultValue);
	}

	/**
	 * Gets the integer.
	 *
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return the integer
	 */
	public int getInteger(String key, int defaultValue) {

		return PreferenceManager.getDefaultSharedPreferences(appContext)
				.getInt(key, defaultValue);
	}

	/**
	 * Gets the string.
	 *
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return the string
	 */
	public float getString(String key, float defaultValue) {

		return PreferenceManager.getDefaultSharedPreferences(appContext)
				.getFloat(key, defaultValue);
	}

	/**
	 * Gets the string.
	 *
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return the string
	 */
	public String getString(String key, String defaultValue) {

		return PreferenceManager.getDefaultSharedPreferences(appContext)
				.getString(key, defaultValue);
	}

	public boolean isUserLoggedIn() {

		return getBoolean(USER_LOGGED_IN, false);
	}

	public void setUserLoggedIn(boolean UserLoggedIn) {

		setBoolean(USER_LOGGED_IN, UserLoggedIn);
	}

}
