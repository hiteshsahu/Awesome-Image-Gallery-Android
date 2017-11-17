package com.hiteshsahu.awesome_gallery.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;

import com.bumptech.glide.Glide;
import com.hiteshsahu.awesome_gallery.R;
import com.hiteshsahu.awesome_gallery.domain.ImageMediaProvider;
import com.hiteshsahu.awesome_gallery.modal.CenterRepository;
import com.hiteshsahu.awesome_gallery.util.Animatrix;
import com.hiteshsahu.awesome_gallery.view.adapter.GalleryItemClickListener;
import com.hiteshsahu.awesome_gallery.view.adapter.ImageGridAdapter;
import com.hiteshsahu.awesome_gallery.view.adapter.MediaViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hitesh on 28-09-2016.
 */
public class ImageGalleryActivity extends BasePermissionActivity implements GalleryItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_grid)
    RecyclerView imageGrid;
    @BindView(R.id.no_media_found)
    View noMedia;
    @BindView(R.id.root)
    CoordinatorLayout imageRoot;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    private RecyclerView.LayoutManager mGridLayoutManager1, mGridLayoutManager2, mGridLayoutManager3;
    private RecyclerView.LayoutManager mCurrentLayoutManager;
    private ScaleGestureDetector mScaleGestureDetector;

    private ImageGridAdapter imageGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
            getWindow().setSharedElementEnterTransition(new ChangeImageTransform());
            getWindow().setSharedElementExitTransition(new ChangeImageTransform());
        }

        imageRoot.setVisibility(View.INVISIBLE);

        ViewTreeObserver viewTreeObserver = imageRoot.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    Animatrix.circularRevealView(imageRoot);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        imageRoot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        imageRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }

        setUpViewEvents();

        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+ Permission APIs
            fuckMarshMallow();

        } else {
            // Pre-Marshmallow
            doStuffs();
        }
    }

    protected void setUpViewEvents() {

        setSupportActionBar(toolbar);

        //initialize layout managers
        mGridLayoutManager1 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mGridLayoutManager2 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mGridLayoutManager3 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        //Staggered Grid style
        if (getResources().getBoolean(R.bool.is_portrait)) {
            mCurrentLayoutManager = mGridLayoutManager2;
        } else {
            mCurrentLayoutManager = mGridLayoutManager3;
        }

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Build.VERSION.SDK_INT >= 23) {
                    // Marshmallow+ Permission APIs
                    fuckMarshMallow();
                } else {
                    // Pre-Marshmallow
                    doStuffs();
                }
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        //set scale gesture detector
        mScaleGestureDetector = new ScaleGestureDetector(ImageGalleryActivity.this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if (detector.getCurrentSpan() > 200 && detector.getTimeDelta() > 200) {
                    if (detector.getCurrentSpan() - detector.getPreviousSpan() < -1) {
                        if (mCurrentLayoutManager == mGridLayoutManager1) {
                            mCurrentLayoutManager = mGridLayoutManager2;
                            imageGrid.setLayoutManager(mGridLayoutManager2);
                            return true;
                        } else if (mCurrentLayoutManager == mGridLayoutManager2) {
                            mCurrentLayoutManager = mGridLayoutManager3;
                            imageGrid.setLayoutManager(mGridLayoutManager3);
                            return true;
                        }
                    } else if (detector.getCurrentSpan() - detector.getPreviousSpan() > 1) {
                        if (mCurrentLayoutManager == mGridLayoutManager3) {
                            mCurrentLayoutManager = mGridLayoutManager2;
                            imageGrid.setLayoutManager(mGridLayoutManager2);
                            return true;
                        } else if (mCurrentLayoutManager == mGridLayoutManager2) {
                            mCurrentLayoutManager = mGridLayoutManager1;
                            imageGrid.setLayoutManager(mGridLayoutManager1);
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        //set touch listener on recycler view
        imageGrid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScaleGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }

    public void renderStuffs() {

        if (!CenterRepository.getInstance().getImageCollection().getListOfImages().isEmpty()) {

            noMedia.setVisibility(View.GONE);

            imageGrid.setLayoutManager(mCurrentLayoutManager);

//            imageGrid.setHasFixedSize(true);

            if (null == imageGridAdapter) {
                imageGridAdapter = new ImageGridAdapter(Glide.with(getApplicationContext()),
                        ImageGalleryActivity.this, getApplicationContext());

                imageGrid.setAdapter(imageGridAdapter);
            } else {
                imageGridAdapter.notifyDataSetChanged();
            }

        } else {
            noMedia.setVisibility(View.VISIBLE);
        }

        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onItemSelected(MediaViewHolder holder, int position) {

        Intent intent = new Intent(ImageGalleryActivity.this, FullScreenGalleryActivity.class);
        intent.putExtra(FullScreenGalleryActivity.IMAGE_POSITION, position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            View imageView = holder.getMediaImage();
            View title = holder.getMediaName();
            View desc = holder.getMediaDesc();

            Pair<View, String> pair1 = Pair.create(imageView, imageView.getTransitionName());
            Pair<View, String> pair2 = Pair.create(title, title.getTransitionName());
            Pair<View, String> pair3 = Pair.create(desc, desc.getTransitionName());
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(ImageGalleryActivity.this, pair1, pair2, pair3);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void doStuffs() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                swipeContainer.setRefreshing(true);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                ImageMediaProvider.getInstance().getAllImages(ImageGalleryActivity.this);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                renderStuffs();
            }
        }.execute();
    }
}
