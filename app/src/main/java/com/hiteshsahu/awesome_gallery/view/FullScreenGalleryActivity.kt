package com.hiteshsahu.awesome_gallery.view

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.hiteshsahu.awesome_gallery.BuildConfig.APPLICATION_ID
import com.hiteshsahu.awesome_gallery.modal.CenterRepository
import com.hiteshsahu.awesome_gallery.view.adapter.GalleryPagerAdapter
import kotlinx.android.synthetic.main.activity_full_screen_gallery.*
import java.io.File


class FullScreenGalleryActivity : AppCompatActivity() {

    private var galleryAdapter: GalleryPagerAdapter? = null

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
        galleryAdapter = GalleryPagerAdapter()
        mViewPager.adapter = galleryAdapter
        mViewPager.currentItem = currentPagePosition


        editImage.setOnClickListener {
            editThisItem(CenterRepository.instance.imageCollection.getImageAt(mViewPager.currentItem).imagePath)
        }

        shareImage.setOnClickListener {
            shareThisItem(CenterRepository.instance.imageCollection
                    .getImageAt(mViewPager.currentItem)
                    .imagePath)
        }

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

