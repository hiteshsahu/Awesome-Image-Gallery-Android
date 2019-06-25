package com.hiteshsahu.awesome_gallery.modal

/**
 * Created by Hitesh on 23-07-2016.
 */
class CenterRepository private constructor() {

    val imageCollection = ImageCollection()

    fun clearImageCollection() {
        imageCollection.listOfImages.clear()
    }

    companion object {

        private var centerRepositoryInstance: CenterRepository? = null

        val instance: CenterRepository
            get() {
                if (null == centerRepositoryInstance) {
                    centerRepositoryInstance = CenterRepository()
                }
                return centerRepositoryInstance!!
            }
    }

}
