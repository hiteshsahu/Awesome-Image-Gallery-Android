package com.hiteshsahu.awesome_gallery.view

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import com.hiteshsahu.awesome_gallery.BuildConfig.APPLICATION_ID
import com.hiteshsahu.awesome_gallery.modal.CenterRepository
import com.hiteshsahu.awesome_gallery.view.adapter.GalleryPagerAdapter
import com.hiteshsahu.awesome_gallery.view.adapter.MyAdapter
import com.hiteshsahu.awesome_gallery.view.widget.ParallaxPagerTransformer
import kotlinx.android.synthetic.main.activity_full_screen_gallery.*
import java.io.File


/**
 * Activity to display image full screen with Pinch to Zoom option
 */
class FullScreenGalleryActivity : AppCompatActivity() {

    private var galleryAdapter: GalleryPagerAdapter? = null
    private var fullScreenMode = false;

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hiteshsahu.awesome_gallery.R.layout.activity_full_screen_gallery)

        /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setEnterTransition(new Explode());
                getWindow().setExitTransition(new Explode());
                getWindow().setSharedElementEnterTransition(new ChangeImageTransform());
                getWindow().setSharedElementExitTransition(new ChangeImageTransform());
                getWindow().setSharedElementEnterTransition(TransitionInflater.from(this)
                        .inflateTransition(R.transition.curve));
            }*/

        val currentPagePosition = intent.getIntExtra(IMAGE_POSITION, 0)



        // On click
        mViewPager.setOnItemClickListener {

            // your code
            if (fullScreenMode) {
                backFab.visibility = View.VISIBLE
                window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            } else {
                backFab.visibility = View.GONE
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            }
            fullScreenMode = !fullScreenMode
        }
        // set adapter
        mViewPager.setPageTransformer(false, ParallaxPagerTransformer(com.hiteshsahu.awesome_gallery.R.id.touchImageView));
        galleryAdapter = GalleryPagerAdapter()
        mViewPager.adapter = galleryAdapter
        mViewPager.currentItem = currentPagePosition



//        // View Pager 2
//        val myAdpater = MyAdapter(this)
//        myViewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
//        myViewPager2.adapter = myAdpater
//        myViewPager2.currentItem = currentPagePosition
//        myViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//            }
//
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                pagePosition.setText("" + position + "/" + galleryAdapter!!.count)
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//                super.onPageScrollStateChanged(state)
//            }
//        })


        //Page index
        pagePosition.setFactory(object : ViewSwitcher.ViewFactory {

            override fun makeView(): View {
                val pageIndex = TextView(this@FullScreenGalleryActivity)
                pageIndex.gravity = Gravity.CENTER
                pageIndex.textSize = 15f
                pageIndex.setTextColor(Color.WHITE)
                return pageIndex
            }
        })

        val slideInUp = AnimationUtils.loadAnimation(this, com.hiteshsahu.awesome_gallery.R.anim.slide_in_up)
        val slideOutDown = AnimationUtils.loadAnimation(this, com.hiteshsahu.awesome_gallery.R.anim.slide_out_up)

        pagePosition.inAnimation = slideInUp
        pagePosition.outAnimation = slideOutDown

        mViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                pagePosition.setText("" + position + "/" + galleryAdapter!!.count)
            }
        })

        pagePosition.setText("" + currentPagePosition + "/" + galleryAdapter!!.count)


        // edit image
        editImage.setOnClickListener {
            editThisItem(CenterRepository.instance.imageCollection.getImageAt(mViewPager.currentItem).imagePath)
        }

        // share image
        shareImage.setOnClickListener {
            shareThisItem(CenterRepository.instance.imageCollection
                    .getImageAt(mViewPager.currentItem)
                    .imagePath)
        }

        //delete Image
        deleteImage.setOnClickListener {
            deleteThisFile()
        }

    }

    private fun editThisItem(imagePath: String) {
        val editIntent = Intent(Intent.ACTION_EDIT)
        editIntent.type = "image/*"
        editIntent.putExtra(Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(this@FullScreenGalleryActivity,
                        APPLICATION_ID + ".provider",
                        File(imagePath)))

        editIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivity(Intent.createChooser(editIntent, null))

    }

    private fun shareThisItem(filePath: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(this@FullScreenGalleryActivity,
                        APPLICATION_ID + ".provider",
                        File(filePath)))
        startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }


    private fun deleteThisFile() {

        // Set up the projection (we only need the ID)
        val projection = arrayOf(MediaStore.Images.Media._ID)

        // Match on the file path
        val selection = MediaStore.Images.Media.DATA + " = ?"
        val selectionArgs = arrayOf(CenterRepository.instance.imageCollection.getImageAt(mViewPager.currentItem).imagePath)

        // Query for the ID of the media matching the file path
        val imageCursor = contentResolver
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs, null)

        if (imageCursor != null) {

            if (imageCursor.moveToFirst()) {
                // We found the ID. Deleting the item via the content
                // provider will also remove the file
                val imageID = imageCursor.getLong(imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val deleteUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageID)
                contentResolver.delete(deleteUri, null, null)

                CenterRepository.instance.imageCollection.listOfImages.removeAt(mViewPager.currentItem)

                galleryAdapter!!.notifyDataSetChanged()

                sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(File(CenterRepository.instance.imageCollection.getImageAt(mViewPager.getCurrentItem()).imagePath))))

                Toast.makeText(applicationContext, "Deleted", Toast.LENGTH_SHORT).show()

            }

            imageCursor.close()
        }
    }

    companion object {
        const val IMAGE_POSITION = "PagerPosition"
    }
}

