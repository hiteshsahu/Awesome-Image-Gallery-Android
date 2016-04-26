package com.serveroverload.gallery.ui.fragments;

import com.example.test.R;
import com.serveroverload.gallery.adapter.AllImagesAdapter;
import com.serveroverload.gallery.adapter.ImageFoldersAdapter;
import com.serveroverload.gallery.ui.customeview.CardsEffect;
import com.serveroverload.gallery.ui.customeview.JazzyGridView;
import com.serveroverload.gallery.util.GalleryHelper;
import com.serveroverload.gallery.util.UtilFunctions;
import com.serveroverload.gallery.util.UtilFunctions.AnimationType;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * @author 663918
 *
 */
public class FoldersFragment extends Fragment implements OnRefreshListener {

	protected static final String TAG = FoldersFragment.class.getSimpleName();

	private MediaPlayer mp;

	private JazzyGridView gallery;

	/** The swipe layout. */
	private SwipeRefreshLayout swipeLayout;

	/** The double back to exit pressed once. */
	private boolean doubleBackToExitPressedOnce;

	/** The m handler. */
	private Handler mHandler = new Handler();

	/** The m runnable. */
	private final Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			doubleBackToExitPressedOnce = false;
		}
	};

	boolean showingDetail = false;

	private ProgressBar progressBar;

	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.home_fragment, container, false);

		getActivity().setTitle("Folders");

		GalleryHelper.getImageFolderMap(getActivity());

		gallery = (JazzyGridView) rootView.findViewById(R.id.listView_products);
		// progressBar = (ProgressBar) rootView.findViewById(R.id.loading_bar);

		gallery.setTransitionEffect(new CardsEffect(24));

		swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(FoldersFragment.this);
		swipeLayout.setColorSchemeColors(android.R.color.holo_blue_bright, R.color.holo_green_light,
				R.color.holo_orange_light, R.color.holo_red_light);

		mp = MediaPlayer.create(getActivity(), R.raw.type_sound);

		// gallery = (GridView) findViewById(R.id.galleryGridView);

		setUpGrid(rootView);

		rootView.setFocusableInTouchMode(true);
		rootView.requestFocus();
		rootView.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

					// UtilFunctions.switchContent(R.id.frag_root,
					// UtilFunctions.DETAIL_FRAGMENT_TAG, getActivity(),
					// AnimationType.SLIDE_DOWN);

				}
				return true;
			}
		});

		return rootView;
	}

	/**
	 * @param rootView
	 */
	public void setUpGrid(View rootView) {

		if (!GalleryHelper.keyList.isEmpty()) {

			// Gridview adapter
			rootView.findViewById(R.id.default_nodata).setVisibility(View.GONE);

			swipeLayout.setVisibility(View.VISIBLE);

			gallery.setAdapter(new ImageFoldersAdapter(getActivity()));

			gallery.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int folderIndex, long arg3) {

					Toast.makeText(getActivity(),
							"position " + folderIndex + " " + GalleryHelper.keyList.get(folderIndex), 300).show();

					showingDetail = true;
//
//					gallery.setAdapter(new AllImagesAdapter(getActivity(),
//							GalleryHelper.imageFolderMap.get(GalleryHelper.keyList.get(folderIndex))));

//					gallery.setOnItemClickListener(new OnItemClickListener() {
//
//						@Override
//						public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//
							UtilFunctions.switchFragmentWithAnimation(R.id.frag_root,
									ImagesInFolder.newInstance(folderIndex), getActivity(),
									UtilFunctions.IMAGE_FOLDERS_TAG, AnimationType.SLIDE_RIGHT);
//
//							makeNoice();
//						}
//					});
//
//					makeNoice();
				}
			});

		} else {

			rootView.findViewById(R.id.default_nodata).setVisibility(View.VISIBLE);
			swipeLayout.setVisibility(View.GONE);

		}
	}

	private void makeNoice() {

		if (mp.isPlaying()) {
			mp.stop();
			mp.release();
			mp = MediaPlayer.create(getActivity(), R.raw.type_sound);
		}

		mp.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener#onRefresh
	 * ()
	 */
	@Override
	public void onRefresh() {

		swipeLayout.setRefreshing(false);

		setUpGrid(rootView);

	}

	public static Fragment newInstance() {
		return new FoldersFragment();
	}

}
