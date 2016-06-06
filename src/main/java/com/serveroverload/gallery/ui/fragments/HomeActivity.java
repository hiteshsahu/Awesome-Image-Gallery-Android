package com.serveroverload.gallery.ui.fragments;

import com.example.test.R;
import com.serveroverload.gallery.adapter.DrawerListArrayAdapter;
import com.serveroverload.gallery.ui.customeview.ActionBarDrawerToggle;
import com.serveroverload.gallery.ui.customeview.DrawerArrowDrawable;
import com.serveroverload.gallery.util.UtilFunctions;
import com.serveroverload.gallery.util.UtilFunctions.AnimationType;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

@SuppressLint("ResourceAsColor")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HomeActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	private LinearLayout mDrawerLinear;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		mDrawerList = (ListView) findViewById(R.id.left_drawer_child);

		if (UtilFunctions.isPortrait(getApplicationContext())) {

			ActionBar ab = getActionBar();
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setHomeButtonEnabled(true);

			mDrawerLinear = (LinearLayout) findViewById(R.id.left_drawer);
			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

			drawerArrow = new DrawerArrowDrawable(this) {
				@Override
				public boolean isLayoutRtl() {
					return false;
				}
			};
			mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
					drawerArrow, R.string.drawer_open, R.string.drawer_close) {

				public void onDrawerClosed(View view) {
					super.onDrawerClosed(view);
					invalidateOptionsMenu();
				}

				public void onDrawerOpened(View drawerView) {
					super.onDrawerOpened(drawerView);
					invalidateOptionsMenu();
				}
			};
			mDrawerLayout.setDrawerListener(mDrawerToggle);
			mDrawerToggle.syncState();

		}

		// String imageUrl =
		// PreferenceHelper.getPrefernceHelperInstace(getApplicationContext())
		// .getString(PreferenceHelper.IMAGE_URL, null);

		// if (null != imageUrl) {
		// Picasso.with(getApplicationContext()).load(Uri.parse(imageUrl))
		// .centerCrop().fit()
		// .into((ImageView) findViewById(R.id.profile_pic));
		// }

		UtilFunctions.switchContent(R.id.frag_root,
				UtilFunctions.ALL_IMAGES_TAG, HomeActivity.this,
				AnimationType.SLIDE_LEFT);

		mDrawerList.setAdapter(new DrawerListArrayAdapter(
				getApplicationContext(), getResources().getStringArray(
						R.array.drawer_list_array)));

		mDrawerList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						mDrawerList.setItemChecked(position, true);

						switch (position) {
						case 0:

							UtilFunctions
									.switchContent(R.id.frag_root,
											UtilFunctions.ALL_IMAGES_TAG,
											HomeActivity.this,
											AnimationType.SLIDE_LEFT);
							break;
						case 1:

							UtilFunctions
									.switchContent(R.id.frag_root,
											UtilFunctions.IMAGE_FOLDERS_TAG,
											HomeActivity.this,
											AnimationType.SLIDE_LEFT);
							break;
						case 2:

							// UtilFunctions.switchContent(R.id.frag_root,
							// UtilFunctions.SELECT_IMAGE_FRAGMENT,
							// HomeActivity.this, AnimationType.SLIDE_LEFT);
							break;
						case 3:

							break;
						case 4:
							//
							// UtilFunctions.switchContent(R.id.frag_root,
							// UtilFunctions.EDIT_IMAGE_FRAGMENT,
							// SampleActivity.this,
							// AnimationType.SLIDE_LEFT);
							break;
						case 5:

							UtilFunctions
									.switchContent(R.id.frag_root,
											UtilFunctions.SETTINGS_TAG,
											HomeActivity.this,
											AnimationType.SLIDE_LEFT);
							break;
						case 6:

							UtilFunctions
									.switchContent(R.id.frag_root,
											UtilFunctions.ABOUT_APP_TAG,
											HomeActivity.this,
											AnimationType.SLIDE_LEFT);
							break;

						}

						if (UtilFunctions.isPortrait(getApplicationContext())) {

							if (mDrawerLayout.isDrawerOpen(mDrawerLinear)) {
								mDrawerLayout.closeDrawer(mDrawerLinear);
							} else {
								mDrawerLayout.openDrawer(mDrawerLinear);
							}
						}
					}
				});

		findViewById(R.id.profile_detail).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (UtilFunctions.isPortrait(getApplicationContext())) {

							if (mDrawerLayout.isDrawerOpen(mDrawerLinear)) {
								mDrawerLayout.closeDrawer(mDrawerLinear);
							} else {
								mDrawerLayout.openDrawer(mDrawerLinear);
							}
						}
						//
						// UtilFunctions.switchContent(R.id.frag_root,
						// UtilFunctions.PROFILE_TAG, HomeActivity.this,
						// AnimationType.SLIDE_LEFT);

					}
				});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == android.R.id.home) {
			if (UtilFunctions.isPortrait(getApplicationContext())) {

				if (mDrawerLayout.isDrawerOpen(mDrawerLinear)) {
					mDrawerLayout.closeDrawer(mDrawerLinear);
				} else {
					mDrawerLayout.openDrawer(mDrawerLinear);
				}
			}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (UtilFunctions.isPortrait(getApplicationContext())) {
			mDrawerToggle.syncState();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (UtilFunctions.isPortrait(getApplicationContext())) {
			mDrawerToggle.onConfigurationChanged(newConfig);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

	}

	private MenuItem saveImage;

	public MenuItem getSaveImage() {
		return saveImage;
	}

}
