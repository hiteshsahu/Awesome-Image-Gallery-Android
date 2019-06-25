package com.hiteshsahu.awesome_gallery.modal

import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Hitesh on 23-07-2016.
 */
class ImageCollection {

    var folderNames = ArrayList<String>()
    val listOfImages = ArrayList<ImageModel>()
    val imagesInFolderMap: HashMap<String, ArrayList<ImageModel>> =  HashMap<String, ArrayList<ImageModel>>()

    fun isFolderAdded(folderName: String): Boolean {
        return imagesInFolderMap.containsKey(folderName)
    }


    fun getImageAt(position: Int): ImageModel {
        return listOfImages[position]
    }
}
