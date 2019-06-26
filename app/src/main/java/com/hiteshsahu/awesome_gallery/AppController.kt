package com.hiteshsahu.awesome_gallery

import android.app.Application
import android.content.ContentResolver

/**
 * Created by Hitesh on 23-07-2016.
 */


class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        appController = this
        globalContentResolvere = contentResolver

    }

    companion object {

        @get:Synchronized
        var appController: AppController? = null
            private set
        @get:Synchronized
        var globalContentResolvere: ContentResolver? = null
            private set
    }
}