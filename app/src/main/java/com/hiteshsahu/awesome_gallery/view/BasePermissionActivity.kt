/*
 * Creator: Hitesh Sahu on 2/8/19 1:56 PM
 * Last modified: 2/8/19 1:56 PM
 * Copyright: All rights reserved Ⓒ 2019 http://hiteshsahu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file    except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.hiteshsahu.camerapreview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeImageTransform
import android.transition.Explode
import android.view.*
import android.widget.Toast
import com.hiteshsahu.awesome_gallery.R


/**
 * Abstract class help with permission stuffs
 */
abstract class BasePermissionActivity : AppCompatActivity() {

    val MULTIPLE_PERMISSIONS = 10 // code you want.
    // https://developer.android.com/guide/topics/permissions/overview#permission-groups
    val DANGEROUS_PERMISSIONS = listOf(
            android.Manifest.permission.READ_CALENDAR,
            android.Manifest.permission.WRITE_CALENDAR,
            android.Manifest.permission.READ_CALL_LOG,
            android.Manifest.permission.WRITE_CALL_LOG,
            android.Manifest.permission.PROCESS_OUTGOING_CALLS,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.GET_ACCOUNTS,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_PHONE_NUMBERS,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.ANSWER_PHONE_CALLS,
            android.Manifest.permission.ADD_VOICEMAIL,
            android.Manifest.permission.USE_SIP,
            android.Manifest.permission.BODY_SENSORS,
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.RECEIVE_SMS,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.RECEIVE_WAP_PUSH,
            android.Manifest.permission.RECEIVE_MMS,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = Explode()
            window.exitTransition = Explode()
            window.sharedElementEnterTransition = ChangeImageTransform()
            window.sharedElementExitTransition = ChangeImageTransform()
        }
        setContentView(getActivityLayout())

        if (Build.VERSION.SDK_INT >= 23) {
            // Pain in A$$ Marshmallow+ Permission APIs
            fuckMarshmallow()

        } else {
            // Pre-Marshmallow
            setUpView()
        }
    }

    //SetUp views after permission granted
    abstract fun setUpView()

    //SetUp views when all  permission not granted
    abstract fun setUpViewWithoutPermissions()

    // activity view
    abstract fun getActivityLayout(): Int


    fun fuckMarshmallow() {

        //get all permissions used by app and then filter them if they are Dangerous Permissions
        val PERMISSIONS = retrieveDangerousPermissions(this)

        //check if permission already granted
        val listPermissionsNeeded = ArrayList<String>()
        for (PERMISSION in PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(PERMISSION)
            }
        }

        // request if Permissions needed
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), MULTIPLE_PERMISSIONS)
        } else ( // if already granted move on
                setUpView()
                )
    }


    // Retrieve all permissions from Android manifest and then filter them based on dangerous permission
    private fun retrieveDangerousPermissions(context: Context): ArrayList<String> {
        try {
            val allPermissions = context
                    .packageManager
                    .getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
                    .requestedPermissions


            val dangerousPermissions = ArrayList<String>()

            // fiiler permissions based on Dangeroud permission
            for (permission in allPermissions) {
                if (DANGEROUS_PERMISSIONS.contains(permission)) {
                    dangerousPermissions.add(permission)
                }
            }
//
//            Toast.makeText(context,
//                    "Found "+ dangerousPermissions.size + " / " +allPermissions.size +" Dangerous Permissions",
//                    Toast.LENGTH_LONG)
//                    .show()

            return dangerousPermissions

        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("This should have never happened.", e)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissionsList: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MULTIPLE_PERMISSIONS -> {
                if (grantResults.size > 0) {
                    var permissionsDenied = ""
                    for (i in 0 until permissionsList.size) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += "\n" + permissionsList[i]
                        }
                    }

                    if (permissionsDenied.isEmpty()) {
                        // Show permissionsDenied
                        setUpView()
                        Toast.makeText(this, "All permission Granted :) " + permissionsDenied, Toast.LENGTH_LONG).show()

                    } else {
                        setUpViewWithoutPermissions()
                        Toast.makeText(this, "Permission Denied :(" + permissionsDenied, Toast.LENGTH_LONG).show()
                    }
                }
                return
            }


        }
    }




    fun doCircularReveal(appRoot: View) {

        appRoot!!.visibility = View.INVISIBLE

        window.decorView.findViewById<View>(android.R.id.content)
                .setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.primary_dark_material_dark))

        val viewTreeObserver = appRoot!!.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    circularRevealView(appRoot!!)
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        appRoot!!.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    } else {
                        appRoot!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            })
        }
    }


    /*
     Animate Circular Exit activity
     */
    fun animateExitScreen(appRoot: View) {
        //Circular exit Animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val anim = exitReveal(appRoot!!)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            anim!!.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    supportFinishAfterTransition()
                }
            })
            anim.start()
        } else {
            finish()
        }
    }


    private fun circularRevealView(revealLayout: View) {

        val cx = revealLayout.width / 2
        val cy = revealLayout.height / 2

        val finalRadius = Math.max(revealLayout.width, revealLayout.height).toFloat()

        // create the animator for this view (the start radius is zero)
        var circularReveal: Animator? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils.createCircularReveal(revealLayout, cx, cy, 0f, finalRadius)

            circularReveal!!.duration = 1000

            // make the view visible and start the animation
            revealLayout.visibility = View.VISIBLE

            circularReveal.start()
        } else {
            revealLayout.visibility = View.VISIBLE
        }
    }

    private fun exitReveal(myView: View): Animator? {
        // previously visible view

        // get the center for the clipping circle
        val cx = myView.measuredWidth / 2
        val cy = myView.measuredHeight / 2


        // get the initial radius for the clipping circle
        val initialRadius = myView.width / 2

        // create the animation (the final radius is zero)
        var anim: Animator? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius.toFloat(), 0f)

            // make the view invisible when the animation is done
            anim!!.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    myView.visibility = View.INVISIBLE
                    finish()
                }
            })
        }

        //  anim.setDuration(800);

        // start the animation
        return anim

    }
}

