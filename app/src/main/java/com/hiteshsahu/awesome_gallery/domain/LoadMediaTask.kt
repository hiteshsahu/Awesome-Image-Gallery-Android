package com.hiteshsahu.awesome_gallery.domain


class LoadMediaTask(listener: ProgressListener) : BaseAsyncTask(listener) {

    override fun doInBackground(vararg params: Void?): String? {
        return ImageMediaProvider.instance.getAllImages()
    }

}