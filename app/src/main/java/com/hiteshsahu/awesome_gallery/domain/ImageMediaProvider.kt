package com.hiteshsahu.awesome_gallery.domain

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import com.hiteshsahu.awesome_gallery.AppController
import com.hiteshsahu.awesome_gallery.modal.CenterRepository
import com.hiteshsahu.awesome_gallery.modal.ImageModel
import java.util.*

class ImageMediaProvider private constructor() {

    fun getAllImages(): String? {

         val shortOrder = MediaStore.Images.Media._ID

        CenterRepository.instance.imageCollection.listOfImages.clear()

        val projection = arrayOf(MediaColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        //Fetch all External SD card Images
        fetchImagesInCursor(AppController.appController!!.contentResolver
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                        null, null, "$shortOrder DESC")!!)

        // Get all Internal SD Card images
        fetchImagesInCursor(AppController.appController!!.contentResolver
                .query(MediaStore.Images.Media.INTERNAL_CONTENT_URI, projection, null, null, "$shortOrder DESC")!!)


        return if (CenterRepository.instance
                        .imageCollection
                        .listOfImages.isEmpty()) {
            null
        } else {
            "NO MEDIA FOUND"
        }

    }

    fun getImageFolderMap(appContext: Context): Int {

        CenterRepository.instance.imageCollection.imagesInFolderMap.clear()

        val projection = arrayOf(MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        //Get all External Image Files
        fetchImagesInFolderCursor(appContext.contentResolver
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, null, null, null)!!)

        // Get all Internal images
        fetchImagesInFolderCursor(appContext.contentResolver
                .query(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                        projection, null, null, null)!!)

        //Get all folders Name from Map
        CenterRepository.instance
                .imageCollection
                .folderNames = ArrayList(CenterRepository
                .instance
                .imageCollection
                .imagesInFolderMap
                .keys)

        return CenterRepository.instance
                .imageCollection.imagesInFolderMap.size
    }

    private fun fetchImagesInFolderCursor(imageFolderCursor: Cursor) {
        val columnIndexData = imageFolderCursor.getColumnIndexOrThrow(MediaColumns.DATA)
        val columnIndexFolderName = imageFolderCursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        while (imageFolderCursor.moveToNext()) {

            val absolutePathOfImage = imageFolderCursor.getString(columnIndexData)
            val folderName = imageFolderCursor.getString(columnIndexFolderName)

            if (CenterRepository.instance.imageCollection.isFolderAdded(folderName)) {

                //If folder is added then insert new Image Data into that folder
                CenterRepository.instance
                        .imageCollection
                        .imagesInFolderMap[folderName]!!
                        .add(ImageModel(folderName, absolutePathOfImage))

            } else {

                //if folder not added then create a new list
                val listOfImagesInFolder = ArrayList<ImageModel>()
                //insert image data in that list
                listOfImagesInFolder
                        .add(ImageModel(folderName, absolutePathOfImage))
                //add list into that folder
                CenterRepository.instance
                        .imageCollection
                        .imagesInFolderMap
                        .put(folderName, listOfImagesInFolder)
            }
        }

        imageFolderCursor.close()
    }

    private fun fetchImagesInCursor(imageCursor: Cursor) {
        val columnIndexData = imageCursor.getColumnIndexOrThrow(MediaColumns.DATA)
        val columnIndexName = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

        while (imageCursor.moveToNext()) {

            CenterRepository.instance
                    .imageCollection
                    .listOfImages
                    .add(ImageModel(imageCursor.getString(columnIndexName), imageCursor.getString(columnIndexData)))
        }
        imageCursor.close()
    }

    companion object {

        val instance = ImageMediaProvider()
    }


}
