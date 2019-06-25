package com.hiteshsahu.awesome_gallery.view


import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.ScaleGestureDetector
import android.view.View
import com.bumptech.glide.Glide
import com.hiteshsahu.awesome_gallery.R
import com.hiteshsahu.awesome_gallery.domain.BaseAsyncTask
import com.hiteshsahu.awesome_gallery.domain.LoadMediaTask
import com.hiteshsahu.awesome_gallery.modal.CenterRepository
import com.hiteshsahu.awesome_gallery.view.adapter.GalleryItemClickListener
import com.hiteshsahu.awesome_gallery.view.adapter.ImageGridAdapter
import com.hiteshsahu.awesome_gallery.view.adapter.MediaViewHolder
import com.hiteshsahu.camerapreview.BasePermissionActivity
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.content_gallery.*
import kotlinx.android.synthetic.main.view_no_media.*

/**
 * Created by Hitesh on 28-09-2016.
 */
class ImageGalleryActivity : BasePermissionActivity(), GalleryItemClickListener {


    private var mGridLayoutManager1: RecyclerView.LayoutManager? = null
    private var mGridLayoutManager2: RecyclerView.LayoutManager? = null
    private var mGridLayoutManager3: RecyclerView.LayoutManager? = null
    private var mCurrentLayoutManager: RecyclerView.LayoutManager? = null
    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var imageGridAdapter: ImageGridAdapter? = null


    override fun getActivityLayout(): Int {
        return R.layout.activity_gallery
    }

    /**
     * Set UP view after Permission granted
     */
    override fun setUpView() {

        // animate entry animation
        doCircularReveal(imageRoot)

        // create adpters
        setUpViewEvents()

// laod and render images
        LoadMediaTask(object : BaseAsyncTask.ProgressListener {
            override fun onStarted() {
                swipeContainer.isRefreshing = true
            }

            override fun onCompleted(successMessage: String?) {
                renderGridLayout()
            }

            override fun onError(errorMessage: String?) {
                renderGridLayout()
            }

        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    override fun setUpViewWithoutPermissions() {
        finish()
    }


    private fun setUpViewEvents() {

        setSupportActionBar(toolbar)

        //initialize layout managers
        mGridLayoutManager1 = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        mGridLayoutManager2 = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mGridLayoutManager3 = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        //Staggered Grid style
        mCurrentLayoutManager = if (resources.getBoolean(R.bool.is_portrait)) {
            mGridLayoutManager2
        } else {
            mGridLayoutManager3
        }


        // reload Images
        swipeContainer.setOnRefreshListener {
            setUpView()
        }
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)


        //set scale gesture detector
        mScaleGestureDetector = ScaleGestureDetector(this@ImageGalleryActivity, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                if (detector.currentSpan > 200 && detector.timeDelta > 200) {
                    if (detector.currentSpan - detector.previousSpan < -1) {
                        if (mCurrentLayoutManager === mGridLayoutManager1) {
                            mCurrentLayoutManager = mGridLayoutManager2
                            imageGrid.setLayoutManager(mGridLayoutManager2)
                            return true
                        } else if (mCurrentLayoutManager === mGridLayoutManager2) {
                            mCurrentLayoutManager = mGridLayoutManager3
                            imageGrid.setLayoutManager(mGridLayoutManager3)
                            return true
                        }
                    } else if (detector.currentSpan - detector.previousSpan > 1) {
                        if (mCurrentLayoutManager === mGridLayoutManager3) {
                            mCurrentLayoutManager = mGridLayoutManager2
                            imageGrid.setLayoutManager(mGridLayoutManager2)
                            return true
                        } else if (mCurrentLayoutManager === mGridLayoutManager2) {
                            mCurrentLayoutManager = mGridLayoutManager1
                            imageGrid.setLayoutManager(mGridLayoutManager1)
                            return true
                        }
                    }
                }
                return false
            }
        })

        //set touch listener on recycler view
        imageGrid.setOnTouchListener(View.OnTouchListener { v, event ->
            mScaleGestureDetector!!.onTouchEvent(event)
            false
        })
    }

    fun renderGridLayout() {

        if (CenterRepository.instance.imageCollection.listOfImages.isNotEmpty()) {

            noMedia.visibility = View.GONE
            imageGrid.layoutManager = mCurrentLayoutManager


            if (null == imageGridAdapter) {
                imageGridAdapter = ImageGridAdapter(Glide.with(applicationContext),
                        this@ImageGalleryActivity, applicationContext)
                imageGrid.adapter = imageGridAdapter
            } else {
                imageGridAdapter!!.notifyDataSetChanged()
            }

        } else {
            noMedia.visibility = View.VISIBLE
        }

        swipeContainer.setRefreshing(false)
    }

    override fun onItemSelected(holder: MediaViewHolder, position: Int) {

        val intent = Intent(this@ImageGalleryActivity, FullScreenGalleryActivity::class.java)
        intent.putExtra(FullScreenGalleryActivity.IMAGE_POSITION, position)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val imageView = holder.mediaImage
            val title = holder.mediaName
            val desc = holder.mediaDesc

            val pair1 = Pair.create<View, String>(imageView, imageView.transitionName)
            val pair2 = Pair.create<View, String>(title, title.transitionName)
            val pair3 = Pair.create<View, String>(desc, desc.transitionName)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@ImageGalleryActivity, pair1, pair2, pair3)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_gallery, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        animateExitScreen(imageRoot)
    }
}
