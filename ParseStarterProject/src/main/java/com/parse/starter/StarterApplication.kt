/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter

import android.app.Application
import android.util.Log

import com.parse.Parse
import com.parse.ParseACL
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.SaveCallback


class StarterApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this)

        // Add your initialization code here
        Parse.initialize(Parse.Configuration.Builder(applicationContext)
                .applicationId("db9088f9651cfd0f5e9345fdbf2cc66336e27646")
                .clientKey("b47b80ce15d98b0f5ba6f15ea5ca225a8ca210eb")
                .server("http://ec2-54-191-82-61.us-west-2.compute.amazonaws.com:80/parse/")
                .build()
        )

        val defaultACL = ParseACL()
        defaultACL.publicReadAccess = true
        defaultACL.publicWriteAccess = true
        ParseACL.setDefaultACL(defaultACL, true)

    }
}
