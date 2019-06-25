package com.hiteshsahu.awesome_gallery.domain

import android.os.AsyncTask

abstract class BaseAsyncTask(val listener: ProgressListener) : AsyncTask<Void, Void, String?>() {

    interface ProgressListener {
        // callback for start
        fun onStarted()

        // callback on success
        fun onCompleted(successMessage: String?)

        // callback on error
        fun onError(errorMessage: String?)

    }

    override fun onPreExecute() {
        listener.onStarted()

    }

    override fun onPostExecute(errorMessage: String?) {
        super.onPostExecute(errorMessage)
        if (null != errorMessage) {
            listener.onError(errorMessage)
        } else {
            listener.onCompleted("Done")
        }
    }
}